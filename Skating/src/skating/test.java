
package skating;
import java.security.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Random;
import javax.swing.ImageIcon;
/*
 * @author Vikas Yadav
 */

public class test extends Applet implements Runnable,KeyListener
{
   Thread th;
   Graphics gps;
   Image img;
   String prev_score;
   int sc;
   File file=new File("C:\\Users\\Vikas Yadav\\Desktop\\vika.txt");
   BufferedReader bf;
   BufferedWriter bw;
   
   private static String direction="start1";
   private static int score;
   int crash_counter=0;
   float time=0;
   Font f=new Font("Arial",Font.BOLD,30);
   private int numtrees=30;
   private int numrocks=30;
   private int nummoguls=30;
   private int numskiers=20;
   
   Random r=new Random();
   int x=245,y=175;
   int sz=720;
 
   Trees[] t=new Trees[numtrees];
   Rocks[] ro=new Rocks[numrocks];
   Moguls[] m=new Moguls[nummoguls];
   Skiers[] s=new Skiers[numskiers];
         
   ImageIcon i=new ImageIcon(getClass().getResource("trees.jpg"));
   ImageIcon i0=new ImageIcon(getClass().getResource("i0.gif"));
   ImageIcon i1=new ImageIcon(getClass().getResource("rock.gif"));
   ImageIcon i2=new ImageIcon(getClass().getResource("mogul.gif"));
   ImageIcon i3=new ImageIcon(getClass().getResource("skier2.gif"));
   ImageIcon i4=new ImageIcon(getClass().getResource("skier1.gif"));
   ImageIcon i5=new ImageIcon(getClass().getResource("paused1.gif"));
   ImageIcon i6=new ImageIcon(getClass().getResource("start1.gif"));
   ImageIcon i7=new ImageIcon(getClass().getResource("start2.gif"));
   ImageIcon i8=new ImageIcon(getClass().getResource("crash1.gif"));
   ImageIcon i9=new ImageIcon(getClass().getResource("ouch.gif"));
   ImageIcon i10=new ImageIcon(getClass().getResource("start3.gif"));
   ImageIcon i11=new ImageIcon(getClass().getResource("end.gif"));
   ImageIcon i12=new ImageIcon(getClass().getResource("i45.gif"));
   ImageIcon i13=new ImageIcon(getClass().getResource("i135.gif"));
   ImageIcon i14=new ImageIcon(getClass().getResource("jump.gif"));
   ImageIcon i15=new ImageIcon(getClass().getResource("pnda.gif"));
   ImageIcon i16=new ImageIcon(getClass().getResource("hs.jpg"));
                        
            
            
      Image ii=i.getImage();
   
      Image ii0=i0.getImage();
      Image ii1=i1.getImage();
      Image ii2=i2.getImage();
      Image ii3=i3.getImage();
      Image ii5=i5.getImage();
      Image ii6=i6.getImage();
      Image ii7=i7.getImage();
      Image ii4=i4.getImage();
      Image ii8=i8.getImage(); 
      Image ii9=i9.getImage(); 
      Image ii10=i10.getImage();
      Image ii11=i11.getImage();
      Image ii12=i12.getImage();
      Image ii13=i13.getImage();
      Image ii14=i14.getImage();
      Image ii15=i15.getImage();
      Image ii16=i16.getImage();
              
        
  public void init()
           
    {
        setFocusable(true);
        setSize(720,720);
        setBackground(Color.white);
        addKeyListener(this); 
        
        for(int i=0;i<numtrees;i++)
        {
            t[i]=new Trees();
            t[i].newTree(r);
            m[i]=new Moguls();
            m[i].newMogul(r);
            ro[i]=new Rocks();
            ro[i].newRock(r);
            
        }
         for(int i=0;i<numskiers;i++)
             {
                          s[i]=new Skiers();
                          s[i].newSkier(r);
             }
       
       }  
    
    
    public void start()
	{
		th = new Thread(this);
		th.start();
	}
    
