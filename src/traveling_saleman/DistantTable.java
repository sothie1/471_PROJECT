/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package traveling_saleman;

import java.util.ArrayList;

/**
 *
 * @author sothiara
 */
public class DistantTable {
 
    private ArrayList<double[]> coordTable = new ArrayList<double[]>();
    
    public DistantTable(ArrayList<Coordinate> coord){
        setTable(coord);
    }
    
    public DistantTable(){
        
    }
    
    public void setTable(ArrayList<Coordinate> coord){
        
        for (int i = 0; i < coord.size() - 1; i++){
            double[] distant = new double[coord.size() - (i + 1)];
            for (int j = i + 1;j<coord.size();j++){
                Coordinate c1 = coord.get(i);
                Coordinate c2 = coord.get(j);
                double distantC = Math.sqrt(Math.pow(c2.getY() - c1.getY(),2) + Math.pow(c2.getX() - c1.getX(),2));
                distant[j-(i+1)] = distantC;
            }
            coordTable.add(distant);
        }
    }
    
    public double getDistant(int point1, int point2){
        if (point1 > coordTable.size()+1 || point2 > coordTable.size() + 1)
            return -1;
        if (point1 == point2)
            return 0;
        else if (point1 > point2){
            return coordTable.get(point2)[point1-point2 - 1];
        }
        else{
            return coordTable.get(point1)[point2-point1 - 1];
        }
    }
    
    public void printTable(){
        int dummy = 0;
        for (int i = 0; i < coordTable.size();i++){
            System.out.format("%-6d :",i);
            double[] d = coordTable.get(i);
            for (int k=d.length;k<coordTable.size()+1;k++){
                System.out.format("%-6d",dummy);
            }
            for (int j = 0; j<d.length;j++){
                System.out.format("%-1.4f", d[j]);
            }
            System.out.println("");
        }   
    }
    
    public int getPointNum(){
        return coordTable.size() + 1;
    }
}
