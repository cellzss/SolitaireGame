package assignment2;
/*
 * @author Selina Gao 
 * 
 */

import java.util.Arrays;

public class SolitaireCipher {
	public Deck key;
	
	public SolitaireCipher (Deck key) {
		this.key = new Deck(key); // deep copy of the deck
	}
	
	/* 
	 * TODO: Generates a keystream of the given size
	 */
	public int[] getKeystream(int size) {
		int[] keyStreamArray = new int[size];
		
		for(int i=0; i<size; i++) {
			int newKey = key.generateNextKeystreamValue();
			keyStreamArray[i]=newKey;
		}
		return keyStreamArray;
		
	}
		
	/* 
	 * TODO: Encodes the input message using the algorithm described in the pdf.
	 */
	public String encode(String msg) {

		String newMsg = msg.toUpperCase();
		String result = "";
		for(int i=0; i<newMsg.length();i++) {
			char newChar = newMsg.charAt(i);
			if(newChar >= 'A' && newChar <= 'Z') {
				result += newChar;
			}
		}
		
		String finalresult = "";
		int[] tempArray = getKeystream(result.length());
		
		for(int i=0; i<result.length();i++) {
			char ch = result.charAt(i);
			int chValue = ch;
			int tempArrayVal = tempArray[i] % 26;
			int newChValue = chValue + tempArrayVal;
			char newChChar = (char) newChValue;
					
			int aValue = 'A';
			int zValue = 'Z';
			if(newChValue <= zValue) {
						finalresult += newChChar;
			}
			else {
				int newnewChValue = newChValue - zValue;
				int newPosition = (aValue + newnewChValue)-1;
				char positionChar = (char) newPosition;
				finalresult += positionChar;
				}
			
		}
		return finalresult;
	}
	
	/* 
	 * TODO: Decodes the input message using the algorithm described in the pdf.
	 */
	public String decode(String msg) {
		String decodeMsg = "";
		int[] publicKey = getKeystream(msg.length());
		
		for(int i=0; i<msg.length(); i++) {
			char ch2 = msg.charAt(i);
			int ch2Value = ch2;
			int publicKeyVal = publicKey[i] % 26;
			int newPublicKeyVal = ch2Value - publicKeyVal;
			char newPublicKeyChar = (char)newPublicKeyVal;
			int aValue = 'A';
			int zValue = 'Z';
			if(newPublicKeyChar >= aValue) {
						decodeMsg += newPublicKeyChar;
			}
			else {
				int newnewPublicKeyValue = newPublicKeyVal - aValue;
				int newPosition = (zValue + newnewPublicKeyValue)+1;
				char keyposition = (char) newPosition;
				decodeMsg += keyposition;
			}
			
			
		}
		return decodeMsg;
	}
	
}
