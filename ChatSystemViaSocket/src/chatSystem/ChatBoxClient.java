package chatSystem;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ChatBoxClient extends JFrame {
	
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	static boolean isStopped;

	private JPanel contentPane;
	private JTextField msgField;
	static JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatBoxClient frame = new ChatBoxClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		try {
			s = new Socket("127.0.0.1" , 1201);
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			
			String msg = new String();
			while(true)
			{
				msg = din.readUTF();
				textArea.setText(textArea.getText().trim() + "\n" + msg);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	/**
	 * Create the frame.
	 */
	public ChatBoxClient() {
		setTitle("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 861, 547);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		textArea.setBounds(40, 33, 761, 336);
		contentPane.add(textArea);
		
		msgField = new JTextField();
		msgField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		msgField.setColumns(10);
		msgField.setBounds(40, 403, 566, 36);
		contentPane.add(msgField);
		
		JButton sendBtn = new JButton("Send");
		sendBtn.setForeground(Color.GREEN);
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try
				{
					String msg = new String();
					msg = msgField.getText().trim();
					dout.writeUTF(msg);
					msgField.setText("");
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		sendBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		sendBtn.setBounds(640, 403, 140, 36);
		contentPane.add(sendBtn);
		
		JLabel label = new JLabel("Type your Message here....");
		label.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label.setBounds(40, 440, 189, 16);
		contentPane.add(label);
		
		JLabel lblChatBox = new JLabel("Chat Box");
		lblChatBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblChatBox.setBounds(40, 13, 56, 16);
		contentPane.add(lblChatBox);
		
		JButton btnLeaveChat = new JButton("Leave Chat");
		btnLeaveChat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLeaveChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					dout.writeUTF("Disconnected");
					isStopped = true;
					setVisible(false);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLeaveChat.setForeground(Color.RED);
		btnLeaveChat.setBounds(640, 462, 140, 25);
		contentPane.add(btnLeaveChat);
	}

}
