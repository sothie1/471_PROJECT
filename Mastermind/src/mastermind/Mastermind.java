/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// Jamison Testing push

package mastermind;
import java.util.Random;

/**
 *
 * @author MDavis
 */
public class Mastermind {
    
    public static int NumberOfPegs = 5;
    public static String[] ColorPalette = {"Red", "Blue", "Green", "White",
            "Yellow", "Magenta", "Cyan", "Black", "Orange"};
        
    /**
     * @param solution
     * @param guess
     */
    public static void RowTest(PegRow solution, PegRow guess)
    {
        boolean hit;
        int i, j;
        int score_match = 0;
        int score_miss = 0;
        // Reset:
        solution.Reset();
        guess.Reset();

        // Check for full matches
        //System.out.println("Checking for full matches:");
        for (i = 0; i<NumberOfPegs; i++)
        {
            //System.out.print(i+": Guessing "+guess.Get(i).GetColor()+"...");
            if (solution.GetValue(i)==guess.GetValue(i))
            {
                //System.out.println("-> Match at "+i);
                score_match++;
                guess.Set(i,-2);
                solution.Set(i,-2);
            }     
            //else System.out.println("");
            //System.out.print("Solution: "); PrintRow();
            //System.out.print("Guess:\t"); guess.PrintRow();
        }

        //System.out.println("Checking for missed colors:");
        for (i = 0; i<NumberOfPegs; i++)
        {
            hit = false;
            if (guess.GetValue(i)<0)
                continue;
            //else System.out.println(i+": Guessing "+guess.Get(i).Print()+"...");
            for (j=0; j< NumberOfPegs; j++)
            {
                if (hit==false &&
                        solution.GetValue(j)==guess.GetValue(i))
                {
                    guess.Set(i, -1);
                    solution.Set(j,-1);
                    hit = true;
                }
            }
            if (hit==true) score_miss++;
            //System.out.print("Solution: "); this.PrintRow();
            //System.out.print("Guess:\t"); guess.PrintRow();

        }
                    // This is what actually gets printed
        solution.Reset();
        guess.Reset();
        //System.out.print("Solution: "); this.PrintRow();
        //System.out.print("Guess:\t"); guess.PrintRow();
        //System.out.print("Answer:\t");
        for(i=0; i<score_match; i++)
            System.out.print("(=) ");
        for(i=0; i<score_miss; i++)
            System.out.print("(/) ");
        for(i=0; i<(NumberOfPegs-score_match-score_miss); i++)
            System.out.print("( ) ");
        System.out.println();
        // The program will evaluate this somehow
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        //int NumberOfPegs = 5;
        int NumberOfColors = 4;
        int i;

        Random randGen = new Random();
        
        
        int[] solutionValues = new int[NumberOfPegs];
        int[] guessValues = new int[NumberOfPegs];
        
        NumberOfColors = Math.min(NumberOfColors, ColorPalette.length);
        
        
        
        
        for (i=0; i< NumberOfPegs; i++)
        {
            solutionValues[i] = randGen.nextInt(NumberOfColors);
            guessValues[i] = randGen.nextInt(NumberOfColors);
        }
        
        
        
        
        PegRow solution = new PegRow(solutionValues);
        PegRow test = new PegRow(guessValues);
        System.out.print("Solution: ");
        solution.PrintRow();
        System.out.print("Guess 0: ");
        test.PrintRow();
        
        RowTest(solution,test);
    }

}
