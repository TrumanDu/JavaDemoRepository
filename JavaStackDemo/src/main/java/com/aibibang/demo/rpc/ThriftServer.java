package com.aibibang.demo.rpc;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aibibang.demo.rpc.proxy.ThriftClient;
import com.aibibang.demo.rpc.proxy.ThriftClientProxy;

/** 
* @author: Truman.P.Du 
* @since: 2016年8月18日 上午9:31:26 
* @version: v1.0
* @description:
*/
public class ThriftServer {
	/**
	 * 服务处理对象
	 */
	private static ThriftClient.Processor<ThriftClientProxy> processor = null;
	/**
	 * 日志输出控件
	 */
	private static Logger logger = LoggerFactory.getLogger(ThriftServer.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			processor = new ThriftClient.Processor<ThriftClientProxy>(new ThriftClientProxy());
			TNonblockingServerSocket socket = new TNonblockingServerSocket(9090);
			THsHaServer.Args arg = new THsHaServer.Args(socket);
			arg.protocolFactory(new TCompactProtocol.Factory());//压缩格式
			arg.transportFactory(new TFramedTransport.Factory());
			arg.processorFactory(new TProcessorFactory(processor));
			TServer server = new THsHaServer(arg);
			logger.info("starting the simple server....");
			server.serve();
		} catch (TTransportException e) {
			logger.error("Start thrift server fail.", e);
		}
	}

}
