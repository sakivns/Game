/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skating;

/**
 *
 * @author Vikas Yadav
 */
public class overloading {
   public static void main(String args[])
   {
       parent paa=new child();
       vika v=new vika();
       v.test(paa);
   }
   
           
}
class vika{
     void test(parent p)
   {
       System.out.println("parent");
   }
   void test(child c)
   {
       System.out.println("child");
   }
}
class child extends parent{
    
}
class parent{
    
}