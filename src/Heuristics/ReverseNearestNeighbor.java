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
 * @author Beautiful
 */
public class ReverseNearestNeighbor {
    
        public static ArrayList<Integer> findPath(DistantTable table, int startPoint){
            ArrayList<Integer> tovisit = new ArrayList();
            //Initializing to visit points
            for (int i = 0; i<table.getPointNum();i++){
                if (i!=startPoint)
                    tovisit.add(i);
            }
            
            //The start and end of the path
            ArrayList<Integer> visit_end1 = new ArrayList();
            ArrayList<Integer> visit_end2 = new ArrayList();
            
            visit_end1.add(startPoint);
            visit_end2.add(startPoint);
            
            while(tovisit.size() != 0){
                
                int shortest_idx_1 = -1;
                double shortest_dist_end1=0;
            
                for (int i = 0;i<tovisit.size();i++){
                    if (!visit_end1.contains(tovisit.get(i)) && !visit_end2.contains(tovisit.get(i))){
                        
                        if (shortest_idx_1 == -1){
                            shortest_idx_1 = i;
                            shortest_dist_end1 = table.getDistant((int) (visit_end1.get(visit_end1.size()-1)), (int)(tovisit.get(i)));
                        }
                        
                        else{
                            double dst = table.getDistant((int)visit_end1.get(visit_end1.size() - 1), (int)tovisit.get(i));
                            if (dst < shortest_dist_end1){
                                shortest_idx_1 = i;
                                shortest_dist_end1 = dst;
                            }
                        }
                    }
                }
                visit_end1.add(tovisit.get(shortest_idx_1));
                tovisit.remove(shortest_idx_1);
                
                /******************************************************/
                if (tovisit.size() != 0){
                    
                    int shortest_idx_2 = -1;
                    double shortest_dist_end2=0;

                    for (int i = 0;i<tovisit.size();i++){
                        if (!visit_end1.contains(tovisit.get(i)) && !visit_end2.contains(tovisit.get(i))){

                            if (shortest_idx_2 == -1){
                                shortest_idx_2 = i;
                                shortest_dist_end2 = table.getDistant((int) (visit_end2.get(visit_end2.size()-1)), (int)(tovisit.get(i)));
                            }

                            else{
                                double dst = table.getDistant((int)visit_end2.get(visit_end2.size() - 1), (int)tovisit.get(i));
                                if (dst < shortest_dist_end2){
                                    shortest_idx_2 = i;
                                    shortest_dist_end2 = dst;
                                }
                            }
                        }
                    }
                    visit_end2.add(tovisit.get(shortest_idx_2));
                    tovisit.remove(shortest_idx_2);
                    
                }
              
                //Get the shortest index for end 1
                
            }
            
            for (int i = 0; i < visit_end2.size() ;i++){
                visit_end1.add(visit_end2.get(visit_end2.size()- 1 - i));
            }
            /*
            for (int i = 0; i < visit_end2.size() -1 ;i++){
                visit_end1.add(visit_end2.get(i));
            }*/
            
            return visit_end1;
        }
}
