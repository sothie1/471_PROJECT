/*
 * Copyright (C) 2013, The Council of Elrond
 */

// Jamison Testing push part 2

package mastermind;
import java.util.Random;
import java.util.ArrayList;
import mastermind_strategies.JellyGuess;

/**
 * 
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class Mastermind {
    
    public static int NumberOfPegs = 4;
    public static int NumberOfColors = 4;
    public static int MaxGuesses = 6;
    public static Random R = new Random();

    public static void main(String[] args) {
        
        int i; // basic iterator, which probably gets reused
        String[] ColorPalette = {"Red", "Blue", "Green", "White",
            "Yellow", "Magenta", "Cyan", "Black", "Orange"};
        int[] solutionValues = {1, 2, 3, 0};
        Pattern solution = new Pattern(solutionValues);
        
        Pattern cluelessSolution = JellyGuess.Solve(solution);
        //System.out.println(cluelessSolution.toString(ColorPalette));
    }

}
