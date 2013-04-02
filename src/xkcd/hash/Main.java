package xkcd.hash;

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
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long seed = System.currentTimeMillis();
			new Guesser(seed, i).start();
		}
	}
}