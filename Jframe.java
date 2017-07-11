import java.awt.Color;  
import java.awt.Graphics;  
import java.awt.Point;  
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;  
import java.awt.event.WindowAdapter;  
import java.awt.event.WindowEvent;  
import java.util.ArrayList;  
import java.util.Iterator;  
  
import javax.swing.JFrame;  
import javax.swing.JOptionPane;  
  
class Jframe extends JFrame{   
static int width=800,height=800,n=15,r=23;  
static int  w=width/n,h=height/n,beg=60;  
ArrayList<Point> points = null;  
public Jframe(){   
   points = new ArrayList<Point>();  
   this.setTitle("老西五子棋v1.0（电脑弱智版）");  
   this.setSize(width+100,height+100);   
   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
   setVisible(true);   
   this.addMouseListener(new MyMouseListener());  
   this.addWindowListener(new WindowAdapter(){  
       public void windowClosing(WindowEvent e)  
       {  
        setVisible(false);  
        System.exit(0);  
       }  
      });  
   this.setBackground(Color.GREEN);  
}   
@Override   
public void paint(Graphics g) {   
       super.paint(g);   
       //画边界  
       g.setColor(Color.GRAY);  
       for(int i=0;i<=n;i++){  
           g.drawLine(beg+i*w, beg, beg+i*w, beg+n*h);  
       }  
       for(int i=0;i<=n;i++){  
           g.drawLine(beg, beg+i*h, beg+n*w, beg+i*h);  
       }  
       // 画棋子  
         
       Iterator<Point> it = points.iterator();  
       Color c = g.getColor();  
       while(it.hasNext())  
       {  
        qi p = (qi)it.next();  
        if(p.color==1)  
            g.setColor(Color.black);  
        else  
            g.setColor(Color.yellow);  
        g.fillOval(p.x-r,p.y-r,2*r,2*r);  
        if(it.hasNext()==false){  
            g.setColor(Color.red);  
            g.fillOval(p.x-4,p.y-4,8,8);  
        }  
       }  
       g.setColor(Color.black);  
       if(AB.round%2==1)  
       g.drawString("轮到黑方", beg, beg+n*h+20);  
       else  
       g.drawString("轮到白方", beg, beg+n*h+20);  
       System.out.println();  
       for(int i=0;i<=n;i++){  
           for(int j=0;j<=n;j++){  
               System.out.print(AB.G[i][j]+" ");  
           }System.out.println();  
       }  
       int ans=AB.checkVictory(AB.G);  
       if(ans==1){  
           JOptionPane.showMessageDialog(null, "黑方获胜！");   
           
       }else if(ans==-1){  
           JOptionPane.showMessageDialog(null, "白方获胜！");   
             
       }  
       if(ans!=0){  
           points.clear();  
           for(int i=0;i<=n;i++){  
               for(int j=0;j<=n;j++){  
                   AB.G[i][j]=0;  
               }  
           }  
           AB.round=1;  
           begin.clicknum=0;  
       }  
}   
public double dis(double x1,double y1,double  x2,double y2){  
    return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));  
}  
  
public void addPoint(Point p)  
{  
 int flag=0;  
 for(int i=0;i<=n;i++){  
     for(int j=0;j<=n;j++){  
         if(dis(beg+i*w,beg+j*h,p.x,p.y)<25){  
             flag=1;  
             p.x=beg+i*w;p.y=beg+j*h;  
             break;  
         }  
     }  
     if(flag==1) break;  
 }  
 if(flag==0)return ;  
 Iterator<Point> i = points.iterator();  
 while(i.hasNext())  
 {  
  qi k = (qi)i.next();  
  if(k.x==p.x&&k.y==p.y){  
      JOptionPane.showMessageDialog(null, "该区域已经有子，请您重放~");   
      return ;  
  }  
 }  
   
 qi temp= new qi(p.x,p.y);  
 if(AB.round%2==1){  
     temp.color=1;  
 }else{  
     temp.color=-1;  
 }  
 AB.G[(temp.y-beg)/h][(temp.x-beg)/w]=temp.color;  
 AB.round++;  
 points.add(temp);  
}  
    
  static void turnPC(){  
      if(AB.checkVictory(AB.G)!=0)return ;  
      Jframe mf=begin.local;  
      Point ansp = new Point(AB.round/2%(AB.n+1),AB.round/2/(AB.n+1));  
         int maxx=-AB.INF;  
         for(int i=0;i<=AB.n;i++){  
             for(int j=0;j<=AB.n;j++){  
                 if(AB.G[i][j]!=0)continue;  
                 //下子范围  
                 if(AB.round<=4){  
                     int flag=1;  
                     if(i>=1&&i<=n-1&&j>=1&&j<=n-1){  
                         for(int k=i-1;k<=i+1;k++){  
                             for(int l=j-1;l<=j+1;l++){  
                                 if(AB.G[k][l]==-1||AB.G[k][l]==1)  
                                     flag=0;  
                             }if(flag==0) break;  
                         }  
                     }  
                     if(flag==1)continue;  
                 }  
                 int curG[][]=new int[20][20];  
                 AB.copy(AB.G, curG);  
                 curG[i][j]=-1;  
                 int k=AB.alphabeta(curG,1,-AB.INF,AB.INF,1);  
               //  System.out.print(k+" ");  
                 if(k>maxx){  
                     maxx=k;ansp.x=j;ansp.y=i;  
                 }  
             }  
         }  
         ansp.x*=Jframe.w;ansp.x+=Jframe.beg;  
         ansp.y*=Jframe.h;ansp.y+=Jframe.beg;  
         mf.addPoint(ansp);  
         mf.repaint();  
  }  
  
}   
class MyMouseListener extends MouseAdapter  
{  
 public void mouseClicked(MouseEvent e)  
 {  
     if(AB.round%2==0)return ;  
     Jframe mf = (Jframe)e.getSource();  
     int before=AB.round;  
     mf.addPoint((new Point(e.getX(),e.getY())));  
     mf.repaint();  
     if(before==AB.round)return ;  
     begin.clicknum++;  
 }  
   
}  