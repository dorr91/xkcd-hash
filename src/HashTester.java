import org.bouncycastle.crypto.digests.Skein;
import org.apache.commons.codec.binary.Hex;


public class HashTester {
	public static void main(String[] args) {
		String test = "asdf";
		Skein skein = new Skein(1024, 1024);
		skein.updateBits(test.getBytes(), 0, test.getBytes().length * 8);
		byte[] hashed = skein.doFinal();
		
		String result = Hex.encodeHexString(hashed);
		System.out.println(test + ":\n" + result);
		System.out.println("Compare:\n" + 
		"7ca7890beb804d4b706b2f75c4e8d6a82ab1db756753e87bdffd36a86a9a593aab07866674fdfb0bd25a3284df38a329aed55bd98d2fd4aed86a0e0396585f5d54e4803e1caba3fd5bf525ee267d47f421348507496e5dc983430cdaa0c6ccc4790d97333c8f9b004f4649e466531f8493adb5350991a43570d300754901bf88"
		);
	}
}
