package View;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import Model.Utilities;
import Model.Vigenere;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * @author Bandile Danxa
 *
 */
public class ViewController implements Initializable, Utilities
{
	
	
	@FXML private TextArea contentTextArea;
	@FXML private TextField openTextField;
	@FXML private RadioButton encryptRadio;
	@FXML private ToggleGroup group;
	@FXML private RadioButton decryptRadio;
	@FXML private ToggleGroup cryptoGroup;
	@FXML private RadioButton vigenereCrypto;
	@FXML private RadioButton normalCrypto;
	private String path; // path of file selected
	private FileChooser fileChooser; // file dialog box
	private String fileContent;
	private Vigenere vigenere;// implementation of vigenere cryptography
    
    

	/**
     * This method receives the event from UPLOAD button and process
     * with the file's loading.
	 * @throws IOException 
     */
	@FXML 
	public void openUpload(ActionEvent event) throws IOException
	{
		 fileChooser = defaultDirectory(fileChooser);
		 path = getFileOrDirectoryPath();
		 vigenere.setStarted(false);

		if(path != null)
		{
			openTextField.setText(path);
			getKey();
		}
	}//////////////////////////////////////////////////////////////////


	/**
     * This method get the hidden from the encrypted file
	 * and upload the textArea with the decrypted details
	 * from the file
	 * @throws IOException 
     */
	private void getKey() throws IOException
	{
		openFile();
		findKey();
		populateGUI();
	}

	/**
	 * @throws IOException 
	 * 
	 */
	private void populateGUI() throws IOException 
	{
		fillTextArea();
		vigenere.setCryptoSettings(contentTextArea.getText());
		decideCryptography();
	}


	/**
     * This method fills the textArea the decrypted content of
     * the file.
	 * @throws IOException 
     */
	private void fillTextArea() throws IOException 
	{
		openFile();
		contentTextArea.setText("");
		contentTextArea.appendText(fileContent);
	}

	/**
     * This method searches for encryption's key and the caesar
     * cryptography's object with the found key.
     */
	//public void findKey()
	private void findKey() 
	{
		vigenere.setPlainText(fileContent);
		String [] key = vigenere.getWordsArray();//array to store the list of words that are possible keys
		for(int i = 0; i < key.length; i++)
		{
			vigenere.setKey(key[i]);
			//vigenere.encryption();
			if(vigenere.getPlainText().contains("Upon which the Wolf seized him and ate him up"))
			{
				vigenere.setKey("a");
				return;
			}
			
			vigenere.decryption();
			
			if(vigenere.getCipherText().contains("Upon which the Wolf seized him and ate him up"))
			{
				System.out.println("The final keyword is : "+ vigenere.getKey());
				return;
			}
			
		}
		System.out.println("Keyword not found");
		System.out.println("Done...");
	}

	/**
     * This method displays a file dialog box and the get the path of
     * selected file.
     * @return an <code> String</code> specifying file's path.
     */
	private String getFileOrDirectoryPath()
	{
		File file = null;
		file = this.fileChooser.showOpenDialog(null);

		return file != null?file.getAbsolutePath(): null;
	}

	/**
	 * This method process the vigenere cryptography, whether encryption
	 * or decryption. Receives event from encryption/decryption radioButtons
	 * @param event : used the event from radioButtons
	 */
	@FXML public void processVigenere(ActionEvent event)
	{
		updateContent(chooseCrypto());
	}//////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * This method determines which message to display based of the user's selection
	 * @return message: message displayed in the text area.
	 */
	private String chooseCrypto()
	{
		String message = contentTextArea.getText();

		if(decryptRadio.isSelected() && normalCrypto.isSelected()&& vigenere.isStarted() )
		{
			message = vigenere.getCipherText(); 
			vigenere.setStarted(false);
		}
		else if(decryptRadio.isSelected() && vigenereCrypto.isSelected())
		{
			message = vigenere.getCipherText();
		}
		else if(encryptRadio.isSelected() && normalCrypto.isSelected()&& vigenere.isStarted())
		{
			message = vigenere.getPlainText();
			vigenere.setStarted(false);
		}
		else if(encryptRadio.isSelected() && vigenereCrypto.isSelected())
		{
			message = vigenere.getPlainText();
		}
		return message;
	}

	/**
	 * This method update the textArea's content after encryption or
	 * decryption.
	 */
	private void updateContent(String message) 
	{
		 contentTextArea.setText(message);
	}

	/**
	 * This method receives event from caesar and normal radio Buttons
	 * and triggers the cryptography's process
	 */
	private void decideCryptography()
	{
		ActionEvent event = null;
		if(vigenereCrypto.isSelected())
		{
			processVigenere(event);
		}
		if(!vigenereCrypto.isSelected())
		{
			updateContent(chooseCrypto());
		}
	}

	/**
	 * This method opens the selected file using the Scanner's object
	 * @throws IOException 
	 */
	private void openFile() throws IOException
	{
		try(Stream<String> stream = Files.lines(Paths.get(path)))
		{
			fileContent = stream.collect(Collectors.joining("\n"));
		}
		catch (FileNotFoundException e)
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Open File Failure");
			alert.setContentText(e.getMessage());
			alert.show();
		}
	}

	/**
     * This method initializes the view with defaults settings
     * @param URL arg0: provide the view's location
     * @param ResourceBundle arg1: bundle of the view
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		vigenere = new Vigenere();//model
		openTextField.setPromptText("Open source file here");
		encryptRadio.setToggleGroup(group);
		decryptRadio.setToggleGroup(group);
		vigenereCrypto.setToggleGroup(cryptoGroup);

		encryptRadio.selectedProperty().addListener(event-> decideCryptography());
		decryptRadio.selectedProperty().addListener(event-> decideCryptography());
	}
    
    
}
