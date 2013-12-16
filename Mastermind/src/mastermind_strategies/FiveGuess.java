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
import mastermind.Pegs;
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
        private ArrayList<Pegs> prevGuesses;
        private ArrayList<Integer> prevGuessInt;
        private ArrayList<Integer> prevGuessHit;
        private ArrayList<Integer> prevGuessMiss;
        private int[] available;
        private int[] answerArray;
        private int[] remaining;
        private int[] currGuess;
        
        public gNode(int game, int numChars, int gamePossible){  
          this.numChars = numChars;
          this.gamePossible = gamePossible;
          prevGuesses = new ArrayList<Pegs>();
          prevGuessInt = new ArrayList<Integer>();
          prevGuessHit = new ArrayList<Integer>();
          prevGuessMiss = new ArrayList<Integer>();
          available = new int[gamePossible];
          for (int i = 0; i < gamePossible; i++){
              available[i] = i+1;
          }
          String temp = Integer.toString(game);
          answerArray = new int[temp.length()];
          for (int i = 0; i < temp.length(); i++){
             answerArray[i] = temp.charAt(i) - '0';
          }
      
      }
        
        public int[] getRandom(int length){
            Random r = new Random();
                 String temp = ""; //remaining[r.nextInt(1296 - 1)];
                 int count = 0;
                 while(count < numChars){
                     int t = r.nextInt(length) + 1;
                     temp += Integer.toString(t);
                     count++;
                 }
                 prevGuessInt.add(Integer.parseInt(temp));
                 currGuess = new int[temp.length()];
                 for (int i = 0; i < temp.length(); i++)
                 {
                    currGuess[i] = temp.charAt(i) - '0';
                 }
                 return currGuess;
        }

        public void start(){
            System.out.println("Starting game");
            System.out.print("Answer is: ");
            for (Integer i: answerArray){
                System.out.print(i);
            }
            System.out.println("Number of pegs first: " + numChars + "\n");
            Pegs solution = new Pegs(answerArray);
            NumberOfPegs = numChars;
            // Random guss
            Pegs guess = new Pegs(getRandom(gamePossible));
            int numGuess = 0;
            while(true){
             //   System.out.println("Stuck here 1");
                 numGuess++;
                 //Pegs copyGuess = guess.Clone();
                 System.out.println("Guessing now: " + guess.toString());
                 guess.Evaluate(solution);
                 prevGuesses.add(guess);
                 prevGuessHit.add(guess.CountMatch());
                 prevGuessMiss.add(guess.CountMiss());
                 if (guess.CountMatch() == 4){
                     if (numGuess <= 8){
                        System.out.println("I won the game in " + numGuess + " moves! :)");
                     }else{
                         System.out.println("I lost the game in " + numGuess + " moves :(");
                     }
                     break;
                 }
                 while(true){
           //          System.out.println("Stuck here 2");
                     guess = new Pegs(getRandom(gamePossible));
                     boolean consistent = true;
                     for (int i = 0; i < prevGuesses.size(); i++){
                         guess.Evaluate(prevGuesses.get(i));
                         int numMatch = guess.CountMatch();
                         int numMiss = guess.CountMiss();
                         if ((numMatch != prevGuessHit.get(i)) || (numMiss != prevGuessMiss.get(i))){
                             consistent = false;
                             break;
                        }
                     }
                     if (consistent){
                         break;
                     }
                 }
            }
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
       allGames.get(1).start();
       allGames.get(2).start();
       allGames.get(3).start();
       allGames.get(4).start();
       allGames.get(5).start();
       allGames.get(6).start();
       allGames.get(7).start();
       
   
    int c = 0;
       for (Integer g: games){
        System.out.println("Game: " + g + " Length: " + lengthEach[c] + " Combins: " + gamePossible[c]);
                c++;
     }
    }
    public static void main(String[] args){
        // this can be done via command line args if you so please
        System.out.println("WHAT THE FUCL");
        System.out.println(args[0]);
        FiveGuess lol = new FiveGuess(args[0]);
        lol.getGames();
        lol.playGames();
    }
}  