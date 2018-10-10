package com.aibibang.demo.java.JavaSockerDemo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/** 
* @author: Truman.P.Du 
* @since: 2016年11月26日 下午5:25:26 
* @version: v1.0
* @description:
*/
public class SocketClient implements Runnable {
	private Socket s;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private boolean bConnected = false;

	public SocketClient(Socket s) {
		this.s = s;
		try {
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
			bConnected = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(String str) {
		try {
			dos.writeUTF(str);
		} catch (IOException e) {
		}
	}

	public void run() {
		try {
			while (bConnected) {
				String str = dis.readUTF();
				System.out.println("------------来自本地服务器:" + str);
			}
		} catch (EOFException e) {
			System.out.println("Client closed!");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (dis != null)
					dis.close();
				if (dos != null)
					dos.close();
				if (s != null) {
					s.close();
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost", 9091);
		SocketClient client = new SocketClient(socket);
		client.run();
		client.send("test");
	}
}
