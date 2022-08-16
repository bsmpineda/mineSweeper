/***********************************************************
 	*
 	* @author Brixter Sien M. Pineda
 	* @created_date 2021-11-25 16:30
 	*
 ***********************************************************/

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameOverStage {
	private StackPane pane;
	private Scene scene;
	private GraphicsContext gc;
	private Canvas canvas;

	GameOverStage(){
		this.pane = new StackPane();
		this.scene = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
//		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
//		this.gc = canvas.getGraphicsContext2D();
		this.setProperties();

	}
	
	//for gif background of the stage
    private ImageView createView() {
        Image bg = new Image("images/gameOver.gif");	// We cannot directly add an Image in the Scene
        ImageView view = new ImageView();	// An ImageView can be used as leaf node in the Scene
        view.setFitHeight(GameStage.WINDOW_HEIGHT*.75);
        view.setFitWidth(GameStage.WINDOW_WIDTH);
        view.setImage(bg);

        return view;
    }

	private void setProperties(){
//		this.gc.setFill(Color.CORAL);										//set font color of text
//		this.gc.fillRect(0,0,GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
//		Font theFont = Font.font("Times New Roman",FontWeight.BOLD,30);//set font type, style and size
//		this.gc.setFont(theFont);
//
//		this.gc.setFill(Color.RED);										//set font color of text
//		this.gc.fillText("Game Over!", GameStage.WINDOW_WIDTH*0.3, GameStage.WINDOW_HEIGHT*0.3);						//add a hello world to location x=60,y=50
		
		//Image used for the button
		Image img = new Image("images/exit.png");
	    ImageView view = new ImageView(img);
	    view.setFitHeight(50);
	    view.setPreserveRatio(true);
		
		Button exitbtn = new Button();
		
		exitbtn.setPrefSize(50, 50);
		
	   //Setting a graphic to the button
	    exitbtn.setGraphic(view);
	
		this.addEventHandler(exitbtn);

		ImageView imgView = this.createView();//bg
		
		//set alignment and margin of exit button
		StackPane.setAlignment(exitbtn, Pos.BOTTOM_CENTER);
        StackPane.setMargin(exitbtn, new Insets(GameStage.WINDOW_HEIGHT*0.03));
        
        
		pane.getChildren().add(imgView);
		pane.getChildren().add(exitbtn);

	}

	private void addEventHandler(Button btn) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				System.exit(0);
			}
		});

	}

	Scene getScene(){
		return this.scene;
	}
}
