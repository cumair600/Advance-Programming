package chatSystem;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable
{
	private Socket clientSocket;
	private static int number;
	int num;
	
	public ClientHandler(Socket s)
	{
		clientSocket = s;
		number++;
		num = number;
	}
	
	static
	{
		number = 0;
	}

	@Override
	public void run() {
		
		try {
			DataInputStream din = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream dout = new DataOutputStream(clientSocket.getOutputStream());
			
			while(true)
			{
				String msg = new String();
				String msg2 = new String();
				msg = din.readUTF();
				ChatBoxServer.textArea.setText(ChatBoxServer.textArea.getText().trim() + "\n" + "Client " + num + " : " + msg);
				for(Socket sk : ChatBoxServer.clients)
				{
					dout = new DataOutputStream(sk.getOutputStream());
					msg2 = "Client " + num + " : " + msg;
					dout.writeUTF(msg2);
				}
				CircularQueue.enQueue(msg2);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
}
