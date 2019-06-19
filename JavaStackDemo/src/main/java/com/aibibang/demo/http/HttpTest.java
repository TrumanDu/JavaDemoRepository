package com.aibibang.demo.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author：Truman.P.Du
 * @createDate: 2019年3月13日 上午9:20:57
 * @version:1.0
 * @description:
 */
public class HttpTest {

	public static void main(String[] args) throws IOException {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/vnd.ksql.v1+json; charset=utf-8");
		RequestBody body = RequestBody.create(mediaType,
				"{\r\n  \"ksql\": \"SELECT * FROM EC_BOT_DETECTION;\",\r\n  \"streamsProperties\": {}\r\n}");
		Request request = new Request.Builder().url("http://172.16.42.31:8088/query").post(body)
				.addHeader("Content-Type", "application/vnd.ksql.v1+json; charset=utf-8").build();

		Response response = client.newCall(request).execute();
		InputStream is = response.body().byteStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String str = null;
		while (true) {
			str = reader.readLine();
			if (str != null)
				System.out.println(str);
			else
				break;
		}
	}

}
