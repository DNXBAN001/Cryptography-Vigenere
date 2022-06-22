import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Model.Vigenere;
import View.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Bandile Danxa
 *
 */
public class Main extends Application
{

	public static void main(String[] args) throws IOException
	{	
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("/View/CryptoView.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("Vigenere Cipher Assignment");
		primaryStage.show();
	}

}
