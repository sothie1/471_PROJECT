/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fileprocess;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import traveling_saleman.Coordinate;
/**
 *Class that handles FileWriting and FileReading
 * @author sothie1
 */
public class ReadFile {
    
    /**
     * Take in the path to the file and parse it, store the coordinates in the
     * arraylist
     * @param String filepath ArrayList<Coordinate> coordinates 
     */
    public static void readFromFile(String filepath,ArrayList<Coordinate> coordinates){
        
        try{
            //Open the file and read it
            FileReader fileRead = new FileReader(filepath);
            BufferedReader buffRead = new BufferedReader(fileRead);
            String line = null;
            String[] read;
            
            //iterate through the file and parse the double coordinates
            while ((line=buffRead.readLine()) != null){
                read = line.split(" ");
                coordinates.add(new Coordinate(Double.parseDouble(read[0]),Double.parseDouble(read[1])));
            }
            //Close file;
            fileRead.close();
            buffRead.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * A method that takes in the filepath to write to and the coordinate list
     * Will loop through the coordinate and write it to file
     * @param String filepath
     * @param ArrayList<Coordinate> coordinates 
     */
    public static void writeToFile(String filepath,ArrayList<Coordinate> coordinates){
        
        try{
            //Open the file to write to
            FileWriter fileWrite = new FileWriter(filepath);
            BufferedWriter buffWrite = new BufferedWriter(fileWrite);
            
            //Loop through and print coordinate to file
            for (Coordinate coor : coordinates){
                buffWrite.write(coor.toString());
                buffWrite.newLine();
            }
            
            //Flushing the buffer and close the writers
            buffWrite.flush();
            fileWrite.close();
            buffWrite.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
