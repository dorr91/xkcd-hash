import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.digests.Skein;

public class Guesser extends Thread {
	static volatile int best = 1000;
	String current;
	Integer threadcount;

	Skein skein = new Skein(1024, 1024);
		
	/* target hash */
	String targetString = "5b4da95f5fa08280fc9879df44f418c8f9f12ba424b7757de02bbdf" +
			"bae0d4c4fdf9317c80cc5fe04c6429073466cf29706b8c25999ddd2f6540d44" +
			"75cc977b87f4757be023f19b8f4035d7722886b78869826de916a79cf9c94cc" +
			"79cd4347d24b567aa3e2390a573a373a48a5e676640c79cc70197e1c5e7f902" +
			"fb53ca1858b6";
	
	byte[] target = new byte[128];
		
	public Guesser(String initial) {
		current = initial;
		
		//set up the byte[] for the target hash
		for(int i=0; i<targetString.length(); i+=2) {
			int b = Integer.parseInt(targetString.substring(i, i+2), 16);
			target[i/2] = (byte)b;
		}
	}
	
	public void run() {
		while(true) {
			skein.updateBits(current.getBytes(), 0, current.getBytes().length * 8);
			byte[] hashed = skein.doFinal();
			int diff = compare(target, hashed);
			if(diff < best) {
				best = diff;
				System.out.println(diff + ": " + current);
				try{
					send(current);
				} catch(IOException e) {
					System.out.println("failed to send.");
					e.printStackTrace();
				}
			}
			
			current = Hex.encodeHexString(hashed)
					+ String.valueOf(System.currentTimeMillis()).toString();
		}
	}
	
	private int compare(byte[] b1, byte[] b2) {
		int count = 0;
		for (int i=0; i<b1.length; i++) {
			byte diff = (byte)(b1[i] ^ b2[i]);
			for(int j=0; j<8; j++) {
				if((diff & (1 << j)) > 0) count ++;
			}
		}
		return count;
	}
	
	private void send(String s) throws IOException {
		URL url = new URL("http://almamater.xkcd.com/?edu=cmu.edu");
		HttpURLConnection c = (HttpURLConnection) (url.openConnection());
		c.setDoOutput(true);
		c.setRequestMethod("POST");
		
		OutputStreamWriter writer = new OutputStreamWriter(c.getOutputStream());
		writer.write("hashable=" + s);
		writer.flush();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
		
		writer.close();
		reader.close();
	}
}
