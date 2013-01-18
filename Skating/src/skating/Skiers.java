/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skating;

import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author Vikas Yadav
 */

class Skiers
{
	public int x;  //x and y coordinates for skiers.
	public int y;
	public int speed;  //Speed and direction variables for skiers.
	public String skiersDirection;
	public int skiersCounter;
	public int skiersChange;
	ImageIcon skierImage;
	
	public Skiers()
	{
	}
	
	public void newSkier(Random g1)  //Function for generating new skiers.
	{
		do
		{
			x = (int)(g1.nextDouble() * 700) + 2;
			y = (int)(g1.nextDouble() * 700) + 2;
		}
		while(x < 265 && x > 235 && y < 190 && y > 160);
		if(((int)(g1.nextDouble() * 2) + 1) == 1)  //Randomly generates direction of skier.
			skiersDirection = "left";
		else
			skiersDirection = "right";
		skiersCounter = 0;
		skiersChange = (int)(g1.nextDouble() * 20) + 1;  //Sets how often the skier 
		speed = (int)(g1.nextDouble() *3)-2;  //changes direction and variable speed.
	}
	
	public void moveSkier(int y1, Random g1)  //Function for moving skiers.
	{
		skiersCounter++;
		if(skiersCounter > skiersChange)  //Chance to change direction after a random
		{                                 //amount of time.
			if(((int)(g1.nextDouble() * 2) + 1) == 1)
				skiersDirection = "left";
			else
				skiersDirection = "right";
			skiersCounter = 0;
			skiersChange = (int)(g1.nextDouble() * 20) + 1;
		}
		y = y - y1 - speed;
		if(skiersDirection == "left")
			x = x - 3 + speed;
		else
			x = x + 3 - speed;
		if(y < 2)  //Generates a new skier at the bottom if one leaves the top of the screen.
		{
			y = 700;
			x = (int)(g1.nextDouble() * 700) + 2;
			if(((int)(g1.nextDouble() * 2) + 1) == 1)
				skiersDirection = "left";
			else
				skiersDirection = "right";
			speed = (int)(g1.nextDouble() * 3) - 1;
		}
		if(y >700)  //Generates a new skier at the top if one leaves the bottom of the screen.
		{
			y = 2;
			x = (int)(g1.nextDouble() * 700) + 2;
			if(((int)(g1.nextDouble() * 2) + 1) == 1)
				skiersDirection = "left";
			else
				skiersDirection = "right";
			speed = (int)(g1.nextDouble() * 3) - 1;
		}
		if(x > 700)  //Pushes skiers back if they collide with the right side.
		{
			x = x - 6 + speed;
			skiersDirection = "left";
		}
		if(x < 2)  //Pushes skiers back if they collide with the left side.
		{
			x = x + 6 - speed;
			skiersDirection = "right";
		}
	}
}