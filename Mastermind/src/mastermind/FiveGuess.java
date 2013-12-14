/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mastermind;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jamison
 */
public class FiveGuess {
    
    public int [] games;
    public int [] lengthEach;
    private File txtFile;
    
    private FiveGuess(String txtFile){
        this.txtFile = new File(txtFile);
    }
    /*
     * Read file for integer strings and stores them in games array
     * SO UGLY BUT IT WORKS SO DON'T TOUCH
     */
    public void getGames(){
        Scanner s = null;
        int numLinesInFile = 0;  
        try {
            s = new Scanner(txtFile);
            String sumthing = "";
            while((sumthing = s.nextLine()) != null){
                numLinesInFile++;
            }
            System.out.println(numLinesInFile);
        } catch (FileNotFoundException ex) {
            ;
        }
        catch(Exception e){
            ;
        }finally{
            s.close();
        }
        games = new int[numLinesInFile];
        lengthEach = new int[numLinesInFile];
        Scanner s1 = null;
        int count2 = 0;
        try{
            s1 = new Scanner(txtFile);
            String sumthin2 = "";
            while((sumthin2 = s1.nextLine()) != null){
                games[count2] = Integer.parseInt(sumthin2);
                count2++;
            }
        }catch (Exception e){
            ;
        }finally{
            s1.close();
        }
        count2 = 0;
        for (Integer w: games){
            lengthEach[count2] = (int)(Math.log10(w)+1);
            count2++;
        }
    
    }
    public class gNode{
        
        private int gameAnswer;
        private int numChars;
        private ArrayList<Integer> available;
        
        public gNode(int game, int numChars){
          this.gameAnswer = game;  
          this.numChars = numChars;
          available = new ArrayList<Integer>();
          available.add(1);
          available.add(2);
          available.add(3);
          available.add(4);
          available.add(5);
          available.add(6);
        }
        
        public void playGame(){
            
        }
    }
    
    
    public static void main(String[] args){
        // this can be done via command line args if you so please
        FiveGuess lol = new FiveGuess("C:/Users/Jamison/Documents/GitHub/471_PROJECT/Mastermind/input.txt");
        lol.getGames();
    }
    
    
}