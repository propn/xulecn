package com.ztesoft.vsop.gxSocket;

/**
 * 类说明 :｛建立客户端｝
 *
 */

import java.io.*;
import java.net.*;

import org.apache.log4j.Logger;

public class ClientSocket {
	private static Logger logger = Logger.getLogger(ClientSocket.class);
//	Socket socket;
//	BufferedReader in;
//	PrintWriter out;

//	public ClientSocket() {
//		try {
//			//广西vsop异常工单处理的socket服务端和客户端都放在一台应用服务器上，
//			socket = new Socket("134.192.204.40", 10000);
//			out = new PrintWriter(socket.getOutputStream(), true);
//			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		} catch (IOException e) {
//			e.printStackTrace() ;
//		}
//			
//	}
	private String serverAddress = "127.0.0.1";
	private int listenerPort = 10000;

	private Socket server = null;

	private BufferedReader reader = null;
	private PrintWriter writer = null;

	public ClientSocket(String serverAddress, int listenerPort)
			throws UnknownHostException, IOException {
		this.serverAddress = serverAddress;
		this.listenerPort = listenerPort;
		initSocket();
	}

	public ClientSocket() throws UnknownHostException, IOException {
		initSocket();
	}

	private void initSocket() throws UnknownHostException, IOException {
		//if (server == null)
			server = new Socket(serverAddress, listenerPort);
		getInput();
		getOutput();
	}

	private BufferedReader getInput() throws IOException {
		//if (reader == null) {
			reader = new BufferedReader(new InputStreamReader(server
					.getInputStream()));
		//}
		return reader;
	}

	private PrintWriter getOutput() throws IOException {
		if (writer == null) {
			writer = new PrintWriter(server.getOutputStream());
		}
		return writer;
	}


	public String sendMsg(String send2ServerMsg) throws UnknownHostException,
			IOException, InterruptedException {
		// synchronized (server) {
		logger.info("send Msg :"+send2ServerMsg);
		logger.info("ClientSocket.sendMsg start");
		BufferedReader in = getInput();
		PrintWriter out = getOutput();
		// 发送信息
		System.out.println("发送信息=====" + send2ServerMsg);
		out.println(send2ServerMsg);
		out.flush();

//		if (testFlag)
//			Thread.currentThread().sleep(2000);

		// 接收信息
		String reveiveMsg = in.readLine();
		System.out.println("接收信息=====" + reveiveMsg);
		logger.info("rece Msg=====" + reveiveMsg);
		logger.info("ClientSocket.sendMsg end");

		closeSource();
		return reveiveMsg;
		// }

	}
//	public String sendMsg(String msg) throws IOException{
//		try {
//			out.println(msg);
//			logger.info("send Msg :"+msg);
//			logger.info("send Msg HashCode :" + msg.hashCode());
//			logger.info("ClientSocket.sendMsg start");
//			String reveiveMsg = in.readLine();
//			logger.info("rece Msg=====" + reveiveMsg);
//			logger.info("receMsg HashCode: " + reveiveMsg.hashCode());
//			logger.info("ClientSocket.sendMsg end");
//			return reveiveMsg;
//		} catch (IOException e) {
//			e.printStackTrace() ;
//		}finally{
//			try {
//				closeSource();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				throw e;
//			}
//			
//		}
//		return "";
//	}
	private void closeSource()throws IOException{
		closePrintWriter();
		closeBufferedReader();
		closeSocket();
	}
	private void closePrintWriter(){
		if(this.writer != null)
		this.writer.close();
	}
	private void closeBufferedReader() throws IOException{
		try{
			if(this.reader != null)
			this.reader.close();
		}catch(IOException e){
			e.printStackTrace();
			throw e;
		}
	}
	private void closeSocket() throws IOException{
		if(this.server != null){
			try{
				this.server.close();
			}catch(IOException e){
				e.printStackTrace();
				throw e;
			}
		}
		
	}
	public static void main(String[] args) throws Exception {
		System.out.println("回复信息咯："+new ClientSocket().sendMsg("测试测试啊"));
	}
}
