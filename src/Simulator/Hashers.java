package Simulator;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashers {

	private String data;
	private MessageDigest messageDigest;
	
	public Hashers(String data) {
		this.data = data;
	}
	
	public BigInteger encryptSHA1() {
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
			messageDigest.update(data.getBytes(),0,data.length());
			BigInteger bigInt = new BigInteger(1, messageDigest.digest());
			return bigInt;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("Error getting SHA-1 algorithm");
			e.printStackTrace();
		}
		return null;
	}
	
	public BigInteger encryptSHA256() {
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(data.getBytes(),0,data.length());
			BigInteger bigInt = new BigInteger(1, messageDigest.digest());
			return bigInt;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("Error getting SHA-256 algorithm");
			e.printStackTrace();
		}
		return null;
	}
	
	public BigInteger encryptMD5() {
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(data.getBytes(),0,data.length());
			BigInteger bigInt = new BigInteger(1, messageDigest.digest());
			return bigInt;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("Error getting MD5 algorithm");
			e.printStackTrace();
		}
		return null;
	}
	 
}
