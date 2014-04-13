package Simulator;

import BlockServer.Block;

public class Client {
	
	private int cacheSize;
	private int clientId;
	private LocalCache lclCache;
	protected int[] LRUCount; // Keeps the LRU count for each block present in local cache
	private final int MAX_LRU_COUNT  = 10;
	
	public Client(int clientId, int cacheSize) {
		this.cacheSize = cacheSize;
		this.lclCache = new LocalCache(cacheSize);
		LRUCount = new int[cacheSize];
		this.clientId = clientId;
	}
	
	public int getClientId() {
		return this.clientId;
	}
	
	public int getCacheSize() {
		return this.cacheSize;
	}
	
	public int getLRUCount(int cacheIndex) {
		return LRUCount[cacheIndex];
	}
	
	public boolean initLocalCache(Block[] chunks) {
		if (this.cacheSize != chunks.length)
			return false;
		lclCache.addBlocks(chunks);
		return true;
	}
	
	public int getCacheIndex(String data) {
		return lclCache.findBlock(data);
	}
	
	public Block getCacheBlock(int index) {
		return lclCache.getBlock(index);
	}
	
	public void replaceLRUBlock(Block newBlock) {
		
		//Lower LRU Count for all blocks by 1
		for (int i=0; i<LRUCount.length; i++) {
			if (LRUCount[i] > 0) {
				--LRUCount[i];
			}
		}
		
		//Find chunk with lower LRUCunt
		int lowestIndex = 0;
		int lowestCount = MAX_LRU_COUNT;
		
		for (int i=0; i<LRUCount.length; i++) {
			if (LRUCount[i] < lowestCount) {
				lowestIndex = i;
				lowestCount = LRUCount[i];
			}
		}
		
		//Replace chunk with lower LRU count in local caches
		LRUCount[lowestIndex] = MAX_LRU_COUNT;
		lclCache.updateBlock(lowestIndex, newBlock);
	}
	
}
