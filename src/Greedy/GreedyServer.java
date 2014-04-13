package Greedy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import BlockServer.Block;
import Simulator.Client;
import Simulator.DataGenerator;
import Simulator.LocalCache;
import Simulator.Server;

public class GreedyServer extends Server {
	
	private HashMap<Integer, ArrayList<Integer>> clientCaches;
	private GreedyClient[] greedyClients;
	
	public GreedyServer(int serverId, int cacheSize, int diskSize, GreedyClient[] clients) {
		super(serverId, cacheSize, diskSize, clients);
		this.greedyClients = clients;
		clientCaches = new HashMap<Integer, ArrayList<Integer>>();
		
		for (int i=0; i<clients.length; i++) {
			clientCaches.put(clients[i].getClientId(), new ArrayList<Integer>());
		}
	}
	
	public void storeClientCaches() {
		
		for (GreedyClient client: greedyClients) {
			int c_size = client.getCacheSize();
			int c_id = client.getClientId();
			for (int i=0; i<c_size; i++) {
				int chunkHash = client.getCacheBlock(i).hashCode();
				clientCaches.get(c_id).add(chunkHash);	
			}
		}
	}
	
	public String fwdRequestToClient(String data, GreedyClient requestClient) {
		
		// Check if data is present in server cache 
		LocalCache srv_cache = this.getServerCache();
		if (srv_cache.findBlock(data) != -1)
			return (this.getCacheBlock(srv_cache.findBlock(data)).getData());
		
		// If not check in client caches and forward to client
		int dataHash = data.hashCode();
		Set<Integer> clientIds = clientCaches.keySet();
		for (int k: clientIds) {
			if (clientCaches.get(k).contains(dataHash)) 
				this.greedyClients[k].getRequestFromServer(requestClient, data);
		}
		int disk_index = disk.findBlock(data);
		Block diskData = disk.getBlock(disk_index);
		
		return diskData.getData();
	}
	
	public static void main(String[] args) {
		int srv_id = 1;
		int cache_size = 100;
		int diskSize = 100;
		GreedyClient[] gClients = new GreedyClient[1];
		GreedyClient gClient = new GreedyClient(10, 10);
		gClients[0] = gClient;
		DataGenerator dg = new DataGenerator(30, 100);
		ArrayList<String> data = dg.getRandomData();
		System.out.println(data.get(0));
		Block[] blocks = new Block[100];
		int i=0;
		
		for (String d: data) {
			if (d.length() > 0) {
				Block b = new Block(d);
				blocks[i] = b;
				// System.out.println(blocks[i]);
				i++;
			}
		}
		
		GreedyServer gServer = new GreedyServer(srv_id, cache_size, diskSize, gClients);
		if (!gServer.initLocalCache(blocks))
			System.out.println("Failed server cache init");
		System.out.println("Initiaised server cache");
		int index = gServer.getCacheIndex(data.get(0));
		System.out.println("An index : "+index);
		//gServer.initDisk(blocks);
		Block readblock = gServer.getCacheBlock(0);
		System.out.println("Read block complete");
		String dat = readblock.getData();
		System.out.println(dat);
	}

}
