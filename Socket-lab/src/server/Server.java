package server;
import java.io.*;
import java.net.*;

public class Server {
	
	public static final int PORT = 9090;
	/*
	 * 服务器端主程序入口
	 */
	public static void main(String[] args)
	{

		ServerWindow window = new ServerWindow();
		window.frame.setVisible(true);
		ServerSocket  serverSocket = null;
		String serverAddr;
		try 
		{
			serverSocket = new ServerSocket(PORT);
			serverAddr = InetAddress.getByName(null).toString();
			window.txtInfo.setText("服务器已启动...\nIP地址：//"+serverAddr+"\n端口号："+PORT+"\n");
			System.out.println("服务器已启动...\nIP地址：//"+serverAddr+"  端口号："+PORT+"\n");
			while(true)
			{
				Socket dataSocket = serverSocket.accept();
			    try {
			    	MultiThreadSocket multiSocket = new MultiThreadSocket(dataSocket);
			    	String fileName = multiSocket.fileName;
			    	window.txtInfo.append("请求下载文件->"+fileName+"\n");
			    	window.frame.setVisible(true);
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