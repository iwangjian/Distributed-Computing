package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

	public static final int PORT = 8080;
	public static void main(String[] args)
	{
		ServerSocket  serverSocket = null;
		String serverAddr;
		try 
		{
			serverSocket = new ServerSocket(PORT);
			serverAddr = InetAddress.getByName(null).toString();
			System.out.println("Web服务器已启动...\nIP地址：//"+serverAddr+"  端口号："+PORT+"\n");
			while(true)
			{
				Socket dataSocket = serverSocket.accept();
			    try {
			    	new MultiThreadSocket(dataSocket);
			    	}
			    catch (IOException e) {
				    dataSocket.close();
				    }
			 }
		}
		catch (IOException e){
			e.printStackTrace();
		}
		finally
		{
			try {
					serverSocket.close();
				} 
			catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
