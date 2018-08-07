package remoteInterface;

import java.rmi.*;

public interface RemoteInterface extends Remote {
	/*
	 * remote interface of querying grades of students
	 */
	public String queryGrade(String sno, String cno) throws java.rmi.RemoteException;
}
