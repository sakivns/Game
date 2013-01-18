import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Skating extends Applet implements  Runnable, KeyListener, FocusListener
{
	private static final long serialVersionUID = 1L;
	private int x = 250;  //Starting horizontal position of snowboarder
	private int armWave = 0;  //Tracks arm wave of snowboarder when going straight down.
	Random Generate1 = new Random();
	Thread t;
	private String direction = "start1";  //Shows startup screen on load.
	Image start;  //Image for startup screen.
	Image exit;  //Image for exit game screen.
	Image end;  //Image for end game screen.
	private String tempDirection = "";  //Stores direction when paused.
	Image mySkier;  //Image for snowboarder.
	Image paused[] = new Image[6];  //Images for animated paused display.
	private int pausedCounter = 0;  //Counter for animating paused display.
	Image offImage;  //Image and Graphics for drawing off screen and then copying it
	Graphics offGraphics;  //onto the main screen to prevent white flashing.
	private boolean crashed = false;  //Variables, images, and audio files for crashes.
	private int crashedCounter = 0, crashedCounter2 = 0;
	Image ow;
	AudioClip crashed1, crashed2, crashed3;
	private boolean jumped = false, jumpedSlow = false; //Variables, images, and audio
	private int jumpedCounter = 0;  //files for jumps.
	AudioClip jump;
	private boolean muted = false;
	private int score = 0;  //Variables for tracking score, high score, and distance.
	private int highScore = 0;
	private int distance = 0;
	private int numMoguls = 5;  //Number of moguls generated.
	private Moguls myMoguls[] = new Moguls[5];  //Array of mogul objects.
	private int numRocks = 10;  //Number of rocks generated.
	private Rocks myRocks[] = new Rocks[10];  //Array of rock objects.
	private int numTrees = 20;  //Number of trees generated.
	private Trees myTrees[] = new Trees[20];  //Array of tree objects.
	private int numSantas = 3;  //Number of santas generated.
	private Santas mySantas[] = new Santas[3];  //Array of santa objects.
	private int numSkiers = 4;  //Number of skiers generated.
	private Skiers mySkiers[] = new Skiers[4];  //Array of skier objects.
	
	public void init()
	{		
		addKeyListener(this);  //Implements the key listeners and focus listeners.
		addFocusListener(this);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		
		for(int i = 0; i < numMoguls; i++)  //Generates initial mogul locations.
		{
			myMoguls[i] = new Moguls();
			myMoguls[i].newMogul(Generate1);
		}
		for(int i = 0; i < numRocks; i++)  //Generates initial rock locations.
		{
			myRocks[i] = new Rocks();
			myRocks[i].newRock(Generate1);
		}
		for(int i = 0; i < numTrees; i++)  //Generates initial tree locations.
		{
			myTrees[i] = new Trees();
			myTrees[i].newTree(Generate1);
		}
		for(int i = 0; i < numSantas; i++)  //Generates initial Santa locations.
		{
			mySantas[i] = new Santas();
			mySantas[i].newSanta(Generate1);
		}
		for(int i = 0; i < numSkiers; i++)  //Generates initial skier locations.
		{
			mySkiers[i] = new Skiers();
			mySkiers[i].newSkier(Generate1);
		}
		
		for (int i = 0; i < 6 ; i++)  //Sticks the proper images into the 
		{                             //array of paused images.
		    paused[i] = getImage(getCodeBase(), "paused" + (i + 1) + ".gif");
		}
		
		ow = getImage(getCodeBase(), "snowboardouch.gif");  //Sets up the crashed image.
		crashed1 = getAudioClip(getCodeBase(), "splat.wav");  //Sets up the various
		crashed2 = getAudioClip(getCodeBase(), "hithard.wav");  //audio files.
		crashed3 = getAudioClip(getCodeBase(), "dullthump.wav");
		jump = getAudioClip(getCodeBase(), "boing2.wav");
		
		repaint();
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();  //Stores keys that are pressed.
		
		if(direction.equals("start1"))  //Allows keys from the start screen.
		{
			if(key == KeyEvent.VK_NUMPAD8)
				direction = "stop";
			if(key == KeyEvent.VK_NUMPAD6)
				direction = "start2";
		}
		else if(direction.equals("start2")) //Allows keys from the second start screen.
		{
			if(key == KeyEvent.VK_NUMPAD8)
				direction = "stop";
			if(key == KeyEvent.VK_NUMPAD4)
				direction = "start1";
			if(key == KeyEvent.VK_NUMPAD6)
				direction = "start3";
		}
		else if(direction.equals("start3"))  //Allows keys from the third start screen.
		{
			if(key == KeyEvent.VK_NUMPAD8)
				direction = "stop";
			if(key == KeyEvent.VK_NUMPAD4)
				direction = "start2";
		}
		else if(direction.equals("exit"))  //Allows only 8 to be pressed after exiting a game.
		{
			if(key == KeyEvent.VK_NUMPAD8)
				newGame();
		}
		else if(direction.equals("end"))  //Allows only 8 to be pressed after the game ends.
		{
			if(key == KeyEvent.VK_NUMPAD8)
				newGame();
		}
		else if(direction.equals("paused"))  //Allows only 5 to be pressed when paused.
		{
			if(key == KeyEvent.VK_NUMPAD0)
				direction = "exit";
			if(key == KeyEvent.VK_NUMPAD5)  //Sets direction to the value it held
			{                               //prior to being paused.
				direction = tempDirection;
				if(direction.equals("upleft") || direction.equals("upright"))
					direction = "stop";  //Sets direction to stop if 7 or 9 
			}                            //were held down when paused.
		}
		else if(jumped == true)  //Allows only some keys when jumped is set to true.
		{
			if(key == KeyEvent.VK_NUMPAD0)
				direction = "exit";
			if(key == KeyEvent.VK_NUMPAD1)
				direction = "downleft";
			if(key == KeyEvent.VK_NUMPAD3)
				direction = "downright";
			if(key == KeyEvent.VK_NUMPAD2)
				direction = "down";
			if(key == KeyEvent.VK_NUMPAD4)
				direction = "downleft";
			if(key == KeyEvent.VK_NUMPAD6)
				direction = "downright";
			if(key == KeyEvent.VK_NUMPAD5)  //Stores direction so it can be restored
			{                               //when the game is unpaused.
				tempDirection = direction;
				direction = "paused";
			}
		}
		else if(jumpedSlow == true)  //Allows only some keys when jumpedSlow is true.
		{
			if(key == KeyEvent.VK_NUMPAD0)
				direction = "exit";
			if(key == KeyEvent.VK_NUMPAD4)
				direction = "left";
			if(key == KeyEvent.VK_NUMPAD6)
				direction = "right";
			if(key == KeyEvent.VK_NUMPAD1)
				direction = "left";
			if(key == KeyEvent.VK_NUMPAD3)
				direction = "right";
			if(key == KeyEvent.VK_NUMPAD2)
				direction = "slowdown";
			if(key == KeyEvent.VK_NUMPAD5)  //Stores direction so it can be restored
			{                               //when the game is unpaused.
				tempDirection = direction;
				direction = "paused";
			}
		}
		else if(crashed == true)  //Disables all commands when crashed is true
		{                         //(approx. .5 seconds).
		}
		else if(direction.equals("crashed"))  //Allows only some keys to be pressed
		{  //after crashed is set to false but while direction still equals crashed.
			if(key == KeyEvent.VK_NUMPAD0)
				direction = "exit";
			if(key == KeyEvent.VK_NUMPAD4)
				direction = "left";
			if(key == KeyEvent.VK_NUMPAD6)
				direction = "right";
			if(key == KeyEvent.VK_NUMPAD1)
				direction = "downleft";
			if(key == KeyEvent.VK_NUMPAD3)
				direction = "downright";
			if(key == KeyEvent.VK_NUMPAD2)
				direction = "down";
			if(key == KeyEvent.VK_NUMPAD5)  //Stores direction so it can be restored
			{                               //when the game is unpaused.
				tempDirection = direction;
				direction = "paused";
			}
		}
		else  //Keys allowed when not crashed, jumped, paused, or on the start screens.
		{
			if(key == KeyEvent.VK_NUMPAD0)
				direction = "exit";
			if(key == KeyEvent.VK_NUMPAD4)
				direction = "left";
			if(key == KeyEvent.VK_NUMPAD6)
				direction = "right";
			if(key == KeyEvent.VK_NUMPAD1)
				direction = "downleft";
			if(key == KeyEvent.VK_NUMPAD3)
				direction = "downright";
			if(key == KeyEvent.VK_NUMPAD7)
				direction = "upleft";
			if(key == KeyEvent.VK_NUMPAD9)
				direction = "upright";
			if(key == KeyEvent.VK_NUMPAD2)
				direction = "down";
			if(key == KeyEvent.VK_NUMPAD8)
				direction = "stop";
			if(key == KeyEvent.VK_NUMPAD5)  //Stores direction so it can be restored
			{                               //when the game is unpaused.
				tempDirection = direction;
				direction = "paused";
			}
		}
		
		if(key == KeyEvent.VK_DECIMAL)  //Mutes sound.
			muted = !muted;
		
		e.consume();
	}
	
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if(!(direction.equals("start1") || direction.equals("start2") || direction.equals("start3") 
			|| direction.equals("paused") || jumped == true || jumpedSlow == true || direction.equals("crashed")
			|| direction.equals("exit") || direction.equals("end")))
		{  //Stops movement after 7 and 9 are released.
			if(key == KeyEvent.VK_NUMPAD7)
				direction = "stop";
			if(key == KeyEvent.VK_NUMPAD9)
				direction = "stop";
		}
		
		e.consume();
	}
	
	public void keyTyped(KeyEvent e)
	{
		e.consume();
	}
	
	public void start()
	{
		t = new Thread(this);
		t.start();
	}
	
	public void run()
	{
		long tm = System.currentTimeMillis();  //Stores system time when program starts.

		while(Thread.currentThread() == t)
		{
			moveSkier();  //Function that moves all objects.
			repaint();
			try
			{
				tm += 33;  //33 milliseconds.
				Thread.sleep(Math.max(0, tm - System.currentTimeMillis()));
			}  //Delay for drawing the animation every 33 milliseconds, calculated from
			catch(InterruptedException e)  //the start times stored earlier.
			{
				break;
			}
		}
	}
	
	public void stop()
	{
		t = null;
		offImage = null;
		offGraphics = null;

    }
		
	public void moveSkier()
	{
		if(direction.equals("paused"))  //Increments pausedCounter for animating
		{                               //the paused display.
			pausedCounter++;
			if(pausedCounter == 54)
				pausedCounter = 0;
		}
		else if(direction.equals("end") || direction.equals("exit"))
		{
			if(score > highScore)  //Moves score into highScore if it is larger.
				highScore = score;
		}
		else
		{
			if(direction.equals("downleft"))  //Snowboarder moves quickly down and left.
			{
				x = x - 4;  //Moves the snowboarder to the left.
				distance = distance + 2;  //Increments distance
				for(int i = 0; i < numMoguls; i++ )  //Functions for moving the objects
					myMoguls[i].moveMogul(7, Generate1);
				for(int i = 0; i < numRocks; i++ )
					myRocks[i].moveRock(7, Generate1);
				for(int i = 0; i < numTrees; i++ )
					myTrees[i].moveTree(7, Generate1);
				for(int i = 0; i < numSantas; i++ )
					mySantas[i].moveSanta(6, Generate1);
				for(int i = 0; i < numSkiers; i++ )
					mySkiers[i].moveSkier(4, Generate1);
				if(x < 2)  //Moves the snowboarder back if he crashes into the side
				{          //of the screen.
					direction = "downright";
					x = x + 8;
				}
			}
			if(direction.equals("downright"))  //Snowboarder moves quickly down and right.
			{
				x = x + 4;  //Moves the snowboarder to the right.
				distance = distance + 2;  //Increments distance.
				for(int i = 0; i < numMoguls; i++ )  //Functions for moving the objects.
					myMoguls[i].moveMogul(7, Generate1);
				for(int i = 0; i < numRocks; i++ )
					myRocks[i].moveRock(7, Generate1);
				for(int i = 0; i < numTrees; i++ )
					myTrees[i].moveTree(7, Generate1);
				for(int i = 0; i < numSantas; i++ )
					mySantas[i].moveSanta(6, Generate1);
				for(int i = 0; i < numSkiers; i++ )
					mySkiers[i].moveSkier(4, Generate1);
				if(x > 481)  //Moves the snowboarder back if he crashes into the side
				{            //of the screen.
					direction = "downleft";
					x = x - 8;
				}
			}
			if(direction.equals("down"))  //Snowboarder moves quickly down.
			{
				distance = distance + 2;  //Increments distance.
				armWave++;  //Increments armWave.
				for(int i = 0; i < numMoguls; i++ )  //Functions for moving the objects.
					myMoguls[i].moveMogul(7, Generate1);
				for(int i = 0; i < numRocks; i++ )
					myRocks[i].moveRock(7, Generate1);
				for(int i = 0; i < numTrees; i++ )
					myTrees[i].moveTree(7, Generate1);
				for(int i = 0; i < numSantas; i++ )
					mySantas[i].moveSanta(6, Generate1);
				for(int i = 0; i < numSkiers; i++ )
					mySkiers[i].moveSkier(4, Generate1);
			}
			if(direction.equals("left"))  //Snowboarder moves slowly down and left.
			{
				x = x - 4;  //Moves the snowboarder to the left.
				distance = distance + 1;  //Increments distance.
				for(int i = 0; i < numMoguls; i++ )  //Functions for moving the objects.
					myMoguls[i].moveMogul(4, Generate1);
				for(int i = 0; i < numRocks; i++ )
					myRocks[i].moveRock(4, Generate1);
				for(int i = 0; i < numTrees; i++ )
					myTrees[i].moveTree(4, Generate1);
				for(int i = 0; i < numSantas; i++ )
					mySantas[i].moveSanta(3, Generate1);
				for(int i = 0; i < numSkiers; i++ )
					mySkiers[i].moveSkier(1, Generate1);
				if(x < 2)  //Moves the snowboarder back if he crashed into the side
				{          //of the screen
					direction = "right";
					x = x + 8;
				}
			}
			if(direction.equals("right"))  //Snowboarder moves slowly down and right.
			{
				x = x + 4;  //Moves the snowboarder to the right.
				distance = distance + 1;  //Increments distance.
				for(int i = 0; i < numMoguls; i++ )  //Functions for moving the objects.
					myMoguls[i].moveMogul(4, Generate1);
				for(int i = 0; i < numRocks; i++ )
					myRocks[i].moveRock(4, Generate1);
				for(int i = 0; i < numTrees; i++ )
					myTrees[i].moveTree(4, Generate1);
				for(int i = 0; i < numSantas; i++ )
					mySantas[i].moveSanta(3, Generate1);
				for(int i = 0; i < numSkiers; i++ )
					mySkiers[i].moveSkier(1, Generate1);
				if(x > 481)  //Moves the snowboarder back if he crashes into the side
				{            //of the screen.
					direction = "left";
					x = x - 8;
				}
			}
			if(direction.equals("upleft"))  //Snowboarder moves one step left.
			{
				x = x - 2;  //Moves the snowboarder to the left.
				for(int i = 0; i < numSantas; i++ )  //Functions for moving the objects
					mySantas[i].moveSanta(-3, Generate1);  //that continue to move when
				for(int i = 0; i < numSkiers; i++ )  //the snowboarder does not.
					mySkiers[i].moveSkier(-5, Generate1);
				if(x < 2)  //Moves the snowboarder back if he crashes into the side
					x = x + 2;  //of the screen.
			}
			if(direction.equals("upright"))  //Snowboarder moves one step right.
			{
				x = x + 2;  //Moves the snowboarder to the right.
				for(int i = 0; i < numSantas; i++ )  //Functions for moving the objects
					mySantas[i].moveSanta(-1, Generate1);  //that continue to move when
				for(int i = 0; i < numSkiers; i++ )  //the snowboarder does not.
					mySkiers[i].moveSkier(-3, Generate1);
				if(x > 481)  //Moves the snowboarder back if he crashes into the side
					x = x - 2;  //of the screen.
			}
			if(direction.equals("stop"))  //Snowboarder does not move.
			{
				for(int i = 0; i < numSantas; i++ )  //Functions for moving the objects
					mySantas[i].moveSanta(-1, Generate1);  //that continue to move when
				for(int i = 0; i < numSkiers; i++ )  //the snowboarder does not.
					mySkiers[i].moveSkier(-3, Generate1);
			}
			if(direction.equals("slowdown"))  //Used for slower jumps only.
			{
				distance = distance + 1;  //Increments distance.
				for(int i = 0; i < numMoguls; i++ )  //Functions for moving the objects.
					myMoguls[i].moveMogul(4, Generate1);
				for(int i = 0; i < numRocks; i++ )
					myRocks[i].moveRock(4, Generate1);
				for(int i = 0; i < numTrees; i++ )
					myTrees[i].moveTree(4, Generate1);
				for(int i = 0; i < numSantas; i++ )
					mySantas[i].moveSanta(3, Generate1);
				for(int i = 0; i < numSkiers; i++ )
					mySkiers[i].moveSkier(1, Generate1);
				if(jumpedSlow == false)  //Sets direction to down if the snowboarder
				{  //is moving straight down at the end of a slow jump.
					direction = "down";
				}
			}
			if(direction.equals("crashed"))  //Moves objects that continue to move when
			{                                //the snowboarder is crashed.
				for(int i = 0; i < numSantas; i++ )
					mySantas[i].moveSanta(-1, Generate1);
				for(int i = 0; i < numSkiers; i++ )
					mySkiers[i].moveSkier(-3, Generate1);
			}
			if(!direction.equals("exit") && !direction.equals("end"))
			{
				if(jumped == false && jumpedSlow == false)
					detectJump();  //Detects jumps when the snowboarder is not already jumped.
				if(jumped == true)
				{
					jumpedCounter++;  //Increments the jumpedCounter.
					jumpedScore();  //Calculates score if the snowboarder passes over
					if(jumpedCounter == 75)  //anything while jumped.
					{
						jumped = false;  //Ends the jump after a set amount of time.
						jumpedCounter = 0;
					}
				}
				else if(jumpedSlow == true)
				{
					jumpedCounter++;  //Increments the jumpedCounter.
					jumpedSlowScore();  //Calculates score if the snowboarder passes over
					if(jumpedCounter == 75)  //anything while jumped.
					{
						jumpedSlow = false;  //Ends the jump after a set amount of time.
						jumpedCounter = 0;
					}
				}
				else if(crashed == true)
				{
					crashedCounter++;  //Increments crashedCounter.
					if(crashedCounter == 15)
					{
						crashed = false;  //Unlocks controls after a set amount of time.
						crashedCounter = 0;
					}
				}
				else
					detectCrash();
				if(distance > 10000 && !direction.equals("crashed"))
					direction = "end";  //Ends game if distance reaches 10,000.
			}
		}
	}
	
	public void detectJump()  //Detects the snowboarder passing over moguls.
	{
		for(int i = 0; i < numMoguls; i++ )
		{
			if(myMoguls[i].x < x + 10 && myMoguls[i].x > x - 10 && myMoguls[i].y < 185 && myMoguls[i].y > 165)
			{
				if(direction.equals("down") || direction.equals("downleft") || direction.equals("downright"))
				{
					jumped = true;  //Jumped state for faster downward movement.
					if(muted == false)
						jump.play();  //Plays jump sound.
					score = score + 50;  //Adds to score.
				}
				else if(direction.equals("left") || direction.equals("right"))
				{
					jumpedSlow = true;  //Jumped state for slower downward movement.
					if(muted == false)
						jump.play();  //Plays jump sound.
					score = score + 25;  //Adds to score.
				}
			}
		}
	}
	
	public void jumpedScore()  //Detects the snowboarder passing over objects during
	{                          //a faster jump.
		for(int i = 0; i < numRocks; i++ )
		{
			if(myRocks[i].x < x + 10 && myRocks[i].x > x - 10 && myRocks[i].y < 185 && myRocks[i].y > 165)
			{
				score = score + 100;  //Increments score for jumping rocks.
			}
		}
		for(int i = 0; i < numTrees; i++ )
		{
			if(myTrees[i].x < x + 10 && myTrees[i].x > x - 10 && myTrees[i].y < 185 && myTrees[i].y > 165)
			{
				score = score + 50;  //Increments score for jumping trees.
			}
		}
		for(int i = 0; i < numSantas; i++ )
		{
			if(mySantas[i].x < x + 10 && mySantas[i].x > x - 10 && mySantas[i].y < 185 && mySantas[i].y > 165)
			{
				score = score + 50;  //Increments score for jumping santas.
			}
		}
		for(int i = 0; i < numSkiers; i++ )
		{
			if(mySkiers[i].x < x + 10 && mySkiers[i].x > x - 10 && mySkiers[i].y < 185 && mySkiers[i].y > 165)
			{
				score = score + 150;  //Increments score for jumping skiers.
			}
		}
	}
	
	public void jumpedSlowScore()  //Detects the snowboarder passing over objects during
	{                              //a slower jump.
		for(int i = 0; i < numRocks; i++ )
		{
			if(myRocks[i].x < x + 10 && myRocks[i].x > x - 10 && myRocks[i].y < 185 && myRocks[i].y > 165)
			{
				score = score + 50;  //Increments score for jumping rocks.
			}
		}
		for(int i = 0; i < numTrees; i++ )
		{
			if(myTrees[i].x < x + 10 && myTrees[i].x > x - 10 && myTrees[i].y < 185 && myTrees[i].y > 165)
			{
				score = score + 25;  //Increments score for jumping trees.
			}
		}
		for(int i = 0; i < numSantas; i++ )
		{
			if(mySantas[i].x < x + 10 && mySantas[i].x > x - 10 && mySantas[i].y < 185 && mySantas[i].y > 165)
			{
				score = score + 25;  //Increments score for jumping santas
			}
		}
		for(int i = 0; i < numSkiers; i++ )
		{
			if(mySkiers[i].x < x + 10 && mySkiers[i].x > x - 10 && mySkiers[i].y < 185 && mySkiers[i].y > 165)
			{
				score = score + 75;  //Increments score for jumping skiers.
			}
		}
	}
	
	public void detectCrash()  //Function that detects collisions.
	{
		if(crashedCounter2 == 0)
		{
			for(int i = 0; i < numRocks; i++ )
			{
				if(myRocks[i].x < x + 10 && myRocks[i].x > x - 10 && myRocks[i].y < 185 && myRocks[i].y > 165)
				{
					direction = "crashed";  //Sets crashed states.
					crashed = true;
					crashedCounter++;  //Increments the crashed counters.
					crashedCounter2++;
					if(muted == false)
						crashed2.play();  //Plays rock crash sound.
					score = score - 400;  //Decreases score for hitting rocks.
				}
			}
			for(int i = 0; i < numTrees; i++ )
			{
				if(myTrees[i].x < x + 10 && myTrees[i].x > x - 10 && myTrees[i].y < 185 && myTrees[i].y > 165)
				{
					direction = "crashed";  //Sets the crashed states.
					crashed = true;
					crashedCounter++;  //Increments the crashed counters.
					crashedCounter2++;
					if(muted == false)
						crashed3.play();  //Plays the tree crash sound.
					score = score - 300;  //Decreases score for hitting trees.
				}
			}
			for(int i = 0; i < numSantas; i++ )
			{
				if(mySantas[i].x < x + 10 && mySantas[i].x > x - 10 && mySantas[i].y < 185 && mySantas[i].y > 165)
				{
					direction = "crashed";  //Sets the crashed states.
					crashed = true;
					crashedCounter++;  //Increments the crashed counters.
					crashedCounter2++;
					if(muted == false)
						crashed1.play();  //Plays the squishy crash sound.
					score = score - 250;  //Decreases score for hitting santa.
				}
			}
			for(int i = 0; i < numSkiers; i++ )
			{
				if(mySkiers[i].x < x + 10 && mySkiers[i].x > x - 10 && mySkiers[i].y < 185 && mySkiers[i].y > 165)
				{
					direction = "crashed";  //Sets the crashed states.
					crashed = true;
					crashedCounter++;  //Increments the crashed counters.
					crashedCounter2++;
					if(muted == false)
						crashed1.play();  //Plays the squishy crash sound.
					score = score - 500;  //Decreases score for hitting skiers.
				}
			}
		}
		else if(!direction.equals("crashed"))  //Disables crash detection for the first
		{  //few movements after a crash to keep from hitting the same object more than
			crashedCounter2++;  //once.
			if(crashedCounter2 == 3)
				crashedCounter2 = 0;
		}
	}
	
	public void newGame()  //Function for starting a new game. 
	{
		x = 250;  //Resets the snowboarder.
		crashed = false;  //Clears crashed and jumped states.
		crashedCounter = 0;
		crashedCounter2 = 0;
		jumped = false;
		jumpedSlow = false;
		jumpedCounter = 0;
				
		for(int i = 0; i < numMoguls; i++)  //Generates new mogul locations.
			myMoguls[i].newMogul(Generate1);
		for(int i = 0; i < numRocks; i++)  //Generates new rock locations.
			myRocks[i].newRock(Generate1);
		for(int i = 0; i < numTrees; i++)  //Generates new tree locations.
			myTrees[i].newTree(Generate1);
		for(int i = 0; i < numSantas; i++)  //Generates new Santa locations.
			mySantas[i].newSanta(Generate1);
		for(int i = 0; i < numSkiers; i++)  //Generates new skier locations.
			mySkiers[i].newSkier(Generate1);
		
		distance = 0;  //Resets distance and score.
		score = 0;
		
		direction = "stop";  //Sets snowboarder to the stopped direction.
	}
	
	public void paint(Graphics g)
	{
		if (offImage != null)  //Makes the offImage if it doesn't exist.
		{
			g.drawImage(offImage, 0, 0, null);
		}
	}
	
	public void update(Graphics g)
	{
		if(offGraphics == null)
		{
			offImage = createImage(500, 500);
			offGraphics = offImage.getGraphics();
		}
		
		offGraphics.setColor(getBackground());
		offGraphics.fillRect(0, 0, 500, 500);  //Fills offImage with a white background.
		offGraphics.setColor(Color.black);
		
		paintFrame(offGraphics);  //Function that draws everything to offGraphics.
		g.drawImage(offImage, 0, 0, null);  //Draws offImage to the main screen.
	}
	
	public void paintFrame(Graphics g)
	{		
		resize(500, 500);
		g.setColor(Color.black);
		requestFocusInWindow();
		
		if(direction.equals("start1"))
		{
			start = getImage(getCodeBase(), "start1.gif");
			g.drawImage(start, 0, 0, this);  //Draws start image.
		}
		else if(direction.equals("start2"))
		{
			start = getImage(getCodeBase(), "start2.gif");
			g.drawImage(start, 0, 0, this);  //Draws start image.
		}
		else if(direction.equals("start3"))
		{
			start = getImage(getCodeBase(), "start3.gif");
			g.drawImage(start, 0, 0, this);  //Draws start image.
		}
		else if(direction.equals("exit"))
		{
			exit = getImage(getCodeBase(), "exit.gif");
			g.drawImage(exit, 0, 0, this);  //Draws exit image.
			g.drawString("Your Score", 185, 315);  //Draws Score and High Score
			g.drawString("=  " + score, 253, 315);
			g.drawString("High Score", 185, 335);
			g.drawString("= " + highScore, 253, 335);
		}
		else if(direction.equals("end"))
		{
			end = getImage(getCodeBase(), "end.gif");
			g.drawImage(end, 0, 0, this);  //Draws end of game image.
			g.drawString("Your Score", 185, 315);  //Draws Score and High Score
			g.drawString("=  " + score, 253, 315);
			g.drawString("High Score", 185, 335);
			g.drawString("= " + highScore, 253, 335);
		}
		else
		{
			if(jumped == true || jumpedSlow == true)  //Sets jumped image.
				mySkier = getImage(getCodeBase(), "snowboardjump.gif");
			else
			{
				if(direction.equals("crashed"))  //Sets other snowboarder images.
					mySkier = getImage(getCodeBase(), "snowboardcrash1.gif");
				if(direction.equals("stop") || direction.equals("upleft") || direction.equals("upright"))
					mySkier = getImage(getCodeBase(), "snowboardside.gif");
				if(direction.equals("left"))
					mySkier = getImage(getCodeBase(), "snowboard45.gif");
				if(direction.equals("right"))
					mySkier = getImage(getCodeBase(), "snowboard135.gif");
				if(direction.equals("downleft"))
					mySkier = getImage(getCodeBase(), "snowboard60.gif");
				if(direction.equals("downright"))
					mySkier = getImage(getCodeBase(), "snowboard120.gif");
				if(direction.equals("down"))
				{
					if(armWave < 8)  //Sets down images based on armWave.
						mySkier = getImage(getCodeBase(), "snowboard0a.gif");
					else
					{
						mySkier = getImage(getCodeBase(), "snowboard0b.gif");
						if(armWave >= 16)
							armWave = 0;
					}
				}
			}
			for(int i = 0;  i < numMoguls; i++)  //Draws moguls
			{
				myMoguls[i].mogulImage = getImage(getCodeBase(), "snowboardmogul.gif");
				g.drawImage(myMoguls[i].mogulImage, myMoguls[i].x, myMoguls[i].y, this);
			}
			for(int i = 0;  i < numRocks; i++)  //Draws rocks
			{
				myRocks[i].rockImage = getImage(getCodeBase(), "snowboardrock.gif");
				g.drawImage(myRocks[i].rockImage, myRocks[i].x, myRocks[i].y, this);
			}
			for(int i = 0;  i < numTrees; i++)  //Draws trees.
			{
				myTrees[i].treeImage = getImage(getCodeBase(), "snowboardtree.gif");
				g.drawImage(myTrees[i].treeImage, myTrees[i].x, myTrees[i].y, this);
			}
			for(int i = 0;  i < numSantas; i++)  //Draws santas
			{
				mySantas[i].santaImage = getImage(getCodeBase(), "snowboardsanta.gif");
				g.drawImage(mySantas[i].santaImage, mySantas[i].x, mySantas[i].y, this);
			}
			for(int i = 0; i < numSkiers; i++)  //Draws skiers
			{
				if(mySkiers[i].skiersDirection == "left")
					mySkiers[i].skierImage = getImage(getCodeBase(), "skier2.gif");
				else  //Image is set based on skiers current direction.
					mySkiers[i].skierImage = getImage(getCodeBase(), "skier1.gif");
				g.drawImage(mySkiers[i].skierImage, mySkiers[i].x, mySkiers[i].y, this);
			}
				
			g.drawImage(mySkier, x, 175, this);  //Draws snowboarder.
			if(crashedCounter2 == 1)
			{
				g.drawImage(ow, x + 9, 165, this);  //Draws ouch image if crashed.
			}
			
			if(direction.equals("paused"))  //Draws paused image, cycling through
			{                               //images based on pausedCounter.
				g.drawImage(paused[(pausedCounter / 9)], 175, 212, this);
				g.drawRect(175, 212, 150, 75);
			}
			
			
			g.setColor(getBackground());
			g.fillRect(350, 0, 150, 73);  //Draws score box.
			g.setColor(Color.black);
			g.drawRect(350, 1, 149, 72);
			g.drawString("Distance", 363, 20);
			g.drawString("=  " + distance + "  ft.", 431, 20);
			g.drawString("Score", 363, 40);
			g.drawString("=  " + score, 431, 40);
			g.drawString("High Score", 363, 60);
			if(score > highScore)  //Makes current score show up as high score if it 
			{                      //is higher
				g.drawString("=", 431, 60);
				g.setColor(Color.RED);
				g.drawString("" + score, 451, 60);
				g.setColor(Color.black);
			}
			else
				g.drawString("=  " + highScore, 431, 60);
		}
		
		g.drawRect(1, 1, 498, 498);  //Draws a box around the game.
		g.setColor(getBackground());  //Sets color to white.
		g.fillRect(0, 0, 85, 30);  //Fills in a box for sound on/off display.
		g.setColor(Color.black);
		g.drawRect(1, 1, 84, 29);  //Draws a box for sound on/off display.
		if(muted == true)  //Displays whether sound is on or off.
			g.drawString("Sound: Off", 15, 20);
		else
			g.drawString("Sound: On", 15, 20);
	}
	
	public void focusGained(FocusEvent e)
	{
		e.getComponent();
	}
	
	public void focusLost(FocusEvent e)  //Pauses the game if focus is moved away.
	{
		if(!(direction.equals("start1") || direction.equals("start2") || direction.equals("start3") 
										|| direction.equals("exit") || direction.equals("end")))
			direction = "paused";
	}
	
}

