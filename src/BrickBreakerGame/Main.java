package BrickBreakerGame;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		GamePlay gameplay = new GamePlay(); //our JPanel
		frame.setBounds(10,10,700,600);
		frame.setTitle("Breakout Game");
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	    //add our class to frame
	    frame.add(gameplay);
	
	}

}
