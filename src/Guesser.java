import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Guesser extends Thread {
	static volatile int best = 1000;
	String[] dict;
	Integer threadcount;

	String baseParams = "hashable=";
	String params;
	
	public Guesser(String[] dict, Integer threadcount) {
		this.dict = dict;
		this.threadcount = threadcount;
	}
	
	public void run() {
		String bestWord = "";
		
		for(String word : dict) {
			int score = guess(word);
			if(score < best) {
				best = score;
				bestWord = word;
				System.out.println("New best: " + word + " = " + score);
			}
		}
		threadcount--;
	}
	
	public int guess(String s) {
		try {
			URL url = new URL("http://almamater.xkcd.com/?edu=cmu.edu");
			params = baseParams + s;
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");

			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(params);
			wr.flush();
			
			
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(
							connection.getInputStream()));

			reader.readLine();
			String line = reader.readLine();
			//I'm so sorry for hardcoding this.
			int difference = Integer.parseInt(line.substring(303,306));
		
			wr.close();
			reader.close();
			
			return difference;
		} catch (IOException e) {
			e.printStackTrace();
			return 1000;
		}
	}
}
