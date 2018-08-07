package client;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientWindow implements ActionListener{

	public JFrame frame;
	private JTextField txtAddr;
	private JTextField txtPort;
	private JTextField txtFile;
	private JTextField txtPath;
	private JButton btnConnect;
	private JButton btnClose;
	private JButton btnSelect;
	private JButton btnLoad;
	private JTextArea txtBlank;
	public Client client;
	public ClientWindow() 
	{
		initialize();
		client = new Client();
	}
	
	private void initialize()
	{
		frame = new JFrame();
		frame.setTitle("文件下载客户端");
		frame.setBounds(100, 100, 450, 300);
		frame.setLocation(300, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblAddr = new JLabel("文件服务器IP地址");
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
		
		JLabel lblFile = new JLabel("请输入下载文件名");
		lblFile.setBounds(10, 50, 104, 15);
		frame.getContentPane().add(lblFile);
		
		txtFile = new JTextField();
		txtFile.setBounds(113, 47, 110, 21);
		frame.getContentPane().add(txtFile);
		txtFile.setColumns(10);
		
		JLabel lblPath = new JLabel("请选择保存路径");
		lblPath.setBounds(10, 87, 104, 15);
		frame.getContentPane().add(lblPath);
		
		btnSelect = new JButton("选择");
		btnSelect.addActionListener(this);
		btnSelect.setBounds(101, 83, 68, 23);
		frame.getContentPane().add(btnSelect);
		
		txtPath = new JTextField();
		txtPath.setBounds(171, 84, 169, 21);
		frame.getContentPane().add(txtPath);
		txtPath.setColumns(10);
		
		txtBlank = new JTextArea();
		txtBlank.setBounds(10, 128, 414, 112);
		txtBlank.setEditable(false);
		frame.getContentPane().add(txtBlank);
		
		btnLoad = new JButton("下载");
		btnLoad.addActionListener(this);
		btnLoad.setBounds(358, 83, 66, 23);
		frame.getContentPane().add(btnLoad);
		
		btnClose = new JButton("断开");
		btnClose.setBounds(358, 46, 66, 23);
		btnClose.addActionListener(this);
		frame.getContentPane().add(btnClose);
	}
	
	public void connect()
	{
		String hostName = txtAddr.getText();
		String portNum = txtPort.getText();
		if(hostName.isEmpty() || portNum.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "请先输入IP地址和端口号！", "提示", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			int flag = client.connectServer(hostName, portNum);
			if(flag == 1)
			{
				JOptionPane.showMessageDialog(null,"服务器连接成功！","提示",JOptionPane.INFORMATION_MESSAGE);
			    txtBlank.setText("服务器连接成功...\n");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "服务器连接失败！", "提示", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	public void select()
	{
		JFileChooser chooser = new JFileChooser("./");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("文本文档","txt");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(frame);
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			txtPath.setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	public void download()
	{
		String fileName = txtFile.getText();
		String fileSavePath = txtPath.getText();
		if(fileName.isEmpty() || fileSavePath.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "请先输入文件名和保存路径！", "提示", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			int flag = client.downloadFile(fileName, fileSavePath);
			if(flag == 0)
			{
				JOptionPane.showMessageDialog(null, "要下载的文件不存在！", "提示", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(null,"文件下载完成！","提示",JOptionPane.INFORMATION_MESSAGE);
				txtBlank.append("文件下载完成！保存路径->"+fileSavePath+"\n");
			}
		}
	}
	public void close()
	{
		client.closeSocket();
		JOptionPane.showMessageDialog(null,"已断开服务器连接！","提示",JOptionPane.INFORMATION_MESSAGE);
		txtBlank.append("已断开服务器连接...\n");
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = (Object)e.getSource();
		if(source.equals(btnConnect))
		{
			connect();

		}
		if(source.equals(btnSelect))
		{
			select();
		}
		if(source.equals(btnLoad))
		{
			download();
		}
		if(source.equals(btnClose))
		{
			close();
		}
	}
}
