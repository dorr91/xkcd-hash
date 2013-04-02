package xkcd.hash;

import java.io.UnsupportedEncodingException;

import org.bouncycastle.crypto.digests.Skein;


public class HashTester {
	public static void main(String[] args) {
		String targetString = "5b4da95f5fa08280fc9879df44f418c8f9f12ba424b7757de02bbdf" +
				"bae0d4c4fdf9317c80cc5fe04c6429073466cf29706b8c25999ddd2f6540d44" +
				"75cc977b87f4757be023f19b8f4035d7722886b78869826de916a79cf9c94cc" +
				"79cd4347d24b567aa3e2390a573a373a48a5e676640c79cc70197e1c5e7f902" +
				"fb53ca1858b6";
		
		byte[] target = new byte[128];
		
		//set up the byte[] for the target hash
		for(int i=0; i<targetString.length(); i+=2) {
			int b = Integer.parseInt(targetString.substring(i, i+2), 16);
			target[i/2] = (byte)b;
		}

		String test = "fubar";
		try {
			byte[] bytes = test.getBytes("UTF-8");
			Skein skein = new Skein(1024, 1024);

			skein.updateBits(bytes, 0, bytes.length * 8);
			byte[] hashed = skein.doFinal();

			System.out.println("Diff between hash of " + test + " and target is " + Guesser.compareByteArrays(hashed, target));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
