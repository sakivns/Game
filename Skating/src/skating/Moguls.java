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
public class Moguls
{
	public int x;  
	public int y;
	ImageIcon mogulImage;
		
	public Moguls()
	{
	}
	
	public void newMogul(Random g1)  
	{
		do  
                {
			x = (int)(g1.nextDouble() * 700) + 2;
			y = (int)(g1.nextDouble() * 700) + 2;
		}
		while(x < 265 && x > 235 && y < 190 && y > 160);
                
	}
	
	public void moveMogul(int y1, Random g1) 
	{
		y = y - y1;
		if(y < 2) 
                {
			y = 720;
			x = (int)(g1.nextDouble() * 700) + 2;
		}
	}
}
