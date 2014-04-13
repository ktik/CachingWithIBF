package Greedy;

import Simulator.Client;
import BlockServer.Block;

public class GreedyClient extends Client {
	
	public GreedyClient(int clientId, int cacheSize) {
		super(clientId, cacheSize);
		
	}
	
	public void getRequestFromServer(GreedyClient requestingClient, String data) {
		int index = this.getCacheIndex(data);
		Block requestedData = this.getCacheBlock(index);
		requestingClient.rcvBlockFromClient(requestedData);
		
	}
	
	public void rcvBlockFromClient(Block data) {
		//Add received block to local cache
		this.replaceLRUBlock(data);
	}
	
	public void requestDataFromServer(String data) {
		
	}

}
