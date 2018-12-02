package chatSystem;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.util.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ChatBoxServer extends JFrame{
	
	static ServerSocket ss;
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	static List<Socket> clients = new ArrayList<Socket>();

	private JPanel contentPane;
	private JTextField msgText;
	static JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatBoxServer frame = new ChatBoxServer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		CircularQueue.initQueue();
		try {
			ss = new ServerSocket(1201);
			ss.setReuseAddress(true);
			
			while(true) {
				s = new Socket();
				s = ss.accept();
				
				ClientHandler clientSocket = new ClientHandler(s);
				textArea.setText(textArea.getText().trim() + "\n" + "Client "+ clientSocket.num +" : "+ " is Connected."); 
				clients.add(s);
				 
	            // The background thread will handle each client separately
	            new Thread(clientSocket).start();
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
	public ChatBoxServer() {
		setTitle("Server");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 861, 547);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		textArea.setBounds(38, 32, 761, 336);
		contentPane.add(textArea);
		
		msgText = new JTextField();
		msgText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		msgText.setBounds(38, 403, 566, 36);
		contentPane.add(msgText);
		msgText.setColumns(10);
		
		JButton sendBtn = new JButton("Send");
		sendBtn.setForeground(Color.GREEN);
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String msg = new String();
				for(Socket sk : clients)
				{
					try {
							dout = new DataOutputStream(sk.getOutputStream());
							msg = "Server : ";
							msg = msg.concat(msgText.getText().trim());
							dout.writeUTF(msg);	
						}
					catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				CircularQueue.enQueue(msg);
				msgText.setText("");
			}
		});
		sendBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		sendBtn.setBounds(639, 403, 140, 36);
		contentPane.add(sendBtn);
		
		JLabel lblTypeYourMessage = new JLabel("Type your Message here....");
		lblTypeYourMessage.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblTypeYourMessage.setBounds(38, 440, 189, 16);
		contentPane.add(lblTypeYourMessage);
		
		JLabel label = new JLabel("Chat Box");
		label.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label.setBounds(38, 13, 56, 16);
		contentPane.add(label);
		
		JButton btnNewButton = new JButton("Show Last 10 Msgs");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				CircularQueue.printQueue();
				Messages msg = new Messages(CircularQueue.queue);
				msg.setVisible(true);
				msg.setDefaultCloseOperation(EXIT_ON_CLOSE);
			}
		});
		btnNewButton.setForeground(Color.BLUE);
		btnNewButton.setBounds(610, 452, 189, 25);
		contentPane.add(btnNewButton);
	}
}

