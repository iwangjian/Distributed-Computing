package client;
import java.io.*;
import java.rmi.*;

import remoteInterface.RemoteInterface;

public class Client {
	/*
	 * 客户端主程序入口
	 */
	
	public static void main(String args[])
	{
		try{        
	        String hostName, portNum;
	        InputStreamReader is = new InputStreamReader(System.in);
	        BufferedReader br = new BufferedReader(is);
	        System.out.println("Enter the RMIRegistry host namer:");
	        hostName = br.readLine();
	        System.out.println("Enter the RMIregistry port number:");
	        portNum = br.readLine();
	        String registryURL = "rmi://" + hostName+ ":" + portNum + "/queryGrade";
	        // 调用远程接口
	        RemoteInterface h = (RemoteInterface)Naming.lookup(registryURL);
	        System.out.println("Lookup completed.");
	        // 获取学生成绩
	        System.out.println("Please input the student ID:");
	        String sno = br.readLine();
	        System.out.println("Please input the course ID:");
	        String cno = br.readLine();
	        String grade = h.queryGrade(sno, cno);
	        System.out.println("The grade is: " +grade);
	     } 
	   catch(Exception e) {
	        System.out.println("Exception in Client: " + e);
	      } 
	}
}

