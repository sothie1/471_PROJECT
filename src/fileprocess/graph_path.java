/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fileprocess;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import traveling_saleman.Coordinate;
import traveling_saleman.DistantTable;
import traveling_saleman.MST;

/**
 *
 * @author Beautiful
 */
public class graph_path extends JFrame{
    
    ArrayList<Coordinate> coords;
    ArrayList path;
    DrawPane drawPanel;
    MST minimum_span;
    
    public graph_path(ArrayList<Coordinate> coords, ArrayList path,DistantTable dist){
        this.coords = coords;
        this.path = path;
        this.setSize(450,490);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Center the jframe.
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dim = toolkit.getScreenSize();
        this.setLocation((int)(dim.getWidth() - this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
        
        drawPanel = new DrawPane(coords,path,dist);
        drawPanel.setPreferredSize(new Dimension(400,400));
        drawPanel.setBackground(Color.black);
        
        this.add(drawPanel);
        this.setVisible(true);
    }
    
    public graph_path(ArrayList<Coordinate> coords,MST mini,DistantTable dist){
        this.coords = coords;
        this.setSize(450,490);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.minimum_span = mini;
        //Center the jframe.
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dim = toolkit.getScreenSize();
        this.setLocation((int)(dim.getWidth() - this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
        
        drawPanel = new DrawPane(coords,path,mini,dist);
        drawPanel.setPreferredSize(new Dimension(400,400));
        drawPanel.setBackground(Color.black);
        
        this.add(drawPanel);
        this.setVisible(true);
    }
    
    private class DrawPane extends JPanel{
 
        private ArrayList<Coordinate> coord;
        private ArrayList path;
        private DistantTable dist = null;
        
        double max_x = 0;
        double max_y = 0;
        MST minimum_span = null;
        
        public DrawPane(ArrayList<Coordinate> coord, ArrayList path){
            this.coord = coord;
            this.path = path;
            
            for (Coordinate co : coord){
                if (co.getX() > max_x)
                    max_x = co.getX();
                if (co.getY() > max_y)
                    max_y = co.getY();
            }
        }
        
        public DrawPane(ArrayList<Coordinate> coord, ArrayList path, MST mini, DistantTable dist){
            this.coord = coord;
            this.path = path;
            this.minimum_span = mini;
            this.dist = dist;
            
            for (Coordinate co : coord){
                if (co.getX() > max_x)
                    max_x = co.getX();
                if (co.getY() > max_y)
                    max_y = co.getY();
            }
        }
        
        public DrawPane(ArrayList<Coordinate> coord, ArrayList path,DistantTable dist){
            this.coord = coord;
            this.path = path;
            this.dist = dist;
            
            for (Coordinate co : coord){
                if (co.getX() > max_x)
                    max_x = co.getX();
                if (co.getY() > max_y)
                    max_y = co.getY();
            }
        }
        
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(1));
            
            double total_length = 0;
            if (minimum_span == null){
                for (int i = 0; i<path.size() -1;i++){
                    int scale_x = (int) ((coord.get((int)path.get(i)).getX()/max_x) * 300);
                    int scale_y = (int) ((coord.get((int)path.get(i)).getY()/max_y) * 300);
                    Ellipse2D.Double circle = new Ellipse2D.Double(scale_x+50,scale_y+50, 4, 4);
                    if (i == 0)
                        g2.setColor(Color.BLUE);
                    else
                        g2.setColor(Color.WHITE);
                    g2.fill(circle);

                    int scale_x2 = (int) ((coord.get((int)path.get(i + 1)).getX()/max_x) * 300);
                    int scale_y2 = (int) ((coord.get((int)path.get(i + 1)).getY()/max_y) * 300);
                    g2.setColor(Color.RED);
                    g2.drawLine(scale_x+50, scale_y+50, scale_x2+50, scale_y2+50);

                    if (dist != null){
                        total_length+=dist.getDistant((int)path.get(i), (int)path.get(i+1));
                    }
                }
                g2.setColor(Color.WHITE);
                g2.drawString("Total distant = " + total_length, 0, 10);
            }
            //Render the minimum spanning tree
            else{
                for (int i =0 ;i < dist.getPointNum();i++){
                    ArrayList<Integer> connection = minimum_span.getConnection(i);
                    Coordinate center = coord.get(i);
                    int scale_x = (int) ((center.getX()/max_x) * 300);
                    int scale_y = (int) ((center.getY()/max_y) * 300);
                    g2.setColor(Color.white);
                    Ellipse2D.Double circle = new Ellipse2D.Double(scale_x+50,scale_y+50, 4, 4);
                    g2.draw(circle);
                    
                    g2.setColor(Color.red);
                    for (int conn : connection){
                        Coordinate connect = coord.get(conn);
                        int scale_x2 = (int) ((connect.getX()/max_x) * 300);
                        int scale_y2 = (int) ((connect.getY()/max_y) * 300);
                        g2.drawLine(scale_x+50, scale_y+50, scale_x2+50, scale_y2+50);
                    }
                }
            }
            //Draw the x-axis
            //g2.drawLine(0, 200, 400, 200);
            //Draw the y-axis
            //g2.drawLine(200, 0, 200, 400);
        }
    }
}