   public void update(Graphics g)
    {
      
        
        if(img==null)
        {
            img=createImage(this.getSize().width,this.getSize().height);
            gps=img.getGraphics();
        }
        
        gps.setColor(getBackground());
        gps.fillRect(0,0 ,this.getSize().width,this.getSize().height);     
        
        gps.setColor(getForeground());
        paint(gps);
        g.drawImage (img, 0, 0, this);
    }
  public void paint(Graphics g)
    {
       
         g.setColor(Color.black);
         g.setFont(f);
         g.drawString("score :"+score, 10    , 40);
         System.out.println(direction);


          if(direction.equalsIgnoreCase("start1"))
          {
             g.drawImage(ii6, 50, 50, null);
             g.drawImage(ii15, 300, 250, null);
          }
         
          
           else if(direction.equalsIgnoreCase("start2"))
              {
                g.drawImage(ii7, 50, 50, null);
              }
           else if(direction.equalsIgnoreCase("start3"))
              {
                g.drawImage(ii10, 50, 50, null);
              }
           else if(direction.equalsIgnoreCase("pause"))
             {
                g.drawImage(ii5, 100, 100, null);
             }
           else if (direction.equalsIgnoreCase("finish"))
            {
             g.drawImage(ii11,50,50,null);  
             g.drawString(String.valueOf(score), 250, 380);
             removeKeyListener(this);
            
            try{
            bf=new BufferedReader(new FileReader(file));
            prev_score=bf.readLine();
           
            sc=Integer.parseInt(prev_score);
           
                System.out.println(prev_score);
                bf.close();
                bw=new BufferedWriter(new FileWriter(file));
                   if(sc<score)
                   {
                       bw.write(String.valueOf(score));
                       g.drawImage(ii16, 200, 400, null);
                   }else 
                   {
                       bw.write(String.valueOf(prev_score));
                   }
                   
           bw.close();
                    
        }catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
              
           }
  
     
   else {for(int j=0;j<numtrees;j++)
       {
            time++;
          
           if(direction.equalsIgnoreCase("left")||direction.equalsIgnoreCase(("downleft")))
               g.drawImage(ii12, x, y, null);
           
           if(direction.equalsIgnoreCase("right")||direction.equalsIgnoreCase("downright"))
               g.drawImage(ii13, x, y, null);
           
           if(direction.equalsIgnoreCase("down")||direction.equalsIgnoreCase(("downleft")))
               g.drawImage(ii14, x, y, null);
           if(direction.equalsIgnoreCase("stop"))
               g.drawImage(ii0, x, y, null);
       
           
        g.drawImage(ii, t[j].x,t[j].y, null);
        g.drawImage(ii1, ro[j].x,ro[j].y, null);
        g.drawImage(ii2, m[j].x,m[j].y, null);
         }
        for(int j=0;j<numskiers;j++)
       {
           if(s[j].skiersDirection.equalsIgnoreCase("left"))
            {
                g.drawImage(ii3, s[j].x, s[j].y, null);
            }
            else 
            {
                g.drawImage(ii4,s[j].x, s[j].y, null);
            }
       }
        
   }
           if(direction.equalsIgnoreCase("crashed"))
       
                 {
                     g.drawImage(ii8, x, y, null);
                     g.drawImage(ii9, x, y+5, null);
                   }
    }
    
public void run()
        {
       while (true){
           
           moveskie();
           if( crash())
                {
                    crash_counter++;
                    System.out.println("crash counter :"+crash_counter);
                    if(crash_counter>100){direction="finish";
                    repaint();
                    th.stop();}
              try{	
			Thread.sleep(20);
		 }catch(InterruptedException e) {}                        
          }
           if(score>23000)
           {
               th.stop();
               direction="finish";
               repaint();
           }
	                      repaint();
              
			try
			{
                            Thread.sleep(50);
		         }  
			catch(InterruptedException e) {} 
                       
		}}
	

    @Override
 public void keyTyped(KeyEvent ke) 
    {
       ke.consume();
    }

    @Override
 public void keyPressed(KeyEvent ke) {
        
    int k=ke.getKeyCode();
      if(direction.equalsIgnoreCase("start1"))
    {
       
         if(k==ke.VK_SPACE)
            direction="go";
         if(k==ke.VK_H)
            direction="start2";
    }
      
      else if(direction.equalsIgnoreCase("start2"))
    {
        if(k==ke.VK_G)
            direction="start1";
        if(k==ke.VK_H)
            direction="start3";
        
    }
      else if(direction.equalsIgnoreCase("start3"))
    {
         if(k==ke.VK_G)
            direction="start2";
    }
     
      
      else  if(direction.equalsIgnoreCase("pause"))
    {
        
        if(k==ke.VK_O){
            direction="go";
       }
    }
  else   
 {
     if(k==ke.VK_P)
     {
      direction="pause"   ;
     
     }
     
     if(k==ke.VK_V)
     {
      direction="downleft"   ;
     
     }
      if(k==ke.VK_N)
     {
      direction="downright"   ;
     
     }
       if(k==ke.VK_H)
     {
      direction="right"   ;
     
     }
        if(k==ke.VK_G)
     {
      direction="left"   ;
     
     }
        if(k==ke.VK_Y)
        {
            direction="stop";
        }
        if(k==ke.VK_B)
        {
            direction="down";
        }
 }
 
 repaint();
    }

