package Simulator;

import BlockServer.Block;
import Bloom.BloomFilter;

public class SBFClient extends Client {
	
	private BloomFilter bloomFilter;
	
	public SBFClient(int clientId, int cacheSize) {
		super(clientId, cacheSize);
	}
	
	public void initializeBloomFilter(Block[] chunks) {
		
		for (Block c: chunks) {
			String chunkData = c.getData();
			bloomFilter.add(chunkData);
		}
	}
	
	public boolean doesBloomFilterContainChunk(Block chunk) {
		
		return bloomFilter.mightContain(chunk.getData());
	}
	
	

}
