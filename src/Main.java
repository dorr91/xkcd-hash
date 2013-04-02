import java.io.IOException;

public class Main {
	
	static int maxThreads = 4;
	
	public static void main(String [] args) throws IOException {
		if(args.length == 0) System.out.println("Please specify the number of threads to use.");
		else if(args.length > 0) {
			maxThreads = Integer.parseInt(args[0]);
		}
		System.out.println("Starting " + maxThreads + " threads.");

		for(int i=0; i<maxThreads; i++) {
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Starting thread " + i);
			new Guesser(String.valueOf(System.currentTimeMillis())).start();
		}
	}
}
