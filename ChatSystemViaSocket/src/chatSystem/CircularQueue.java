package chatSystem;

public class CircularQueue {
	
	static String queue[] = new String[10];
	int size = 10;
	
	CircularQueue()
	{
		queue = new String[10];
		size = 10;
	}
	
	CircularQueue(int size)
	{
		this.size = size;
		queue = new String[size];
	}
	
	static void initQueue()
	{
		for(int i = 0 ; i < 10 ; i++)
		{
			queue[i] = new String();
		}
	}
	
	static void enQueue(String s)
	{
		String temp = new String(queue[0]);
		queue[0] = s;
		for(int i = 1 ; i < 10 ; i++)
		{
			String t = new String(queue[i]);
			queue[i] = temp;
			temp = t;
		}
	}
	
	static void printQueue()
	{
		System.out.println("Last 10 Messages are :");
		for(int i = 0 ; i < 10 ; i++)
		{
			System.out.println(queue[i]);
		}
	}
}
