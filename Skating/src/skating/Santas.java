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


public class Santas
{
	public int x;  //x and y coordinates for santas.
	public int y;
	Image santaImage;
	
	public Santas()
	{
	}
	
	public void newSanta(Random g1)  //Function for generating new santas.
	{
		do  //Loop keeps santas from generating on top of the snowboarder.
		{
			x = (int)(g1.nextDouble() * 480) + 2;
			y = (int)(g1.nextDouble() * 480) + 2;
		}
		while(x < 265 && x > 235 && y < 190 && y > 160);
	}
	
	public void moveSanta(int y1, Random g1)  //Function for moving santas.
	{
		y = y - y1;
		if(y < 2)  //New santa at bottom of screen if one leaves the top of the screen.
		{
			y = 498;
			x = (int)(g1.nextDouble() * 480) + 2;
		}
		if(y > 498)  //New santa at top of screen if one leaves the bottom of the screen.
		{
			y = 2;
			x = (int)(g1.nextDouble() * 480) + 2;
		}
	}
}


