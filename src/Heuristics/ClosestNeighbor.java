/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Heuristics;

import java.util.ArrayList;
import traveling_saleman.DistantTable;

/**
 *
 * @author sothiara
 */
public class ClosestNeighbor {

    public static void findPath(DistantTable table, String writeout){

    }
    
    public static void findPath(DistantTable table, int startPoint,String writeout){
        
    }
    
    public static ArrayList findPath(DistantTable table, int startPoint){
        ArrayList tovisit = new ArrayList();
        ArrayList visited = new ArrayList();
        for (int i = 0; i<table.getPointNum();i++){
            if (i!=startPoint)
                tovisit.add(i);
        }
        
        visited.add(startPoint);
        
        int currentNode = startPoint;
        while(tovisit.size() != 0){
            
            int nextPoint = (int)tovisit.get(0);
            double distant = table.getDistant(currentNode, nextPoint);
            
            for (int i =1;i<tovisit.size();i++){
                double itrDistant = table.getDistant((int)tovisit.get(i),currentNode);
                if (itrDistant < distant && itrDistant != 0){
                    nextPoint = (int)tovisit.get(i);
                    distant = itrDistant;
                }
            }
            visited.add(nextPoint);
            //System.out.print("Next point: " + nextPoint);
            //System.out.println(" size: "+tovisit.size());
            tovisit.remove(tovisit.indexOf(nextPoint));
            currentNode = nextPoint;
        }
        /*
        for (int i =0;i<visited.size();i++){
            System.out.println(visited.get(i));
        }*/
        visited.add(startPoint);
        return visited;
    }
}
