package com.aibibang.demo.elasticsearch.rest;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

/**
 * @author：Truman.P.Du
 * @createDate: 2017年11月16日 下午1:38:43
 * @version:1.0
 * @description:
 */
public abstract class HttpClient {
	private PoolingHttpClientConnectionManager cm;
	// 设置超时时间
	private final int REQUEST_TIMEOUT = 30 * 1000;
	private final int REQUEST_SOCKET_TIME = 30 * 1000;
	private String EMPTY_STR = "";
	/*private int retryTime = 5;
	private int popSize = 0;*/
	public CopyOnWriteArrayList<String> urls = new CopyOnWriteArrayList<String>();

	public HttpClient(String host) {
		cm = new PoolingHttpClientConnectionManager();
		// Increase max total connection to 200
		cm.setMaxTotal(200);
		urls.add(host);
	}

	public String getUrl() throws Exception {

		if (urls.size() > 0) {
			return "http://" + urls.get(0);
		} else {
			throw new Exception("errors:now has no rest url can use !Please to check  config");
		}
	}

	/**
	 * 将不可用的URL移至队尾
	 */
	public void popErrorUrl() {
		// 整个队列重试5次不再重试
/*		if (popSize == retryTime * urls.size()) {
			urls.clear();
		}*/

		if (urls.size() > 0) {
			String url = urls.get(0);
			urls.remove(0);
			urls.add(url);
			//popSize++;
		}
	}

	public CloseableHttpClient getHttpClient() {

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(REQUEST_TIMEOUT)
				.setSocketTimeout(REQUEST_SOCKET_TIME).build();

		return HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(cm).build();
	}

	public String httpHead(String url) throws Exception {
		HttpHead httpHead = new HttpHead(getUrl() + url);
		return getResult(httpHead);
	}

	public String httpGet(String url) throws Exception {
		HttpGet httpGet = new HttpGet(getUrl() + url);
		return getResult(httpGet);
	}

	public String httpGet(String host, String url) throws Exception {
		HttpGet httpGet = new HttpGet(host + url);
		return getResult(httpGet);
	}

	public String httpPut(String url, HttpEntity entity) throws Exception {
		HttpPut httpPut = new HttpPut(getUrl() + url);
		httpPut.setEntity(entity);
		return getResult(httpPut);
	}

	public String httpDelete(String url) throws Exception {
		HttpDelete httpDelete = new HttpDelete(getUrl() + url);
		return getResult(httpDelete);
	}

	public String httpPost(String url, HttpEntity entity) throws Exception {
		HttpPost httpPost = new HttpPost(getUrl() + url);
		httpPost.setEntity(entity);
		return getResult(httpPost);
	}

	/**
	 * 处理Http请求
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	public String getResult(HttpRequestBase request) {
		CloseableHttpClient httpClient = getHttpClient();
		try {
			CloseableHttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			int responseCode = response.getStatusLine().getStatusCode();
			if (entity != null) {
				String result = EntityUtils.toString(entity, "utf-8");
				response.close();
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (HttpHostConnectException e) {
			try {
				return retryAction(request);
			} catch (Exception e2) {
				e2.printStackTrace();
				e.printStackTrace();
			}

		} catch (IOException e) {
			try {
				return retryAction(request);
			} catch (Exception e2) {
				e2.printStackTrace();
				e.printStackTrace();
			}
		} finally {
		}

		return EMPTY_STR;
	}

	public boolean getStatusCode(HttpRequestBase request) throws Exception {
		CloseableHttpClient httpClient = getHttpClient();
		try {
			CloseableHttpResponse response = httpClient.execute(request);
			int httpCode = response.getStatusLine().getStatusCode();
			response.close();
			if (200 == httpCode) {
				return true;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (HttpHostConnectException e) {
			this.popErrorUrl();
			if (urls.size() > 0) {
				return retryGetStatusCode(request);
			} else {
				e.printStackTrace();
			}

		} catch (IOException e) {
			try {
				return retryGetStatusCode(request);
			} catch (Exception e2) {
				e2.printStackTrace();
				e.printStackTrace();
			}
		} finally {
		}
		return false;
	}

	public String retryAction(HttpRequestBase request) throws Exception {
		this.popErrorUrl();
		if (urls.size() > 0) {
			String path = request.getURI().getPath();
			try {
				request.setURI(URI.create(getUrl() + path));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return getResult(request);
		} else {
			throw new Exception("errors:now has no es rest url can use !Please to check");
		}
	}

	public boolean retryGetStatusCode(HttpRequestBase request) throws Exception {
		this.popErrorUrl();
		if (urls.size() > 0) {
			String path = request.getURI().getPath();
			try {
				request.setURI(URI.create(getUrl() + path));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return getStatusCode(request);
		} else {
			throw new Exception("errors:now has no es rest url can use !Please to check");
		}
	}
}
