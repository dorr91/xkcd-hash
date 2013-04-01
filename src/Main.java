import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main {
	
	static final int MAXTHREADS = 20;
	
	public static void main(String [] args) throws IOException {
		System.out.println("Starting");
		Integer threadcount = new Integer(0);

		String[][] split = splitDict(generateDict());

		for(int i=0; i<split.length; i++) {
			
			//make sure we don't exceed the maximum number of threads
			//(in case someone decides to split the dict into more than MAXTHREAD pieces)
			for(; threadcount >= MAXTHREADS; ) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
			}
			new Guesser(split[i], threadcount).start();
			threadcount ++;
		}
	}
	
	static String[][] splitDict(String[] dict) {
		int numDicts = MAXTHREADS;
		int wordsPerDict = dict.length/MAXTHREADS;
		//potentially lose up to wordsPerDict - 1 words. oh well.
		String[][] dictlist = new String[numDicts][wordsPerDict];
		for(int i=0; i<numDicts; i++) {
			for(int j=0; j<wordsPerDict; j++) {
				dictlist[i][j] = dict[(wordsPerDict * i) + j];
			}
		}
		
		
		return dictlist;
	}
	
	static String[] generateDict() throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("/usr/share/dict/words"));
		String[] dict = new String[99171];
		for(int i=0; i<99171; i++) {
			dict[i] = br.readLine();
		}

		br.close();
		
		return dict;
	}
}