    @Override
    public  void keyReleased(KeyEvent e) {
        
      e.consume();
    }boolean crash(){
       
       
        for(int i=0;i<20;i++){
       if(ro[i].x < x + 10 && ro[i].x > x - 10 && ro[i].y < 185 && ro[i].y > 165)
       {
           direction ="crashed";
           return true;
       }
       if(m[i].x < x + 20 && m[i].x > x - 20 && m[i].y < 185 && m[i].y > 165)
       {
           direction ="down"; return true;
       }
       if(t[i].x < x + 20 && t[i].x > x - 20 && t[i].y < 185 && t[i].y > 165)
       {
           direction ="crashed"; return true;
       }}for(int i=0;i<10;i++){

       }
      
    
       return false;
    }
void moveskie()
{
    
			if(direction.equals("downleft"))  
			{
                            score +=10;
				x = x - 4;  
				  	for(int i = 0; i < nummoguls; i++ ) 					
                                            m[i].moveMogul(10,r);
				for(int i = 0; i <numrocks; i++ )
					ro[i].moveRock(10,r);
				for(int i = 0; i <numtrees; i++ )
					t[i].moveTree(10,r);
					for(int i = 0; i <numskiers; i++ )
					s[i].moveSkier(7,r);
				if(x < 2)  
				{          
					direction = "downright";
					x = x + 8;
				}
			}
			if(direction.equals("downright"))  
			{
                            score+=10;
				x = x + 4; 
				for(int i = 0; i < nummoguls; i++ ) 
					m[i].moveMogul(10,r);
				for(int i = 0; i <numrocks; i++ )
					ro[i].moveRock(10,r);
				for(int i = 0; i <numtrees; i++ )
					t[i].moveTree(10,r);
					for(int i = 0; i <numskiers; i++ )
					s[i].moveSkier(7,r);
				if(x > 700)  
				{            
					direction = "downleft";
					x = x - 8;
				}
			}  
			
                        if(direction.equals("down"))  
			{ score+=10;
				for(int i = 0; i < nummoguls; i++ ) 
					m[i].moveMogul(10,r);
				for(int i = 0; i <numrocks; i++ )
					ro[i].moveRock(10,r);
				for(int i = 0; i <numtrees; i++ )
					t[i].moveTree(10,r);
					for(int i = 0; i <numskiers; i++ )
					s[i].moveSkier(7,r);
			}
			if(direction.equals("left"))  
			{
                            score+=5;
				x = x - 4;  
				for(int i = 0; i < nummoguls; i++ )  
					m[i].moveMogul(7,r);
				for(int i = 0; i <numrocks; i++ )
					ro[i].moveRock(7,r);
				for(int i = 0; i <numtrees; i++ )
					t[i].moveTree(7,r);
					for(int i = 0; i <numskiers; i++ )
					s[i].moveSkier(4,r);
				if(x < 2)  
				{          
                                    direction = "right";
					x = x + 8;
				}
			}
                      
                        if(direction.equals("right")) 
			{
                            score+=5;
				x = x + 4;  				
                                for(int i = 0; i < nummoguls; i++ )  
					m[i].moveMogul(7,r);
				for(int 
                                        i = 0; i <numrocks; i++ )
					
                                    ro[i].moveRock(7,r);
				for(int i = 0; i <numtrees; i++ )
					t[i].moveTree(7,r);
					for(int i = 0; i <numskiers; i++ )
					s[i].moveSkier(4,r);			
                                       if(x > 700)  
				{            
					direction = "left";
					x = x - 8;
				}
			}
			if(direction.equals("upleft"))  
			{
                            
				x = x - 2;  
				for(int i = 0; i <numskiers; i++ )  
				s[i].moveSkier(-5,r);
				if(x < 2)  
					x = x + 2;  
			}
			if(direction.equals("upright"))  
			{
				x = x + 2;  
				for(int i = 0; i < numskiers; i++ ) 
					s[i].moveSkier(-3,r);
				if(x > 700)  
					x = x - 2;  
			}
			if(direction.equals("stop"))  
			{for(int i = 0; i < numskiers; i++ )  
					s[i].moveSkier(-3,r);
			}
                        
                        if(direction.equals("crashed"))  
			{
                            score-=50;
                            for(int i = 0; i <numskiers; i++ )  
					s[i].moveSkier(-3,r);
			}
                        
}
}
