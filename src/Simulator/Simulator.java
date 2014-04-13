package Simulator;

import java.util.ArrayList;

import BlockServer.Block;
import Greedy.GreedyClient;

public class Simulator {
	
	public static void main() {
		
		// Greedy Implementation test
		
		int _numClients = 5;
		int _clientCacheSize = 15;
		ArrayList<Client> clientObjectsList = new ArrayList<Client>();
		
		for (int i=0; i<_numClients; i++) {
			Client client = new GreedyClient(i, _clientCacheSize);
			Block[] chunks = new Block[100];
			DataGenerator dg = new DataGenerator(30, 100);
			ArrayList<String> data = dg.getRandomData();
			int j=0;
			
			for (String d: data) {
				Block chunk = new Block(d);
				chunks[j++] = chunk;
			}
			
			client.initLocalCache(chunks);
		}
		
		//Create Server
		// Server greedyServer = new GreedyServer(1, )
	}

}
