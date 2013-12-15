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
public class Mastermind  {
    
    public static int NumberOfPegs = 4;
    public static int NumberOfColors = 4;
    public static int MaxGuesses = 10;
    public static Random R = new Random();

    public static ArrayList<int[]> SearchSpace()
    {
        ArrayList<int[]> list = new ArrayList<int[]>();
        int[] startArray = new int[NumberOfPegs];
        Fun(list, startArray, 0);
        return list;
    }
    
    private static void Fun(ArrayList<int[]> list, int[] array, int depth)
    {
        int i;
        for (i=0; i<NumberOfColors; i++) {
            array[depth] = i;
            if (depth == NumberOfPegs-1) list.add(array.clone());
            else Fun(list, array, depth+1);
        }
    }
    
    public static String[] ColorPalette = {"Red", "Blue", "Green", "White",
            "Yellow", "Magenta", "Cyan", "Black", "Orange"};
    
    public static void main(String[] args) {
        
        int i; // basic iterator, which probably gets reused
        
        
        Pattern solution = new Pattern(new int[]{0,1,2,3});

        //ArrayList<int[]> ValueSpace =SearchSpace();
        //Pattern solution = new Pattern(solutionValues);
        Pattern cluelessSolution = JellyGuess.Solve(solution);
        System.out.println(cluelessSolution.toString(ColorPalette));
    }

}
