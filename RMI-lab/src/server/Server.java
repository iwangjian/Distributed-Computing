package server;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;

public class Server {
	/*
	 * 服务器端主程序入口
	 */
	 public static void main(String[] args)
	 {
		 InputStreamReader is = new InputStreamReader(System.in);
		 BufferedReader br = new BufferedReader(is);
		 String portNum, registryURL;
			try{
				System.out.println("Enter the RMIregistry port number:");
		        portNum = (br.readLine()).trim();
		        int RMIPortNum = Integer.parseInt(portNum);
		        //开始注册
		        startRegistry(RMIPortNum);
		        Impl exportedObj = new Impl();
		        registryURL = "rmi://localhost:" + portNum + "/queryGrade";
		        Naming.rebind(registryURL, exportedObj);
		        System.out.println("Server registered.Registry currently contains:");
		        //列出已注册的URL
		        listRegistry(registryURL); 
		        System.out.println("Server ready.");
			}
			catch(Exception e){
				System.out.println("Exception in Server: " + e);
			}

	 }
	 private static void startRegistry(int RMIPortNum) throws RemoteException
		{
			try{
				LocateRegistry.createRegistry(RMIPortNum);
				Registry registry = LocateRegistry.getRegistry(RMIPortNum);
				registry.list();
			}
			catch(RemoteException e){
				System.out.println("RMI registry cannot be located at port " + RMIPortNum);
				LocateRegistry.createRegistry(RMIPortNum);
				System.out.println("RMI registry created at port " + RMIPortNum);
			}
		}
		private static void listRegistry(String registryURL) throws RemoteException, MalformedURLException
		{
			System.out.println("Registry " + registryURL + " contains: ");
			String[] names = Naming.list(registryURL);
			for(int i=0; i<names.length; i++)
				System.out.println(names[i]);
		}

}
