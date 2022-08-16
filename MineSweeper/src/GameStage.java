/***********************************************************
 	*THIS CLASS IS FOR THE GAME WINDOW
 	*
 	*
 	* @author Brixter Sien M. Pineda
 	* @created_date 2021-11-25 15:00
 	*
 ***********************************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameStage {
	private Scene scene;
	private Stage stage;
	/*Group layout/container is a component which applies no special
	layout to its children. All child components (nodes) are positioned at 0,0*/
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private Element flag;
	private GridPane map;
	private int[][] gameBoard;
	private ArrayList<Element> landCells;

	private boolean flagClicked=false;

	public final static int MAX_CELLS = 81;
	public final static int MAP_NUM_ROWS = 9;
	public final static int MAP_NUM_COLS = 9;
	public final static int MAP_WIDTH = 600;
	public final static int MAP_HEIGHT = 600;
	public final static int CELL_WIDTH = 65;
	public final static int CELL_HEIGHT = 65;
	public final static int FLAG_WIDTH = 60;
	public final static int FLAG_HEIGHT = 60;
	public final static boolean IS_GAME_DONE = false;
	public final static int WINDOW_WIDTH = 700;
	public final static int WINDOW_HEIGHT = 800;

	private int flaggedBomb;
	private int numOfSafe; //number of times a safe was clicked

	public final Image bg = new Image("images/background.jpg",800,800,false,false);

	public GameStage() {
		this.root = new Group();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.WHITE);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.flag = new Element(Element.FLAG_TYPE,this);
		this.map = new GridPane();
		this.landCells = new ArrayList<Element>();
		this.gameBoard = new int[GameStage.MAP_NUM_ROWS][GameStage.MAP_NUM_COLS];
		this.flaggedBomb = 0;
		this.numOfSafe = 0;
	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;
		//draw the background to the canvas at location x=0, y=70
		this.gc.drawImage( this.bg, 0, 70);

		this.initGameBoard();	//initialize game board with 1s and 0s
		this.createMap();

		//set stage elements here
		this.root.getChildren().add(canvas);
		this.root.getChildren().add(map);
		this.root.getChildren().add(this.flag.getImageView());
		this.stage.setTitle("Modified Minesweeper Game");
		this.stage.setScene(this.scene);
		this.stage.show();
	}

	private void initGameBoard(){
		Random r = new Random();
		int numOfBomb = 0, randNum;
		for(int i=0;i<GameStage.MAP_NUM_ROWS;i++){
			for(int j=0;j<GameStage.MAP_NUM_COLS;j++){
				randNum =  r.nextInt(3); // I put the range from 0-2 so that there'll be bomb at the last part of game board
				if(randNum == 1 && numOfBomb < 20){
					this.gameBoard[i][j] = randNum;
					numOfBomb++;
				}
				else{
					this.gameBoard[i][j] = 0;
				}
					//randomize 0 or 1; 0-no bomb, 1-bomb
			}
		}
		for(int i=0;i<GameStage.MAP_NUM_ROWS;i++){
			System.out.println(Arrays.toString(this.gameBoard[i]));//print final board content
		}
	}

	private void createMap(){
		//create 9 land cells
		for(int i=0;i<GameStage.MAP_NUM_ROWS;i++){
			for(int j=0;j<GameStage.MAP_NUM_COLS;j++){

				// Instantiate land elements
				Element land = new Element(Element.LAND_TYPE,this);
				land.initRowCol(i,j);

				//add each land to the array list landCells
				this.landCells.add(land);
			}
		}

		this.setGridPaneProperties();
		this.setGridPaneContents();
	}

	//method to set size and location of the grid pane
	private void setGridPaneProperties(){
		this.map.setPrefSize(GameStage.MAP_WIDTH, GameStage.MAP_HEIGHT);
		//set the map to x and y location; add border color to see the size of the gridpane/map
//	    this.map.setStyle("-fx-border-color: red ;");
		this.map.setLayoutX(GameStage.WINDOW_WIDTH*0.05);
	    this.map.setLayoutY(GameStage.WINDOW_WIDTH*0.15);
	    this.map.setVgap(5);
	    this.map.setHgap(5);
	}

	//method to add row and column constraints of the grid pane
	private void setGridPaneContents(){

		 //loop that will set the constraints of the elements in the grid pane
	     int counter=0;
	     for(int row=0;row<GameStage.MAP_NUM_ROWS;row++){
	    	 for(int col=0;col<GameStage.MAP_NUM_COLS;col++){
	    		 // map each land's constraints
	    		 GridPane.setConstraints(landCells.get(counter).getImageView(),col,row);
	    		 counter++;
	    	 }
	     }

	   //loop to add each land element to the gridpane/map
	     for(Element landCell: landCells){
	    	 this.map.getChildren().add(landCell.getImageView());
	     }
	}

	boolean isBomb(Element element){
		int i = element.getRow();
		int j = element.getCol();

		//if the row col cell value is equal to 1, cell has bomb
		if(this.gameBoard[i][j] == 1){
			System.out.println(">>>>>>>>>Bomb!");
			return true;
		}

		System.out.println(">>>>>>>>>SAFE!");
		return false;
	}

	public boolean isFlagClicked() {
		return this.flagClicked;
	}

	public void setFlagClicked(boolean value) {
		this.flagClicked = value;
	}

	Stage getStage() {
		return this.stage;
	}


	void flashGameOver(){
		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();

		transition.setOnFinished(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				GameOverStage gameover = new GameOverStage();
				stage.setScene(gameover.getScene());
			}
		});
	}

	void flashWinGame(){
		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();

		transition.setOnFinished(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				WinGameStage winGame = new WinGameStage();
				stage.setScene(winGame.getScene());
			}
		});
	}

	public void setGameOver(int num){
		if(num == 1){
			this.flashWinGame(); //player wins
		}
		else{
			this.flashGameOver();
		}
	}

	//get the number of neighboring bombs
	public int checkNeighbors(int row, int col){
		int[][] gameBoard = this.gameBoard;
		int numOfBomb = 0;

		try{
			if(gameBoard[row-1][col] == 1){ //check top
				numOfBomb ++;
			}
		}
		catch(Exception e){}

		try{
			if(gameBoard[row+1][col] == 1){ //check bottom
				numOfBomb ++;
			}
		}
		catch(Exception e){}

		try{
			if(gameBoard[row][col-1] == 1){ //check left
				numOfBomb ++;
			}
		}
		catch(Exception e){}

		try{
			if(gameBoard[row][col+1] == 1){ //check right
				numOfBomb ++;
			}
		}
		catch(Exception e){}

		try{
			if(gameBoard[row-1][col-1] == 1){ //check upper-left
				numOfBomb ++;
			}
		}
		catch(Exception e){}

		try{
			if(gameBoard[row-1][col+1] == 1){ //check upper-right
				numOfBomb ++;
			}
		}
		catch(Exception e){}

		try{
			if(gameBoard[row+1][col+1] == 1){ //check lower-right
				numOfBomb ++;
			}
		}
		catch(Exception e){}

		try{
			if(gameBoard[row+1][col-1] == 1){ //check lower-left
				numOfBomb ++;
			}
		}
		catch(Exception e){}


		return numOfBomb;
	}

	//get the image of the clicked safe cell
	public Image getNumType(int numOfBomb){
		Image type;

		if(numOfBomb == 1){
			type = Element.ONE_IMAGE;
		}
		else if(numOfBomb == 2){
			type = Element.TWO_IMAGE;
		}
		else if(numOfBomb == 3){
			type = Element.THREE_IMAGE;
		}
		else if(numOfBomb == 4){
			type = Element.FOUR_IMAGE;
		}
		else if(numOfBomb == 5){
			type = Element.FIVE_IMAGE;
		}
		else if(numOfBomb == 6){
			type = Element.SIX_IMAGE;
		}
		else if(numOfBomb == 7){
			type = Element.SEVEN_IMAGE;
		}
		else{
			type = Element.EIGHT_IMAGE;
		}

		return type;
	}

	//METHOD THAT SET THE COUNT OF FLAGGED BOMB LANDS
	public void setFlaggedBomb(int n){
		this.flaggedBomb += n;

		if(this.flaggedBomb ==  20){ //if all bombs were flagged
			this.setGameOver(1); //player wins
			System.out.println("YOU WIN!!");
		}
	}

	public int getFlaggedBomb(){
		return this.flaggedBomb;
	}

	//SET THE NUMBER OF CLICKED LANDS THAT WERE NOT BOMB
	public void setNumOfSafe(){
		this.numOfSafe++;

		if(numOfSafe == (GameStage.MAX_CELLS - 20)){//if all safe cell were clicked
			this.setGameOver(1); //player wins
			System.out.println("YOU WIN!!");
		}
	}

	public int getNumOfSafe(){
		return this.numOfSafe;
	}

}

