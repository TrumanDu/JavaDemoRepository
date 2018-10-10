package com.aibibang.demo.java.JavaSockerDemo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/** 
* @author: Truman.P.Du 
* @since: 2016年11月24日 下午7:11:54 
* @version: v1.0
* @description:
*/
public class DemoClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataInputStream in;  
		
        DataOutputStream out;
        
        /*BufferedInputStream is;
        BufferedOutputStream os;*/
        byte[] readResult=new byte[2000];
     try {
		Socket socket = new Socket("127.0.0.1",9091);
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		
		out.write("I am test!我的额外分为".getBytes());
		
		in.read(readResult);
		System.out.println(new String(readResult,"UTF-8"));
		in.close();
		out.close();
		socket.close();
		
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
