package xkcd.hash;

public class Benchmark {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		int total = 0;
		int totalTime = 0;
		new Guesser(start, 0).start();
		while(true) {
			for(; System.currentTimeMillis() - start < 1000; ) {
				try{
					Thread.sleep(1000);
				} catch(InterruptedException e) {}
			}
			long elapsed = (System.currentTimeMillis() - start);
			/*int hashed = Guesser.count - total;

			start = System.currentTimeMillis();
			total = Guesser.count;
			totalTime += elapsed;
			System.out.println(elapsed + "ms: " + hashed + "; average: " + (total/(totalTime/1000)));
			*/
		}
	}
}
