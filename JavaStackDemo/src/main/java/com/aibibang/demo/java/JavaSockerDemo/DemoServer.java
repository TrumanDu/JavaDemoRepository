package com.aibibang.demo.java.JavaSockerDemo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** 
* @author: Truman.P.Du 
* @since: 2016年11月24日 下午4:29:45 
* @version: v1.0
* @description:
*/
public class DemoServer {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		DataInputStream in;  
        DataOutputStream out;
        byte[] readResult=new byte[2000];  
		try {
			serverSocket = new ServerSocket(9091);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (true) {
			Socket socker = null;

			try {
				socker = serverSocket.accept();
				in = new DataInputStream(socker.getInputStream());
				out = new DataOutputStream(socker.getOutputStream());
				in.read(readResult);
				System.out.println(new String(readResult));
				String back = "received："+new String(readResult)+"\n";
				out.write(back.getBytes());
				out.flush();
				in.close();
				out.close();
				socker.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
