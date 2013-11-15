/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * 
 * QUESTIONS FROM JAMISON:
 * 1) What is the 2d array of coordinates storing? Why 2d?
 * 2) Do we have to store the names of each thing?
 * 
 */
package traveling_saleman;

import javax.swing.JOptionPane;
import fileprocess.*;
import java.util.ArrayList;

public class Traveling_saleman {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Check if file path is present
        if (args.length != 1){
            System.out.println("Need 1 argument for coordinate file");
            System.exit(-1);
        }
        
        //Setting the lookup table for the distance between coordinates
        double[][] distant_coord;
        //Storing the coordinate as an arraylist of coordinate objects
        ArrayList<Coordinate> coordinates = new ArrayList();
        //Reading the coordinates from the file and store them in the list
        ReadFile.readFromFile(args[0],coordinates);
        //Initialing the distant array;
        distant_coord = new double[coordinates.size()][coordinates.size()];
        
        JOptionPane.showMessageDialog(null,"Hello World");
    }
    
    // Takes in a list of coordinates left to travel to, and then 2 nulls
    // Will recursively call self and change nulls to values
    // Returns the arraylist of coordinates in order of "shortest path"
    public ArrayList<Coordinate> getMin(ArrayList<Coordinate> remaining, ArrayList<Coordinate> path, 
            Coordinate startAndEnd, Coordinate current){
        
        // make sure there's enough coordinates left
        if (remaining.size() < 2){
            path.add(startAndEnd);
            return path;
        }
        
        // should only happen once, when this function runs the first time
        if (startAndEnd == null){
            path.add(remaining.get(0));
            current = remaining.get(0);
            remaining.remove(0);
        }
        
        // Finds shortest distance out of all coordinates
        Coordinate temp = remaining.get(0);
        int location = 0;
        for (int i = 0; i < remaining.size()-1; i++){
            if (current.getDistant(remaining.get(i)) < current.getDistant(remaining.get(i+1))){
                temp = remaining.get(i);
                location = i;
            }
        }
        
        // Checks for last element in list
        if (current.getDistant(remaining.get(remaining.size())) < current.getDistant(temp)){
            temp = remaining.get(remaining.size());
            location = remaining.size();
        }
        
        // must be done before calling again
        path.add(temp);
        current = temp;
        remaining.remove(location);
        
        getMin(remaining, path, startAndEnd, current);
        
        // Does this happen more than once?  I forgot recursion.  I only want it
        // to happen at the end when the path is complete.
        return path;
    }


}
