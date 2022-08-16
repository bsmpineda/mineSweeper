/***********************************************************
 	*
 	* @author Brixter Sien M. Pineda
 	* @created_date 2021-11-25 16:00
 	*
 ***********************************************************/


import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage){		
		GameStage theGameStage = new GameStage();
		theGameStage.setStage(stage);
	}

}
