/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skating;

import java.awt.Image;
import java.util.Random;
import java.awt.*;
import javax.swing.ImageIcon;
/**
 *
 * @author Vikas Yadav
 */
public class Trees
{
	public int x;  
	public int y;
	static ImageIcon treeImage;
	
	public Trees()
	{
            
	}
	
	public void newTree(Random g1)  
	{
		do  
		{
			x = (int)(g1.nextDouble() * 700) ;
			y = (int)(g1.nextDouble() * 700) ;
		}
		while(x < 245 && x > 235 && y < 190 && y > 160);
                
	}
	
	public void moveTree(int y1, Random g1)  
	{
		y = y - y1;
		if(y < 2) 
		{
			y = 720;
			x = (int)(g1.nextDouble() * 700) + 2;
		}
                
	}
       
}

