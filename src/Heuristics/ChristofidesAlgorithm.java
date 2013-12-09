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
        dist = table;
        PrimMinimumSpanning();
        return minimum_spanning;
    }
    
    private static void PrimMinimumSpanning(){
        
        boolean even = false;
        Random r = new Random();
        ArrayList<Integer> previousStart = new ArrayList<Integer>();
        boolean failedSearch = false;
        
        int count_itr = 0;
        while (!even){
            count_itr++;
            minimum_spanning = new MST();
            int startingPoint;
            while(previousStart.contains(startingPoint = r.nextInt(dist.getPointNum())));
            //startingPoint = 7;
            calcMinimumSpanning(startingPoint);
            int numofOdd = 0;
            for (int i = 0; i<dist.getPointNum();i++){
                if (minimum_spanning.getConnection(i).size() % 2 !=0)
                    numofOdd ++;
            }
            
            if ((double)count_itr / dist.getPointNum() > .5){
                even = true;
                failedSearch = true;
            }
            if (numofOdd % 2 == 0)
                even = true;
            else
                System.out.println("Fuck");
            System.out.print("MST Iterations: " + count_itr);
        }
        
        if (failedSearch){
            System.out.println("Cannot find odd, pass threshold itr: " + count_itr + " Total size: " + dist.getPointNum());
            System.exit(0);
        }
       
        
        //Getting all of the odd verticies
        ArrayList<Integer> oddVerticies = new ArrayList<Integer>();
        for (int i = 0; i < dist.getPointNum(); i++)
            if (minimum_spanning.getConnection(i).size() % 2 != 0)
                oddVerticies.add(i);

        System.out.println("Number of odd: " + oddVerticies.size());
        int start_odd;
        while (oddVerticies.size() != 0){
            start_odd = oddVerticies.remove(0);
            //Linking all the odd verticies
            int idx = -1;
            double distant1 = 0;
            for (int i = 0;i < oddVerticies.size(); i++){
                if (idx == -1 &&!minimum_spanning.getConnection(start_odd).contains(oddVerticies.get(i))){
                    distant1 = dist.getDistant(start_odd, oddVerticies.get(i));
                    idx = i;
                }
                else if (idx!=-1){
                    double d = dist.getDistant(start_odd, oddVerticies.get(i));
                    if (d < distant1 && !minimum_spanning.getConnection(start_odd).contains(oddVerticies.get(i))){
                        idx = i;
                        distant1 = d;
                    }
                }
            }//End finding the smallest distant node
            System.out.println((oddVerticies.get(idx)+1) + " " + (1+start_odd));
            
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
    
    private static void calcMinimumSpanning(int startingPoint){
        ArrayList<Integer> tovisit = new ArrayList<Integer>();
        ArrayList<Integer> visited = new ArrayList<Integer>();
        
        //Pick a random starting point
        //startingPoint = 0;
        int endpoint = 0;
     
        visited.add(startingPoint);
        minimum_spanning.addNode(startingPoint);
        for (int i = 0; i<dist.getPointNum();i++)
            if (i != startingPoint)
                tovisit.add(i);
                      
        System.out.println("Starting point: " + startingPoint);
        int start_point = 0;
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
    }
}
