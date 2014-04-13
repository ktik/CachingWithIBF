package Bloom;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import Simulator.DataGenerator;
import Simulator.Hashers;

public class IBF extends BloomFilter {
	
	private int[] bloomFilter;
	private int M = 8;
	private int P = 5;
	private int importanceLength = 20;
	
	public IBF(int m) {
		super(m);
		bloomFilter = new int[m];
	}
	
	public void add(String data) {
		
		if (!mightContain(data)) {
			
			ArrayList<Integer> pInd = new ArrayList<Integer>();
			pInd = decrementMValues();
					
			for (int i: pInd) {
				if (bloomFilter[i] >= 1)
					bloomFilter[i] -= 1;
			}
		
			int[] ind = getHashIndexes(data);
			for (int i: ind) {
				if (bloomFilter[i] < importance(data))
					bloomFilter[i] = importance(data);
			}
		}
				
	}
	
	private int importance(String data) {
		
		int dataLen = data.length();
		if (dataLen < importanceLength)
			return M/2;
		return M;
	}
	
	private ArrayList<Integer> decrementMValues() {
		ArrayList<Integer> index = new ArrayList<Integer>();
		for (int i=0; i< P; i++) {
			Random random = new Random();
			int ind = random.nextInt() % this.m;
			ind = Math.abs(ind);
			index.add(ind);
			if (bloomFilter[ind] > 0)
				bloomFilter[ind] += -1;
		}
		return index;
	}
	
	public boolean mightContain(String data) {
		int[] indexes = getHashIndexes(data);
		for (int i: indexes) {
			if (bloomFilter[i] == 0)
				return false;
		}
		return true;
	}
	
	private int[] getHashIndexes(String data) {
		int[] indexes = new int[3];
		
		Hashers hash = new Hashers(data);
		BigInteger sha1code = hash.encryptSHA1();
		int sha1 = sha1code.intValue() % super.m;
		sha1 = Math.abs(sha1);
		// System.out.println("Hashcode: "+sha1);
		indexes[0] = sha1;
		
		BigInteger sha256code = hash.encryptSHA256();
		int sha256 = sha256code.intValue() % this.m;
		sha256 = Math.abs(sha256);
		// System.out.println("Hashcode: "+sha256);
		indexes[1] = sha256;
		
		BigInteger md5code = hash.encryptMD5();
		int md5 = md5code.intValue() % this.m;
		md5 = Math.abs(md5);
		// System.out.println("Hashcode: "+md5);
		indexes[2] = md5;
		return indexes;
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
		IBF ibf = new IBF(100);
		DataGenerator dg = new DataGenerator(30,100);
		ArrayList<String> data = dg.getRandomData();
		
		for (String s: data) {
			ibf.add(s);
		}
		DataGenerator testDg = new DataGenerator(30,1000);
		ArrayList<String> testData = testDg.getRandomData();
		int falseP = ibf.computeFalsePositiveRate(data, testData);
		System.out.println("Standard Bloom False Positive: "+falseP);
		
//		ibf.add("Kartik");
//		ibf.add("Jana");
//		System.out.println("String jana: "+ibf.mightContain("Janaki"));
//		System.out.println("String jana: "+ibf.mightContain("Kartik"));
	}

}
