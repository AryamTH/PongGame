import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{ //so we can run it on thread


	static final int GAME_WIDTH = 1000; //if there is more than one panel well be just one instance of these and well be run faster 
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.555)); 
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);  
	static final  int BALL_DIAMETER = 20; //cicle قطر 
	static final int PADDLE_WIDTH = 25; 
	static final int PADDLE_HEIGHT = 100; 

	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;

	
	
	
	GamePanel(){
		newPaddles();
		newBall();
	
		score = new Score(GAME_WIDTH, GAME_HEIGHT);
		this.setFocusable(true); //read key press
		this.addKeyListener(new AL()); //extends the key adaptor class
		this.setPreferredSize(SCREEN_SIZE);
		
		gameThread = new Thread(this); //????????
		gameThread.start();

	
}

public void newBall() { //create new ball in the screen
	//random = new Random();
	ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2), (GAME_HEIGHT/2)-(BALL_DIAMETER/2), BALL_DIAMETER, BALL_DIAMETER );

}

public void newPaddles() { //!!??
	paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT,1);
	paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT,2);

}

public void paint(Graphics g) { //paint stuff in the screen
    
	image = createImage(getWidth(), getHeight()); //has d for w and h of our game panel
	graphics = image.getGraphics(); //
	draw(graphics); //pass in our graphics "draw all component
	g.drawImage(image, 0, 0, this); //0,0 start above in the corner
	
	
	
}

public void draw(Graphics g) {  //to draw
	paddle1.draw(g);
	paddle2.draw(g);
	ball.draw(g);
	score.draw(g);
}

public void move() { //to move things after each iteration of game loop and be faster 
	paddle1.move();
	paddle2.move();
	ball.move();
}

public void checkCollision() {
	//stop paddle
	if(paddle1.y<=0)
		paddle1.y=0;
	if(paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
		paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
	
	if(paddle2.y<=0)
		paddle2.y=0;
	if(paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
		paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;
	
	//hiva the player 1 point and creat new paddels and ball
	if(ball.x <= 0) {
		score.player2++;
		newPaddles();
		newBall();
	}
	
	if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
		score.player1++;
		newPaddles();
		newBall();
	}
	
	
	
	//Stop ball
	if(ball.y <= 0)
		ball.setYDirection(-ball.yVelocity);
	if(ball.y >= GAME_HEIGHT-BALL_DIAMETER)
		ball.setYDirection(-ball.yVelocity);

	//----
	if(ball.intersects(paddle1)) {
		ball.xVelocity = Math.abs(ball.xVelocity);
		ball.xVelocity++;
	    if(ball.yVelocity>0)
	    	ball.yVelocity++;
	    else
	    	ball.yVelocity--;
	    ball.setXDirection(ball.xVelocity);
	    ball.setYDirection(ball.yVelocity);
	}
	
	if(ball.intersects(paddle2)) {
		ball.xVelocity = Math.abs(ball.xVelocity);
		ball.xVelocity++;
	    if(ball.yVelocity>0)
	    	ball.yVelocity++;
	    else
	    	ball.yVelocity--;
	    ball.setXDirection(-ball.xVelocity);
	    ball.setYDirection(ball.yVelocity);
	}
	
}


public void run() {
	//game panel
	long lastTime = System.nanoTime();
	double amountOfTicks = 60.0;
	double ns = 1000000000/amountOfTicks;
	double delta = 0;
	
	while(true) {
		long now = System.nanoTime();
		delta += (now -lastTime)/ns; //most be between ()
		lastTime = now;
		if (delta>=1) {
			move();
			checkCollision();
			repaint();
			delta --;
		}
	}
	
}


public class AL extends KeyAdapter{ //inner class for Action Listener
	
	public void keyPressed(KeyEvent e ) {
		paddle1.keyPressed(e);
		paddle2.keyPressed(e);
	}
	
public void keyReleased(KeyEvent e ) {
		paddle1.keyReleased(e);
		paddle2.keyReleased(e);
	}
}

}
