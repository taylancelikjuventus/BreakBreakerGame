package BrickBreakerGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener,ActionListener {

	//properties
	private boolean play = true; //our game shouldn't play by itself
	private int score = 0;
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 10;
	
	
	private int playerX = 310; //start pos of slider
	
	private int ballposX = 120;//start pos of ball
	private int ballposY = 350;//start pos of ball
	
	//ball direction
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	//map generator , this draws the bricks
	private MapGenerator map ;
	
	//constructor
	public GamePlay() {
		
	   map = new MapGenerator(3, 7);	
       addKeyListener(this); //add key listener to this object
	   setFocusable(true); 
	   setFocusTraversalKeysEnabled(false);
	   timer = new Timer(delay,this); 
	   timer.start(); 
	   
	}
	
	//get Canvas
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
	 
     //bgcolor
	  g.setColor(Color.BLACK);
	  g.fillRect(1, 1, 692, 592);
	  
	  //draw Bricks
	  map.draw((Graphics2D)g);
	  
	  //borders,there is no bottom border
	  g.setColor(Color.DARK_GRAY);
	  g.fillRect(0, 0, 3, 592);
	  g.fillRect(0, 0, 692, 3);
	  g.fillRect(681, 0, 3, 592);
	  
	  //score
	  g.setColor(Color.ORANGE);
	  g.setFont(new Font("serif",Font.BOLD,25 ) );
	  g.drawString("Score :"+String.valueOf(score), 550, 30);
	  
	  
	  //paddle
	  g.setColor(Color.WHITE);
	  g.fillRect(playerX, 550, 100, 8);
	  
	  //ball
	  g.setColor(Color.BLUE);
	  g.fillOval(ballposX, ballposY, 20, 20);
	  
      //Game Over
	  if(ballposY > 570) {
		  
		  play = false;
		  ballXdir = 0;
		  ballYdir = 0;
		  g.setColor(Color.RED);

		  g.setFont(new Font("serif",Font.BOLD, 35 ) );
		  g.drawString("GAME OVER", 190, 300);
		  g.drawString("Score :"+String.valueOf(score), 190, 340);
		  g.setFont(new Font("serif",Font.BOLD, 20 ) );
		  g.drawString("Press Enter to restart", 190, 370);
		 
		  
	  }
	  
	  //Win situation
	  if(totalBricks <= 0) {
		  
		  play = false;
		  ballXdir = 0;
		  ballYdir = 0;
		  g.setColor(Color.RED);

		  g.setFont(new Font("serif",Font.BOLD, 35 ) );
		  g.drawString("YOU WON !", 190, 300);
		  g.drawString("Score :"+String.valueOf(score), 190, 340);
		  g.setFont(new Font("serif",Font.BOLD, 20 ) );
		  g.drawString("Press Enter to restart", 190, 370);
		  
		  
	  }
	  
	  
	  g.dispose();
	  
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	
		timer.start();
       
	//code for moving ball	
		if(play) {
			
			//collision detection between paddle and ball
			if(new Rectangle(ballposX, ballposY,20,20).  
					intersects(new Rectangle(playerX,550,100,8)) )
				//we detect it by considering the ball as rectangle!
			{
				
				ballYdir = -ballYdir;
				
			}
			
			//collision with bricks and ball
		A:	for(int i=0 ; i<map.map.length  ;i++) {
				for(int j =0 ;j<map.map[0].length ;j++) {
					
					if( map.map[i][j] >0 ) {
						
						//detect intersection
						int brickX = j* map.brickWidth + 80 ;
						int brickY = i* map.brickHeight + 50 ;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
					
					    //black rectangle to replace with collided
					    Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
					    Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
					    Rectangle brickRect = rect ;
					    
					    if(ballRect.intersects(brickRect)) {
					    	
					    	map.setBrickValue(0, i, j);
					    	totalBricks--;  //decrease number of bricks
					    	score += 5 ;
					    	
					    	//if ball hits brick's left or right side
					    	if(
					    			ballposX+19 <= brickRect.x ||
					    		    ballposX+1 >=brickRect.x + brickRect.width
					    	) {
					    		
					    		//change x direction
					    		ballXdir = -ballXdir;
					    		
					    	}else {//if ball hits brick's bottom ot top side
					    		ballYdir = -ballYdir;
					    	}
					    	
					    	break A;  //go to label A
					    }
					    
					    
					    
					}
					
				}
				
			}
			
			
			
			ballposX += ballXdir ;
			ballposY += ballYdir ;
			
			//for left border
			if(ballposX < 0 ) {
				ballXdir = -ballXdir; 
			}
			//for top border 
			if(ballposY < 0 ) {
				ballYdir = -ballYdir; 
			}
			//for right border
			if(ballposX >670 ) {
				ballXdir = -ballXdir; 
			}
			
		}
		
		
		this.repaint();
	
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
         if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			
			timer.setDelay(delay);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		//if right arrow is pressed
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			
			if(playerX >=600)//if paddle goes outside panel
				playerX = 600;
			else {
				moveRight();
			}
				
			
		}
		
		//if left arrow is pressed
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			
			if(playerX < 10)//if paddle goes outside panel
				playerX = 10;
			else {
				moveLeft();
			}
			
			
		}
		
		
		//IF game is over and user press enter restart game
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			
			if(!play) { // if game over
			  
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 320;
				score = 0;
				totalBricks = 21 ;
				map = new MapGenerator(3, 7);
				
				//repaint graphics
				repaint();
			}
			
		}
		//Move faster
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			
			timer.setDelay(1);
		}
		
	}

	public void moveRight() {
		
		play = true; //start playing
		playerX += 20;  //move paddle
		
	}

	public void moveLeft() {
		
		play = true; //start playing
		playerX -= 20;  //move paddle
		
	}

	
	
	
	
}
