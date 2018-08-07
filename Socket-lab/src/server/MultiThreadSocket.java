package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class MultiThreadSocket extends Thread {
	
	private Socket socket;
	private DataInputStream bufferedReader;
	private DataOutputStream dataOutput;
	private File dir;
	public String fileName;
	private static final int MAXBYTE = 1024;
	/*
	 * 多线程服务,根据客户端请求的文件名传输对应的文件
	 */
	public MultiThreadSocket(Socket dataSocket) throws IOException
	{
		socket = dataSocket;
		bufferedReader = new DataInputStream(socket.getInputStream());
		fileName = bufferedReader.readUTF();
		dataOutput = new DataOutputStream(socket.getOutputStream());
		dir = new File("files/");
		start();
	}
	public void run()
	{
		try {
			String[] files = dir.list();
			int isExist = 0;
			for(int i=0; i<files.length; i++)
			{
				if(files[i].equals(fileName))
				{
					isExist = 1;
					break;
				}
			}
			if(isExist == 1)
			{
				int length = 0;
				byte[] bytes = new byte[MAXBYTE];
				FileInputStream fileInput = new FileInputStream("files/"+fileName);
				while((length = fileInput.read(bytes,0,bytes.length)) > 0)
				{
					dataOutput.write(bytes,0,length);
	
				}
				System.out.println("文件传输完成.");
				fileInput.close();
			}
			else
			{
				System.out.println("请求的文件不存在！");
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				bufferedReader.close();
				socket.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

