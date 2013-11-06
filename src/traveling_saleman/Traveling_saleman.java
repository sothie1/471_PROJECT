/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
    }
}
