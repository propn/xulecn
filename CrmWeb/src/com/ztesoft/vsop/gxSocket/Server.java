package com.ztesoft.vsop.gxSocket;

/**
 * 类说明 :
 *｛建立服务器｝
 * 
 */

import java.net.*;
import java.io.*;

public class Server {
	private ServerSocket ss;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public Server() {
		try {
			ss = new ServerSocket(10000);
			while (true) {
				socket = ss.accept();
				System.out.println("============================xxx") ;
				in = new BufferedReader(new InputStreamReader(socket
						.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				String line = in.readLine();
				System.out.println("============================Content="+line) ;
				out.println("you input is :" + line);
//				out.flush();
				out.close();
				in.close();
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		new Server();
	}
}
