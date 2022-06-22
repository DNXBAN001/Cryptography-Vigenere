package Model;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * @author Bandile Danxa
 *
 */
public class Vigenere implements Utilities
{
	private String plainText = "";  // store the plain text
	private String key; // store the encryption's key
	private String cipherText = ""; // store the cipher text
	private boolean started = true;
	private String words;
	private String[] wordsArray;
	
	/**
	 * 
	 */
	public Vigenere()
	{
		loadWords();
		wordsArray = words.split("[\n]");
	}
	
	public Vigenere(String text, String keyword)
	{
		this.plainText = text;
		this.key = keyword;
	}

	/**
	 * @return
	 */
	public boolean isStarted()
	{
		return started;
	}

	/**
	 * @param started
	 */
	public void setStarted(boolean started) 
	{
		this.started = started;
	}

	/**
	 * This method retrieves the current plain text
	 * @return <code> String </code> current plain text
	 */
	public String getPlainText()
	{
		return plainText;
	}

	/**
	 * This method mutates/changes the plain text
	 * @param plainText: input a text
	 */
	public void setPlainText(String plainText)
	{
		this.plainText = "";
		this.plainText = plainText;
	}

	/**
	 * This method acquires the current encryption's key.
	 * @return <code>key</code>: specify the key
	 */
	public String getKey() 
	{
		return key;
	}

	/**
	 * This method changes/mutates the encryption's key.
	 * @param key: input the key ranging 0 - 25.
	 */
	public void setKey(String key) 
	{
		this.key = key;
	}

	/**
	 * This method gives access to the cipher text
	 * @return <code>String</code>: specify a string
	 */
	public String getCipherText() 
	{
		return cipherText;
	}
	
	/**
	 * This method set the cipher text
	 * @param cipherText: input a string
	 */
	public void setCipherText(String cipherText)
	{
		this.cipherText = "";
		this.cipherText = cipherText;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getWords()
	{
		return words;
	}
	public String [] getWordsArray()
	{
		return this.wordsArray;
	}

	/**
	 * This method allow to set the plaintext and as well as the cipher after
	 * encryption or decryption.
	 * @param message : plaintext message to apply cryptography
	 */
	public void setCryptoSettings(String message)
	{
		this.setPlainText(message);
		this.encryption();
		this.decryption();
	}

	/**
	 * This method encrypts the string message the given key
	 */
	public void encryption()
	{
		
		this.cipherText = ""; // clears the cipher text
		this.setStarted(true);
		
		// extracts each individual character from the string
		char [] plainCharacter = this.plainText.toCharArray();
		char [] key = this.prolongedKeyword(this.plainText, this.getKey()).toCharArray();
		
		int j = -1;
		for(int i = 0; i < plainCharacter.length; i++)
		{
			if(Character.isLetter(plainCharacter[i]))
			{
				j++;
			}
			getCryptography(plainCharacter[i], key[j]);
		}
	}

	/**
	 * This method decrypts the message using the given key.
	 * This reverts the encryption's process.
	 */
	public void decryption()
	{
		this.cipherText = ""; // clears the cipher text
		this.setStarted(false);
		
		char [] plainCharacters = this.plainText.toCharArray();
		char [] key = this.prolongedKeyword(this.plainText, this.getKey()).toCharArray();
		
		int j = -1;
		for(int i = 0; i < plainCharacters.length; i++)
		{
			if(Character.isLetter(plainCharacters[i]))
			{
				j++;
			}
			getCryptography(plainCharacters[i], key[j]);
			
		}
	}
	
	/**
	 * 
	 * @return key
	 */
	private int scaleKeyCharacter(char keyChar)
	{
		int key;
		if(Character.isUpperCase(keyChar))
		{
			key = keyChar - LETTER_UPPER;
		}
		else
		{
			key = keyChar - LETTER_LOWER;
		}
		return this.started? key: - key;
	}
	
	/**
	 * 
	 * @param ch
	 */
	private void convert(char ch1, char ch2)
	{
		//initialize shift and see if it is within 0-25
		byte shift = (byte)scalekey(ch1, ch2); //3
		
		//if it is outside then set it to be within the range
		byte pos = updateRange(shift);//pos = 3
		
		//collect the ciphertext using all the characters
		this.cipherText += Character.toString((char)(Character.isUpperCase(ch1)? pos + LETTER_UPPER: pos + LETTER_LOWER));
	
	}

	/**
	 * This method is for keeping the key within the range 0-25
	 * @param ch
	 * @return
	 */
	private int scalekey(char ch1, char ch2)
	{
		return Character.isUpperCase(ch1)? ch1 - LETTER_UPPER + scaleKeyCharacter(ch2):ch1 - LETTER_LOWER + scaleKeyCharacter(ch2);
	}

	
	
	/**
	 * This method generates a prolonged keyword depending on the plaintext's length
	 * @param plainword
	 * @param keyword
	 * @return
	 */
	public String prolongedKeyword(String plainword, String keyword)
	//private String prolongedKeyword(String plainword, String keyword)
	{
		String prolongedKeyword = "";
		try
		{
			int prolongFactor = (plainword.length()/keyword.length())+1;
			for(int i = 0; i < prolongFactor; i++)
			{
				prolongedKeyword += keyword;
			}
		}
		catch(ArithmeticException ex)
		{
			ex.printStackTrace();
		}
		
		return prolongedKeyword;
	}
	
	
	private void getCryptography(char index, char ch2)
	{
		if(!Character.isLetter(index))
		{
			this.cipherText += index;
			return;
		}
		this.convert(index, ch2);
	}
	
	
	/**
	 * This method reads records from the file and stores it into a string
	 */
	private void loadWords()
	{
		try(Stream<String> lines = Files.lines(Paths.get("words.txt")))
		{
			words = lines.collect(Collectors.joining("\n"));
		}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText(e.getMessage());
			alert.show();
		}
	}
	
	
}

