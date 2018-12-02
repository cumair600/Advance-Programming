package chatSystem;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Messages extends JFrame {

	private JPanel contentPane;
	String[] queue = new String[10];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Messages frame = new Messages();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Messages(String[] q) {
		setTitle("Messages");
		
		queue = q;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 922, 444);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane msgs = new JTextPane();
		msgs.setEditable(false);
		msgs.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		msgs.setBounds(43, 56, 808, 287);
		contentPane.add(msgs);
		
		JLabel lblLastMessages = new JLabel("Last 10 Messages");
		lblLastMessages.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		lblLastMessages.setBounds(43, 13, 203, 34);
		contentPane.add(lblLastMessages);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				setVisible(false);
			}
		});
		closeBtn.setForeground(Color.RED);
		closeBtn.setFont(new Font("Times New Roman", Font.BOLD, 16));
		closeBtn.setBounds(295, 359, 270, 25);
		contentPane.add(closeBtn);
		
		for(int i = 0 ; i < 10 ; i++)
			msgs.setText(msgs.getText().trim() + "\n" + queue[i]);
	}
}
