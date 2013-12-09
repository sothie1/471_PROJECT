/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Heuristics;

import java.util.ArrayList;
import java.util.Random;
import traveling_saleman.DistantTable;
import traveling_saleman.MST;

/**
 *
 * @author Beautiful
 */
public class ChristofidesAlgorithm {
    
    private static ArrayList<Integer> path = null;
    private static MST minimum_spanning;
    private static DistantTable dist;
    
    //public static ArrayList<Integer> findPath(DistantTable table, int startPoint){
    public static MST findPath(DistantTable table, int startPoint){
        path = new ArrayList<Integer>();
        minimum_spanning = new MST();
        dist = table;
        PrimMinimumSpanning();
        return minimum_spanning;
    }
    
    private static void PrimMinimumSpanning(){
        ArrayList<Integer> tovisit = new ArrayList<Integer>();
        ArrayList<Integer> visited = new ArrayList<Integer>();
        Random r = new Random();
        
        //Pick a random starting point
        int startingPoint = r.nextInt(dist.getPointNum());
        int endpoint = 0;
     
        visited.add(startingPoint);
        minimum_spanning.addNode(startingPoint);
        for (int i = 0; i<dist.getPointNum();i++)
            if (i != startingPoint)
                tovisit.add(i);
                      
        System.out.println("Starting point: " + startingPoint);
        int start_point = startingPoint;
        int end_point = 0;
        double distant; 
        
        while (tovisit.size() != 0){
            start_point = -1;            
            distant = 0;    
            end_point = -1;
            
            for (int i = 0;i<visited.size();i++){
                for (int j = 0; j<tovisit.size();j++){
                    if (start_point != -1){
                        double d = dist.getDistant(visited.get(i), tovisit.get(j));
                        if (d < distant){
                            start_point = i;
                            end_point = j;
                            distant = d;
                        }
                    }
                    else{
                        start_point = i;
                        end_point = j;
                        distant = dist.getDistant(visited.get(i), tovisit.get(j));
                    }
                }
            }
            minimum_spanning.addNode(tovisit.get(end_point));
            minimum_spanning.addConnection(tovisit.get(end_point), visited.get(start_point));
            minimum_spanning.addConnection(visited.get(start_point), tovisit.get(end_point));
            visited.add(tovisit.remove(end_point));
        
        }
      
        
        //Getting all of the odd verticies
        ArrayList<Integer> oddVerticies = new ArrayList<Integer>();
        for (int i = 0; i < dist.getPointNum(); i++)
            if (minimum_spanning.getConnection(i).size() % 2 != 0)
                oddVerticies.add(i);

        int start_odd;
        while (oddVerticies.size() != 0){
            start_odd = oddVerticies.remove(0);
            //Linking all the odd verticies
            int idx = 0;
            double distant1 = dist.getDistant(start_odd, oddVerticies.get(idx));
            for (int i = 0;i < oddVerticies.size(); i++){
                double d = dist.getDistant(start_odd, oddVerticies.get(i));
                if (d < distant1){
                    idx = i;
                    distant1 = d;
                }
            }//End finding the smallest distant node
            minimum_spanning.addConnection(start_odd, oddVerticies.get(idx));
            minimum_spanning.addConnection(oddVerticies.get(idx), start_odd);
            oddVerticies.remove(idx);
        }
        for (int i = 0; i < dist.getPointNum();i++){
            if (minimum_spanning.getConnection(i).size() %2 !=0)
                System.out.println("Error " + i);
        }
        
        /*

        ArrayList<Integer> eulerCycle = new ArrayList<Integer>();
        int currentNode = startingPoint;
        for (int i = 0; i < dist.getPointNum() + 1;i++){
            ArrayList<Integer> connection = minimum_spanning.getConnection(currentNode);
            int nextPath ;
            //while((nextPath = r.nextInt(connection.size())) != startingPoint && eulerCy)
            
            eulerCycle.add(currentNode);
            //currentNode = connection.get(nextPath);
            
        }
                */
    }            
}
