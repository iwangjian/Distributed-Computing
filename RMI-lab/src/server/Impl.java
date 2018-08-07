package server;
import remoteInterface.RemoteInterface;

import java.rmi.*;
import java.rmi.server.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Impl extends UnicastRemoteObject implements RemoteInterface {
	
	private static final long serialVersionUID = 1L;

	protected Impl() throws RemoteException 
	{
		super();
	}

	public String queryGrade(String sno, String cno) throws RemoteException
	{
		//驱动程序名
		String driver = "com.mysql.jdbc.Driver";
		//URL指向要访问的数据库
		String url = "jdbc:mysql://localhost:3306/student";
		String user = "root";
		String password = "";
		String grade;
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		    if(!conn.isClosed())
			{
				System.out.println("Succeeded to connecting to database.");
			}
		    String sql = "SELECT GRADE FROM sc WHERE SNO='"+sno+"' AND CNO='"+cno+"';";
		    Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if(resultSet.next())
			{
				grade = resultSet.getString("GRADE");
				return grade;
			}
			}
		catch(ClassNotFoundException e1)
		{
			System.out.println("Driver of database not exist!");
			return null;
		}
		catch(SQLException e2)
		{
			System.out.println("Database error!");
			return null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
