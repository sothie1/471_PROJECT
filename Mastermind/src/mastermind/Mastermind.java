/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastermind;
import java.util.Random;
import java.util.ArrayList;
import mastermind_strategies.Genetic;

/**
 *
 * @author MDavis
 */
public class Mastermind {
    
    public static int NumberOfPegs = 5;
    public static int NumberOfColors = 4;
    public static Random R = new Random();
   
    public static void PrintRow(Pegs p)
    {
        String[] ColorPalette = {"Red", "Blue", "Green", "White",
            "Yellow", "Magenta", "Cyan", "Black", "Orange"};
        System.out.print("[");
        for (int i=0; i<NumberOfPegs; i++)
        {
            System.out.print(ColorPalette[p.Get(i)]+" ");
        }
        System.out.println("]");
    }
        
    /**
     * @param solution
     * @param guess
     */
    public static void RowTest(Pegs solution, Pegs guess)
    {
        boolean hit;
        int i, j;
        int score_match = 0;
        int score_miss = 0;
        int [] solution_values = solution.ValuesCopy();
        int [] guess_values = guess.ValuesCopy();
        // Reset:

        // Check for full matches
        for (i = 0; i<NumberOfPegs; i++)
        {
            if (solution_values[i]==guess_values[i])
            {
                score_match++;
                guess_values[i] = solution_values[i] = -2;
            }     
          }
        for (i = 0; i<NumberOfPegs; i++)
        {
            hit = false;
            if (guess_values[i] <0) continue;
           for (j=0; j< NumberOfPegs; j++)
            {
                if (!hit && solution_values[j] ==guess_values[j])
                {
                    guess_values[i] = solution_values[j] = -1;
                    hit = true;
                }
            }
            if (hit==true) score_miss++;
        }

        System.out.print("Guess "+guess.Depth()+": ");
        for(i=0; i<score_match; i++)
            System.out.print("(=) ");
        for(i=0; i<score_miss; i++)
            System.out.print("(/) ");
        for(i=0; i<(NumberOfPegs-score_match-score_miss); i++)
            System.out.print("( ) ");
        System.out.println();
        // The program will evaluate this somehow
    }
    
    public static boolean HasVisited(Pegs test, ArrayList<Pegs> list)
    {
        boolean check = false;
        for (Pegs p : list) {
            if (test.Equals(p))
            {
                check = true;
                break;
            }}
        return check;
    }
    
    
    
    public static void main(String[] args) {
        
        int i; // basic iterator, which probably gets reused
        
        int[] solutionValues = new int[NumberOfPegs];
        int[] guessValues = new int[NumberOfPegs];
        for (i=0; i< NumberOfPegs; i++)
        {
            solutionValues[i] = R.nextInt(NumberOfColors);
            guessValues[i] = R.nextInt(NumberOfColors);
        }
        Pegs solution = new Pegs(solutionValues);
        Pegs initial = new Pegs(guessValues);
        System.out.print("Solution: ");
        PrintRow(solution);
        System.out.print("Initial: ");
        PrintRow(initial);
        //Mutate(test2, 1.0f);
        Genetic.Solve(initial, solution);

        
       RowTest(solution,initial);
    }

}
