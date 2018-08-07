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
	public String request;
	private static final int MAXBYTE = 2048;
	/*
	 * 多线程服务
	 */
	public MultiThreadSocket(Socket dataSocket) throws IOException
	{
		socket = dataSocket;
		bufferedReader = new DataInputStream(socket.getInputStream());
		byte[] requestByte = new byte[MAXBYTE];
		
		bufferedReader.read(requestByte);
		request = new String(requestByte);
		System.out.println(request);
		dataOutput = new DataOutputStream(socket.getOutputStream());

		start();
	}
	public void run()
	{
		try {
			if(request.startsWith("GET"))
			{
				int beginIndex = request.indexOf("/");
				int endIndex = request.indexOf(" ", beginIndex);
				String fileName = request.substring(beginIndex+1, endIndex);
				
				if(fileName.endsWith(".html") || fileName.endsWith(".js") || fileName.endsWith(".css")
						|| fileName.endsWith(".jpg") || fileName.endsWith(".jpg")|| fileName.endsWith(".txt"))
				{
					byte[] bytes = new byte[MAXBYTE];
					File file = new File("files/"+fileName);
					if(file.exists())
					{
						FileInputStream fileInput = new FileInputStream(file);
						String responseHead = "HTTP/1.1 200 OK\r\n"
								+"Content_Type:text/html\r\n"
								+"Content_Length:"+file.length()+"\r\n"
								+"\r\n";
						dataOutput.write(responseHead.getBytes());
						int length = 0;
						while((length = fileInput.read(bytes,0,bytes.length)) > 0)
						{
							dataOutput.write(bytes,0,length);
						}
						System.out.println("Response succeeded.");
						fileInput.close();
					}
					else
					{
						String errorMessage = "HTTP/1.1 404 NOT Found\n"
								+"Content_Type:text/html\n"
								+"Content_Length:23\n"
								+"\n"
								+"File Not Found";
						dataOutput.write(errorMessage.getBytes());
						System.out.println("File Not Found.");
					}
				}
				else if(fileName.endsWith(".cgi"))
				{
					String program = fileName + ".exe";
					File file = new File("files/"+program);
					if(file.exists())
					{
						Runtime run = Runtime.getRuntime();
						Process CGI = run.exec("files/"+program);
						DataInputStream buff = new DataInputStream(CGI.getInputStream());
						String display = "";
						byte[] bytes = new byte[MAXBYTE];
						while(buff.read(bytes,0,bytes.length) > 0)
						{
							display = display + new String(bytes);
						}
						System.out.println(display);
						String responseHead = "HTTP/1.1 200 OK\r\n"
								+"Content_Type:text/html\r\n"
								+"Content_Length:"+display.getBytes().length +"\r\n"
								+"\r\n";
						dataOutput.write(responseHead.getBytes());
						dataOutput.write(display.getBytes());
					}
					else
					{
						String errorMessage = "HTTP/1.1 404 NOT Found\n"
								+"Content_Type:text/html\n"
								+"Content_Length:23\n"
								+"\n"
								+"File Not Found";
						dataOutput.write(errorMessage.getBytes());
						System.out.println("File Not Found.");
					}
				}
			}
			else if(request.startsWith("POST"))
			{
				int beginIndex = request.indexOf("/");
				int endIndex = request.indexOf(" ", beginIndex);
				String fileName = request.substring(beginIndex+1, endIndex);

				int beginIndex2 = request.indexOf("arg=");
				int endIndex2 = request.indexOf("\0", beginIndex2);
				String args = request.substring(beginIndex2+4, endIndex2);

				Runtime run = Runtime.getRuntime();
				Process CGI = run.exec("files/"+fileName+" "+args);
				DataInputStream buff = new DataInputStream(CGI.getInputStream());
				String display = "";
				byte[] bytes = new byte[MAXBYTE];
				while(buff.read(bytes,0,bytes.length) > 0)
				{
					display = display + new String(bytes);
				}

				String responseHead = "HTTP/1.1 200 OK\r\n"
						+"Content_Type:text/html\r\n"
						+"Content_Length:"+display.getBytes().length +"\r\n"
						+"\r\n";
				dataOutput.write(responseHead.getBytes());
				dataOutput.write(display.getBytes());
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

