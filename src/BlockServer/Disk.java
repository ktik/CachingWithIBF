package BlockServer;

public class Disk {
	
	int totalBlocks;
	private Block[] block;
	
	public Disk(int totalBlocks) {
		this.totalBlocks = totalBlocks;
		block = new Block[totalBlocks];
	}
	
	public int findBlock(String block) {
		for (int i=0; i<totalBlocks; i++) {
			if (this.block[i].isEqual(block))
				return i;
		}
		return -1;
	}
	
	public void addBlocks(Block[] newBlocks) {
		for (int i=0; i<newBlocks.length; i++) {
			block[i] = newBlocks[i];
		}
	}
	
	public Block getBlock(int index) {
		return block[index];
	}
	
	public void updateBlock(int index, Block block) {
		this.block[index] = block;
	}
}
