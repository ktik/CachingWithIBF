package BlockServer;

public class Block implements Comparable<Block>{
	
	private String data;
	private int circulationCount;
	
	public Block(String data) {
		this.data = data;
		this.circulationCount = 0;
	}
	
	public String getData() {
		return this.data;
	}
	
	public boolean isEqual(String data) {
		if (this.data.equals(data))
			return true;
		return false;
	}
	
	@Override
	public int compareTo(Block chunk) {
		return this.data.compareTo(chunk.getData());
	}
	
	@Override
	public String toString() {
		return this.data;
	}
	

}
