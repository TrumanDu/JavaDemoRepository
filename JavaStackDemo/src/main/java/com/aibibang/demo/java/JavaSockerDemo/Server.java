package com.aibibang.demo.java.JavaSockerDemo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** 
* @author: Truman.P.Du 
* @since: 2016年11月24日 下午3:13:19 
* @version: v1.0
* @description:
*/
public class Server extends Thread {

	String inMessage, outMessage;
	ServerSocket serverSocket = null;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			serverSocket = new ServerSocket(9002);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();//从连接队列中取出一个连接，如果没有则等待
				System.out.println("新增连接：" + socket.getInetAddress() + ":" + socket.getPort());
				//接收和发送数据
				// 读取客户端数据  
	            DataInputStream dis = new DataInputStream(socket.getInputStream());  
	            String result = dis.readUTF();
	            System.out.println(result);  
               
	            // 向客户端输出数据  
	            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());  
	            dos.writeUTF("Hello "+result);  
	            dos.flush();
	            
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (socket != null)
						socket.close();//与一个客户端通信结束后，要关闭Socket
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void close() {
		if (serverSocket != null) {
			try {
				serverSocket.close();
				System.out.println("服务器已关闭!!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("服务器关闭异常.....");
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
		server.run();
	}

}
