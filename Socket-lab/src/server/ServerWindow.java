package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.tree.DefaultMutableTreeNode;

public class ServerWindow implements ActionListener{
	
	public JFrame frame;
	private JTree treeFileList;
	private JButton btnStart;
	private JButton btnClose;
	public JTextArea txtInfo;
	
	public ServerWindow() 
	{
		initialize();
	}
	private void initialize() 
	{
		frame = new JFrame();
		frame.setTitle("文件服务器");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panelFile = new JPanel();
		panelFile.setBounds(10, 10, 162, 223);
		frame.getContentPane().add(panelFile);
		panelFile.setLayout(null);
		
		JLabel lblFileList = new JLabel("文件列表");
		lblFileList.setBounds(10, 10, 88, 15);
		panelFile.add(lblFileList);
		
		//创建文件列表树形结构
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Files");
		DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("pic1.jpg");
		DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("pic2.jpg");
		DefaultMutableTreeNode child3 = new DefaultMutableTreeNode("test1.txt");
		DefaultMutableTreeNode child4 = new DefaultMutableTreeNode("test2.doc");
		root.add(child1);
		root.add(child2);
		root.add(child3);
		root.add(child4);
		treeFileList = new JTree(root);
		treeFileList.setBounds(10, 28, 142, 195);
		panelFile.add(treeFileList);
		
		btnStart = new JButton("启动");
		btnStart.setBounds(186, 10, 93, 23);
		btnStart.addActionListener(this);
		frame.getContentPane().add(btnStart);
		
		btnClose = new JButton("关闭");
		btnClose.setBounds(300, 10, 93, 23);
		btnClose.addActionListener(this);
		frame.getContentPane().add(btnClose);
		
		txtInfo = new JTextArea();
		txtInfo.setBounds(182, 43, 212, 190);
		txtInfo.setEditable(false);
		frame.getContentPane().add(txtInfo);	
	}
	
	public void start()
	{
		txtInfo.setText("服务器已启动...\nIP地址：//localhost/127.0.0.1\n端口号："+Server.PORT+"\n");
	}
	
	public void close()
	{
		txtInfo.append("服务器已关闭...\n");
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = (Object)e.getSource();
		if(source.equals(btnStart))
		{
			start();
		}
		if(source.equals(btnClose))
		{
			close();
		}	
	}
}

