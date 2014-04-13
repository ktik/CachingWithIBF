package Simulator;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class DataGenerator {
	
	private ArrayList<String> data;
	private int maxLen; //Max length of random string
	private int count; //Number of String to be generated
	
	public DataGenerator(int max, int count) {
		
		this.maxLen = max;
		this.count = count;
		data = new ArrayList<String>();
		generateStrings();
	}
	
	private void generateStrings() {
		
		for (int i=0; i< count; i++) {
			Random random = new Random();
			int nextStringLen = random.nextInt(maxLen);
			String randomString = UUID.randomUUID().toString();
			randomString = randomString.substring(0, nextStringLen);
			data.add(randomString);
		}
	}
	
	public ArrayList<String> getRandomData() {
		return data;
	}	

}
