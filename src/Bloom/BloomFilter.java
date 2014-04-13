package Bloom;
import java.util.ArrayList;
import java.util.BitSet;
import java.math.BigInteger;

import Simulator.DataGenerator;
import Simulator.Hashers;

public class BloomFilter {
	
	private BitSet bitset; //bit array to store the hash bits
	protected int m; //length of bloom filter
	
	/* Create an empty Bloom filter of size m-cells, k hash functions*/
	public BloomFilter(int m) {
		this.m = m;
		bitset = new BitSet(this.m);
	}
	
	public BitSet getBloomFilterState() {
		
		return bitset;
	}
	
	public void add(String e) {
		Hashers hash = new Hashers(e);
		BigInteger sha1code = hash.encryptSHA1();
		int sha1 = sha1code.intValue() % this.m;
		sha1 = Math.abs(sha1);
		// System.out.println("Hashcode: "+sha1);
		bitset.set(sha1);
		
		BigInteger sha256code = hash.encryptSHA256();
		int sha256 = sha256code.intValue() % this.m;
		sha256 = Math.abs(sha256);
		// System.out.println("Hashcode: "+sha256);
		bitset.set(sha256);
		
		BigInteger md5code = hash.encryptMD5();
		int md5 = md5code.intValue() % this.m;
		md5 = Math.abs(md5);
		// System.out.println("Hashcode: "+md5);
		bitset.set(md5);
		
	}
	
	public boolean mightContain(String q) {
		try {
			Hashers h = new Hashers(q);
			BigInteger sha1 = h.encryptSHA1();
			int j = sha1.intValue() % this.m;
			j = Math.abs(j);
			// System.out.println("SHA1 hash: "+sha1);
			// System.out.println("Integer value "+j);
			if (bitset.get(j))
				return true;
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return false;
	}
	
	public int computeFalsePositiveRate(ArrayList<String> data, ArrayList<String> test) {
		int falsePositiveCount = 0;
		
		for (String elem: test) {
			if (mightContain(elem)) {
				if (!data.contains(elem)) {
					falsePositiveCount++;
				}
			}
		}
		return falsePositiveCount;
	}
	
	
	
	public static void main(String[] args) {
		BloomFilter BF = new BloomFilter(1000);
		IBF ibf = new IBF(1000);
		
		DataGenerator dg = new DataGenerator(30,5000);
		ArrayList<String> data = dg.getRandomData();
		
		for (String s: data) {
			BF.add(s);
			ibf.add(s);
		}
		DataGenerator testDg = new DataGenerator(30,3000);
		ArrayList<String> testData = testDg.getRandomData();
		//testData.addAll(data);
		int sfalseP = BF.computeFalsePositiveRate(data, testData);
		System.out.println("Standard Bloom False Positive: "+sfalseP);
		
		int ifalseP = ibf.computeFalsePositiveRate(data, testData);
		System.out.println("IBF False Positive: "+ifalseP);
		
		// BF.add("Kartik");
		// BF.add("Jana");
		// System.out.println("String: "+BF.mightContain(d1+"kartik102"));
		// System.out.println("String: "+BF.mightContain(d2));
	}

}
