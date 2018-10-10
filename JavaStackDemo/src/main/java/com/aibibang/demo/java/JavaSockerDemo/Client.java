package com.aibibang.demo.java.JavaSockerDemo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/** 
* @author: Truman.P.Du 
* @since: 2016年11月24日 下午3:14:37 
* @version: v1.0
* @description:
*/
public class Client {

	public static void main(String[] args) {

		for (int i = 0; i < 1; i++) {
			new Thread() {
				public void run() {
					Socket mysocket;
					try {

						mysocket = new Socket("127.0.0.1", 9002);
						// 向服务器端发送数据  
						OutputStream os = mysocket.getOutputStream();
						DataOutputStream bos = new DataOutputStream(os);
						bos.writeUTF("I am " + Thread.currentThread().getName());
						bos.flush();

						// 接收服务器端数据  

						DataInputStream dis = new DataInputStream(mysocket.getInputStream());
						System.out.println("-------------");
						System.out.println(dis.readUTF());

					} catch (Exception e) {
					}
				};
			}.start();
		}

	}
}
