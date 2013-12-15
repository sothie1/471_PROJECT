/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mastermind_strategies;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import mastermind.Pattern;
import static mastermind.Mastermind.NumberOfColors;
import static mastermind.Mastermind.NumberOfPegs;
import static mastermind.Mastermind.MaxGuesses;
import static mastermind.Mastermind.R;
/**
 *
 * @author Jamison
 */
public class FiveGuess {
    
    public int [] games;
    public int [] lengthEach;
    public int [] gamePossible;
    private File txtFile;
    
    private FiveGuess(String txtFile){
        this.txtFile = new File(txtFile);
    }
    /*
     * Read file for integer strings and numColors and stores them in games array
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
        gamePossible = new int[numLinesInFile];
        Scanner s1 = null;
        int count2 = 0;
        try{
            s1 = new Scanner(txtFile);
            String [] sumthin2 = new String[2];
            while((sumthin2 = s1.nextLine().split(" ")) != null){
                gamePossible[count2] = Integer.parseInt(sumthin2[1]);
                games[count2] = Integer.parseInt(sumthin2[0]);
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
                    
        private int numChars;
        private int gamePossible;
        private ArrayList<Pattern> prevGuesses;
        private int[] available;
        private int[] answerArray;
        private int[] remaining;  
        
        public gNode(int game, int numChars, int gamePossible){  
          this.numChars = numChars;
          this.gamePossible = gamePossible;
          
          available = new int[gamePossible];
          for (int i = 0; i < gamePossible; i++){
              available[i] = i+1;
          }
          String temp = Integer.toString(game);
          answerArray = new int[temp.length()];
          for (int i = 0; i < temp.length(); i++)
          {
            answerArray[i] = temp.charAt(i) - '0';
          }
          /*
          remaining = new int[1296];
          ArrayList<Integer> test = new ArrayList<Integer>();
          int counter = 0;
          for(int a =1; a <= 6; a++){
              for(int b =1; b <= 6; b++){
                  for(int c = 1; c <= 6; c++){
                      for(int d = 1; d <= 6; d++){
                          String l = Integer.toString(a);
                          String k = Integer.toString(b);
                          String j = Integer.toString(c);
                          String h = Integer.toString(d);
                          String g = (l + k + j + h);
                          //remaining[counter] = Integer.parseInt(g);
                          remaining[counter] = Integer.parseInt(g);
                         // System.out.println(g);
                          counter++;
                      }
                  }
              }
          }
          */
         }
        
        public void start(){
            System.out.println("Starting game");
            System.out.print("Answer is: ");
            for (Integer i: answerArray){
                System.out.print(i);
            }
            System.out.println("\n");
            Pattern solution = new Pattern(answerArray);
            NumberOfPegs = numChars;
            
            // Random guess
            Random r = new Random();
            int nextGuess = remaining[r.nextInt(1296)];
            String temp = Integer.toString(nextGuess);
            answerArray = new int[temp.length()];
          for (int i = 0; i < temp.length(); i++)
          {
            answerArray[i] = temp.charAt(i) - '0';
          }
            
            NumberOfPegs = numChars;
            Pattern guess = new Pattern(new int[]{1,2,3,4});
            NumberOfPegs = numChars;
            guess.Evaluate(solution);
            System.out.println("Guess: " + nextGuess);
            System.out.println("Matches: " + guess.CountMatch());
            System.out.println("Misses: " + guess.CountMiss());
            
            
            
            
    }
    }
    public void playGames(){
       ArrayList<gNode> allGames = new ArrayList<gNode>();
       int counter = 0;
       for (Integer i: games){
           allGames.add(new gNode(i, lengthEach[counter],gamePossible[counter]));
           counter++;
       }
       allGames.get(0).start();
    }
    public static void main(String[] args){
        // this can be done via command line args if you so please
        System.out.println(args[0]);
        FiveGuess lol = new FiveGuess(args[0]);
        lol.getGames();
        lol.playGames();
    }
}  