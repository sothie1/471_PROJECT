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
    
    public static int NumberOfPegs = 4;
    public static int NumberOfColors = 4;
    public static int MaxGuesses = 10;
    public static Random R = new Random();
   
  
    
    public static void main(String[] args) {
        
        int i; // basic iterator, which probably gets reused
        String[] ColorPalette = {"Red", "Blue", "Green", "White",
            "Yellow", "Magenta", "Cyan", "Black", "Orange"};
        int[] solutionValues = new int[NumberOfPegs];
        int[] guessValues = new int[NumberOfPegs];
        for (i=0; i< NumberOfPegs; i++)
        {
            solutionValues[i] = R.nextInt(NumberOfColors);
            guessValues[i] = R.nextInt(NumberOfColors);
        }
        Pattern solution = new Pattern(solutionValues);
        Pattern initial = new Pattern(guessValues);
        System.out.print("Solution: ");
        solution.Print(ColorPalette);
        System.out.print("Initial: ");
        initial.Print(ColorPalette);
        //Mutate(test2, 1.0f);
        //Genetic.Solve(initial, solution);
    }

}
