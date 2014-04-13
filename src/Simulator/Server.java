package Simulator;

import BlockServer.Disk;
import BlockServer.Block;

public class Server {

	private LocalCache serverCache;
	private int serverCacheSize;
	private int serverDiskSize;
	protected Disk disk;
	private int serverId;
	protected Client[] clients;
	private int[] LRUCount;
	private final int MAX_LRU_COUNT = 10;
		
	public Server(int serverId, int cacheSize, int diskSize, Client[] clients) {
		this.serverId = serverId;
		this.serverCacheSize = cacheSize;
		serverCache = new LocalCache(cacheSize);
		
		this.serverDiskSize = diskSize;
		disk = new Disk(diskSize);
		this.clients = clients;
		
		LRUCount = new int[cacheSize];
	}
	
	public int getServerId() {
		return this.serverId;
	}
	
	public LocalCache getServerCache() {
		return serverCache;
	}
	
	public Client[] getClients() {
		return clients;
	}
	
	public boolean initLocalCache(Block[] chunks) {
		if (this.serverCacheSize < chunks.length)
			return false;
		serverCache.addBlocks(chunks);
		for (int i=0; i<chunks.length; i++) {
			LRUCount[i] = MAX_LRU_COUNT;
		}
		return true;
	}
	
	public boolean initDisk(Block[] diskChunks) {
		if (this.serverDiskSize != diskChunks.length)
			return false;
		disk.addBlocks(diskChunks);
		return true;
	}
	
	public int getCacheIndex(String element) {
		return serverCache.findBlock(element);
	}
	
	public Block getDiskBlock(int index) {
		return disk.getBlock(index);
	}
	
	public Block getCacheBlock(int index) {
		return serverCache.getBlock(index);
	}
	
	public void updateServerCache(Block newChunk) {
		//Reduce LRU count of existing chunks
		for (int i=0; i< LRUCount.length; i++) {
			--LRUCount[i];
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
		serverCache.updateBlock(lowestIndex, newChunk);
	}
	
}
