import javax.swing.*;   
import java.awt.*;   
import java.awt.event.*;  
import java.util.ArrayList;  
import java.util.Iterator;  
  
  
public class begin {   
    static Jframe local;  
    static int clicknum=0;  
    static int over=0;  
public static void main (String args[]) {   
    local=new Jframe();   
      
    while(true){    
        int last=clicknum;  
        Point point = MouseInfo.getPointerInfo().getLocation();    
        if(clicknum>last)  
        Jframe.turnPC();  
       // System.out.println(point);    
      //  try { Thread.sleep(100); } catch (InterruptedException e) { }    
      
    }    
 }   
}  