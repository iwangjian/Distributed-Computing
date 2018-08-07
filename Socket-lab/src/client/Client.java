package client;

import java.io.*;
import java.net.*;

public class Client {
	private static final int MAXBYTE = 1024;
	public Socket dataSocket;
	
	//采用流式Socket连接服务器
	public int connectServer(String hostName, String portNum)
	{
		try 
		{
			InetAddress host = InetAddress.getByName(hostName);
			int port = Integer.parseInt(portNum);
			SocketAddress socketAddr = new InetSocketAddress(host, port);
			dataSocket = new Socket();
			dataSocket.connect(socketAddr,5000);
		} 
		catch (Exception e) {
			return 0;
		}
		return 1;
	}
	
	//下载文件
	public int downloadFile(String name, String path)
	{
		String fileName = name;
		String fileSavePath = path;
		try 
		{
			//发送文件名给服务器
			DataOutputStream bufferedWriter = new DataOutputStream(dataSocket.getOutputStream());
			bufferedWriter.writeUTF(fileName);
			//接收服务器传来的文件
			byte[] bytes = new byte[MAXBYTE];
			DataInputStream dataInput = new DataInputStream(dataSocket.getInputStream());
			int length = 0, begin = 0;
			File tmpFile = new File(fileSavePath);
			FileOutputStream fileOutput = new FileOutputStream(tmpFile);
			while((length = dataInput.read(bytes,0,bytes.length)) >= -1)
			{
				if(begin==0 && length == -1) //当第一次读取数据流为空时说明文件不存在
				{
					tmpFile.deleteOnExit();
					fileOutput.close();
					return 0;
				}
				begin = 1;
				if(length == -1) //文件读取完毕
					break;
				fileOutput.write(bytes,0,length);			
			}
			fileOutput.close();
			dataInput.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return 1;
	}
	
	public void closeSocket()
	{
		try
		{
			dataSocket.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * 客户端主程序入口
	 */
	public static void main(String args[])
	{
		ClientWindow window = new ClientWindow();
		window.frame.setVisible(true);			
	}
}
