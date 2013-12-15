/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Heuristics;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
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
    
    public static ArrayList<Integer> findPath(DistantTable table, int startPoint){
    //public static MST findPath(DistantTable table, int startPoint){
        path = new ArrayList<Integer>();
        dist = table;
        //PrimMinimumSpanning();
        //return minimum_spanning;
        path = PrimMinimumSpanning();
        return path;
    }
    
    private static ArrayList<Integer> PrimMinimumSpanning(){
        boolean even = false;
        Random r = new Random();
        ArrayList<Integer> previousStart = new ArrayList<Integer>();
        boolean failedSearch = false;
        
        ArrayList<Integer> path = new ArrayList<Integer>();
        
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

        
        ArrayList<Integer> oddBack = (ArrayList<Integer>)oddVerticies.clone();
        boolean finish = false;
        boolean another_start = false;
        int start_odd = -1;
        while (!finish){                
            if (!another_start)
                start_odd = oddVerticies.remove(0);
            else{
                another_start = false;
            }
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
            if (idx == -1){
                oddVerticies = (ArrayList<Integer>)oddBack.clone();
                oddVerticies.remove((Integer)start_odd);
                another_start = true;
            }
            else{
                try{
                    minimum_spanning.addConnection(start_odd, oddVerticies.get(idx));
                    minimum_spanning.addConnection(oddVerticies.get(idx), start_odd);
                    oddVerticies.remove(idx);
                    if (oddVerticies.size() == 0)
                        finish = true;
                }catch(Exception e){
                    ArrayList<Integer> startOdd = minimum_spanning.getConnection(start_odd);
                    JOptionPane.showMessageDialog(null,"Cannot match odd index");
                }
            }
        }

        
        //Finding the euler cycle
        int index = -1;
        //Selecting a starting point where the number of connected branch is 2
        ArrayList<Integer> tovisit = new ArrayList<Integer>();
        for (int i = 0;i<dist.getPointNum();i++){
            if (minimum_spanning.getConnection(i).size() == 2){
                index = i;
                break;
            }
        }
        for (int i = 0;i<dist.getPointNum();i++){
            if (i != index){
                tovisit.add(i);
            }
        }
        
        if (index == -1)
            JOptionPane.showMessageDialog(null, "Unable to find starting point");
        //===========================================================================
        path.add(index);
        int currentIndex = index;
        int nextIndex = -1;
        while (tovisit.size() != 0){
            
            boolean isCycle = true;
            //Determining cycle and getting rid of cycle
            ArrayList<Integer> cycle = minimum_spanning.getConnection(currentIndex);
            //Check the current index cycle
            for (int i = 0; i < cycle.size();i++)
                if (!path.contains(cycle.get(i)))
                    isCycle = false;
            //Getting the exit point
            int exit_point = -1;
            double distant = 0;
            
            if (isCycle){
                for (int i = 0 ;i < tovisit.size();i++){
                    if (exit_point == -1){
                        exit_point = tovisit.get(i);
                        distant = dist.getDistant(tovisit.get(i), currentIndex);
                    }
                    else{
                        double d = dist.getDistant(tovisit.get(i), currentIndex);
                        if (d<distant){
                            distant = d;
                            exit_point = tovisit.get(i);
                        }
                    }
                }//End for the for loop
                currentIndex = exit_point;
                path.add(currentIndex);
                tovisit.remove((Integer)exit_point);
            }else{
                //====================================================================
                ArrayList<Integer> link = minimum_spanning.getConnection(currentIndex);
                if (link.size() == 2){
                   if (path.contains(link.get(0))){
                       path.add(link.get(1));
                       currentIndex = link.get(1);
                       tovisit.remove((Integer)link.get(1));
                   }
                   else{
                       path.add(link.get(0));
                       currentIndex = link.get(0);
                       tovisit.remove((Integer)link.get(0));

                   }
                }else{
                    boolean exit = false;
                    while (!exit){
                        nextIndex = link.get(r.nextInt(link.size()));
                        if (!path.contains(nextIndex) && index != nextIndex)
                            exit = true;
                    }
                    currentIndex = nextIndex;
                    path.add(currentIndex);
                    tovisit.remove((Integer)currentIndex);
                }
            }
            
        }
        
        //===========================================================================
       
        path.add(index);
        return path;
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
