package Model;

import java.nio.file.Paths;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public interface Utilities {
	int LOWER_BOUND = 0;
	int UPPER_BOUND = 26;
	char LETTER_UPPER = 'A';
	char LETTER_LOWER = 'a';
	
	/**
	 * This method shift of the letter in such a way that it
	 * remains within 0 - 25.
	 * @param shift: input the current shift of letter
	 * @return <code>byte</code> update the shift
	 */
	default byte updateRange(byte shift)
	{
		if(shift < LOWER_BOUND)
		{
			shift += UPPER_BOUND;
		}
		else if(shift >= UPPER_BOUND)
		{
			shift %= UPPER_BOUND;
		}
		return shift;
	}
	
	/**
	 * This method is for choosing file from the project default folder
	 */
	default FileChooser defaultDirectory(FileChooser fileChooser)
	{
		fileChooser = new FileChooser();
		 fileChooser.setInitialDirectory(Paths.get(".").toAbsolutePath().toFile());
		 fileChooser.getExtensionFilters().addAll(// filters the files...
		         new ExtensionFilter("Binary Files", "*.dat"),
		         new ExtensionFilter("Comma Separated Files", "*.csv"),
		         new ExtensionFilter("Text Files", "*.txt"));
		 return fileChooser;
	}
	
	
}

