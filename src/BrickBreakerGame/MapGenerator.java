package BrickBreakerGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

//this class is used to create bricks
public class MapGenerator {

	public int map[][]; //we store brick positions in this 2D array
	public int brickWidth;
	public int brickHeight;
	
	//how many row and columns should be generated
	public MapGenerator( int row ,int col ) {

	  map = new int[row][col];
	  
	  for(int i = 0 ;i< map.length ;i++) {
		  for(int j = 0 ;j< map[0].length ;j++) {
			  
			  map[i][j] = 1; //yani bu pozisyonlarda brick olacak!
		  }
	  }
      
	  brickWidth = 540/col;
	  brickHeight = 150/row;
	  
	}
	
	//draw brick
	public void draw(Graphics2D g) {
		
		  for(int i = 0 ;i< map.length ;i++) {
			  for(int j = 0 ;j< map[0].length ;j++) {
				  
				  if(map[i][j] > 0) { //draw brick
					  
					  g.setColor(Color.MAGENTA);
					  g.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth,brickHeight);
					  
					  //stroke/border for each bricks
					  g.setStroke(new BasicStroke(2));
					  g.setColor(Color.BLACK);
					  g.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth,brickHeight);
				  
				  
				  }

			  }
		  }
		
		
		
	}
	
	//used to set brick value after they colidded with ball
	//value = 1 means there is a brick,value = 0 means
	//there is no brick at given row and col.
	public void setBrickValue( int value,int row,int col ) {
		
          map[row][col] = value ; 		
	}
	
	
}
