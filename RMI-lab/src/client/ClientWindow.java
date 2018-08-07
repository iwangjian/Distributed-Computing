package client;

import java.net.MalformedURLException;
import java.rmi.*;

import remoteInterface.RemoteInterface;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientWindow implements ActionListener {

	public JFrame frame;
	private JTextField txtAddr;
	private JTextField txtPort;
	private JTextField txtSno;
	private JTextField txtCno;
	public JTextArea txtBlank;
	public JButton btnConnect;
	public JButton btnQuery;
	private RemoteInterface h;
	
	public static void main(String[] args) 
	{
		ClientWindow window = new ClientWindow();
		window.frame.setVisible(true);	
	}
	
	public ClientWindow() 
	{
		initialize();
	}
	private void initialize() 
	{
		frame = new JFrame();
		frame.setTitle("学生成绩查询客户端");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblAddr = new JLabel("对象服务器IP地址");
		lblAddr.setBounds(9, 13, 104, 15);
		frame.getContentPane().add(lblAddr);
		
		txtAddr = new JTextField();
		txtAddr.setBounds(113, 10, 110, 21);
		frame.getContentPane().add(txtAddr);
		txtAddr.setColumns(10);
		
		JLabel lblPort = new JLabel("端口号");
		lblPort.setBounds(233, 13, 41, 15);
		frame.getContentPane().add(lblPort);
		
		txtPort = new JTextField();
		txtPort.setBounds(274, 10, 66, 21);
		frame.getContentPane().add(txtPort);
		txtPort.setColumns(10);
		
		btnConnect = new JButton("连接");
		btnConnect.addActionListener(this);
		btnConnect.setBounds(358, 9, 66, 23);
		frame.getContentPane().add(btnConnect);
		
		JLabel lblSno = new JLabel("请输入学号");
		lblSno.setBounds(20, 50, 93, 15);
		frame.getContentPane().add(lblSno);
		
		txtSno = new JTextField();
		txtSno.setBounds(113, 47, 169, 21);
		frame.getContentPane().add(txtSno);
		txtSno.setColumns(10);
		
		JLabel lblCno = new JLabel("请输入课程号");
		lblCno.setBounds(21, 87, 82, 15);
		frame.getContentPane().add(lblCno);
		
		txtCno = new JTextField();
		txtCno.setBounds(113, 84, 169, 21);
		frame.getContentPane().add(txtCno);
		txtCno.setColumns(10);
		
		txtBlank = new JTextArea();
		txtBlank.setBounds(10, 128, 414, 112);
		frame.getContentPane().add(txtBlank);
		
		btnQuery = new JButton("查询");
		btnQuery.addActionListener(this);
		btnQuery.setBounds(292, 83, 66, 23);
		frame.getContentPane().add(btnQuery);
		
	}
	
	public void connect()
	{
		String hostName = txtAddr.getText();
		String portNum = txtPort.getText();
		String registryURL = "rmi://" + hostName+ ":" + portNum + "/queryGrade";
        // 调用远程接口
        try {
			h = (RemoteInterface)Naming.lookup(registryURL);
			JOptionPane.showMessageDialog(null,"服务器连接成功！","提示",JOptionPane.INFORMATION_MESSAGE);
			txtBlank.setText("服务器连接成功...\n");
		} 
        catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(null, "URL不正确！", "提示", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} 
        catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, "对象服务器连接失败！", "提示", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} 
        catch (NotBoundException e) {
        	JOptionPane.showMessageDialog(null, "未知的错误！", "提示", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	public void query()
	{
		String sno = txtSno.getText();
		String cno = txtCno.getText();
		try {
			String grade = h.queryGrade(sno, cno);
			if(grade != null)
			{
				txtBlank.append("您的成绩为："+grade+"\n");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "您的成绩为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		} 
		catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, "没有查询到您的成绩！", "提示", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = (Object)e.getSource();
		if(source.equals(btnConnect))
		{
			connect();

		}
		if(source.equals(btnQuery))
		{
			query();
		}
	}
}