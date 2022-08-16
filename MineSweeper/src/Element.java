/***********************************************************
 	*
 	* @author Brixter Sien M. Pineda
 	* @created_date 2021-11-25 16:00
 	*
 ***********************************************************/

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Element {
	private String type;
	protected Image img;
	protected ImageView imgView;
	protected GameStage gameStage;
	protected int row, col;

	public final static Image FLAG_IMAGE = new Image("images/flag.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image BOMB_IMAGE = new Image("images/bomb.gif",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image LAND_IMAGE = new Image("images/land.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image ONE_IMAGE =  new Image("images/one.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image TWO_IMAGE =  new Image("images/two.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image THREE_IMAGE =  new Image("images/three.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image FOUR_IMAGE =  new Image("images/four.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image FIVE_IMAGE =  new Image("images/five.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image SIX_IMAGE =  new Image("images/six.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image SEVEN_IMAGE =  new Image("images/seven.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image EIGHT_IMAGE =  new Image("images/eight.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);

	public final static int IMAGE_SIZE = 65;

	public final static String FLAG_TYPE = "flag";
	public final static String BOMB_TYPE = "bomb";
	public final static String LAND_TYPE = "land";
	public final static String LAND_FLAG_TYPE = "landToFlag";
	public final static String ONE_TYPE = "one";
	public final static String TWO_TYPE = "two";
	public final static String THREE_TYPE = "three";
	public final static String FOUR_TYPE = "four";
	public final static String FIVE_TYPE = "five";
	public final static String SIX_TYPE = "six";
	public final static String SEVEN_TYPE = "seven";
	public final static String EIGHT_TYPE = "eight";

	public Element(String type, GameStage gameStage) {
		this.type = type;
		this.gameStage = gameStage;

		// load image depending on the type
		switch(this.type) {
			case Element.FLAG_TYPE: this.img = Element.FLAG_IMAGE; break;
			case Element.LAND_TYPE: this.img = Element.LAND_IMAGE; break;
			case Element.BOMB_TYPE: this.img = Element.BOMB_IMAGE; break;
			case Element.ONE_TYPE: this.img = Element.ONE_IMAGE; break;
			case Element.TWO_TYPE: this.img = Element.TWO_IMAGE; break;
			case Element.THREE_TYPE: this.img = Element.THREE_IMAGE; break;
			case Element.FOUR_TYPE: this.img = Element.FOUR_IMAGE; break;
			case Element.FIVE_TYPE: this.img = Element.FIVE_IMAGE; break;
			case Element.SIX_TYPE: this.img = Element.SIX_IMAGE; break;
			case Element.SEVEN_TYPE: this.img = Element.SEVEN_IMAGE; break;
			case Element.EIGHT_TYPE: this.img = Element.EIGHT_IMAGE; break;

		}

		this.setImageView();
		this.setMouseHandler();
	}

	protected void loadImage(String filename,int width, int height){
		try{
			this.img = new Image(filename,width,height,false,false);
		} catch(Exception e){}
	}


	String getType(){
		return this.type;
	}

	int getRow() {
		return this.row;
	}

	int getCol() {
		return this.col;
	}


	protected ImageView getImageView(){
		return this.imgView;
	}

	void setType(String type){
		this.type = type;
	}

	void initRowCol(int i, int j) {
		this.row = i;
		this.col = j;
	}

	private void setImageView() {
		// initialize the image view of this element
		this.imgView = new ImageView();
		this.imgView.setImage(this.img);
		this.imgView.setLayoutX(0);
		this.imgView.setLayoutY(0);
		this.imgView.setPreserveRatio(true);

		if(this.type.equals(Element.FLAG_TYPE)) {
			this.imgView.setFitWidth(GameStage.FLAG_WIDTH);
			this.imgView.setFitHeight(GameStage.FLAG_HEIGHT);
		}else {
			this.imgView.setFitWidth(GameStage.CELL_WIDTH);
			this.imgView.setFitHeight(GameStage.CELL_HEIGHT);
		}
	}

	private void setMouseHandler(){
		Element element = this;
		this.imgView.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
                switch(element.getType()) {
	                case Element.FLAG_TYPE: 		// if flag, set flagClicked to true
								                	System.out.println("FLAG clicked!");
								    	            gameStage.setFlagClicked(true);
								    	            break;
	    			case Element.LAND_TYPE:
			    									System.out.println("LAND clicked!");
								    				if(!gameStage.isFlagClicked()) {
								    					if(!gameStage.isBomb(element)){// if not a bomb, clear image
								    						int numOfNeigh = gameStage.checkNeighbors(element.getRow(), element.getCol()); //number of neighboring bomb
								    						//if their is a neighboring bomb, change image
								    						if(numOfNeigh > 0){
								    							Image type = gameStage.getNumType(numOfNeigh);
								    							changeImage(element,type);
								    						}
								    						else //else, clear image
								    						{
								    							clearImage(element);
								    						}

								    						gameStage.setNumOfSafe(); //increment the number of clicked-safe tile
								    					}
								    					else {
								    						changeImage(element,Element.BOMB_IMAGE); // if bomb, change image to bomb
								    						gameStage.setGameOver(0); //player wins
								    						System.out.println("YOU LOSE!!!");
								    					}
								    	            } else {
								    	            	changeImage(element,Element.FLAG_IMAGE);	// if flag was clicked before hand, change image to flag
								    	            	element.setType(LAND_FLAG_TYPE);			// change type to landToFlag
								    	            	gameStage.setFlagClicked(false);	    	// reset flagClicked to false

								    	            	if(gameStage.isBomb(element)){
								    	            		gameStage.setFlaggedBomb(1); //increment the counter for the flagged bomb tile by 1
								    	            	}
								    	            }
								    				break;
	    			case Element.LAND_FLAG_TYPE:
								    				if(gameStage.isBomb(element)){
							    	            		gameStage.setFlaggedBomb(-1); //decrement the counter for the flagged bomb tile by 1
							    	            	}
								    				changeImage(element,Element.LAND_IMAGE);		// if flag is clicked, change image back to land
							    	            	element.setType(LAND_TYPE);
							    	            	break;
                }
			}	//end of handle()
		});
	}

	private void clearImage(Element element) {
		imgView.setImage(null);
	}

	private void changeImage(Element element, Image image) {
		this.imgView.setImage(image);

	}
}
