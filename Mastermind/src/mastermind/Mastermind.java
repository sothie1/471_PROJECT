/*
 * Copyright (C) 2013, The Council of Elrond
 */

// Jamison Testing push part 2

package mastermind;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import mastermind_strategies.*;

/**
 * 
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class Mastermind  {
    
    public static int NumberOfPegs = 4;
    public static int NumberOfColors = 5;
    public static int MaxGuesses = 8;
    public static Random R = new Random();

    public static ArrayList<int[]> SearchSpace()
    {
        ArrayList<int[]> list = new ArrayList<int[]>();
        int[] startArray = new int[NumberOfPegs];
        RecurseSS(list, startArray, 0);
        Collections.shuffle(list,R);
        return list;
    }
    
    //
    private static void RecurseSS(ArrayList<int[]> list, int[] array, int depth)
    {
        int i;
        for (i=0; i<NumberOfColors; i++) {
            array[depth] = i;
            if (depth == NumberOfPegs-1) list.add(array.clone());
            else RecurseSS(list, array, depth+1);
        }
    }
    
    public static String[] ColorPalette = {"Red", "Blue", "Green", "White",
            "Yellow", "Magenta", "Cyan", "Black", "Orange"};
    
    public static void main(String[] args) {
        
        int i; // basic iterator, which probably gets reused
        
        
        Pegs solution = new Pegs();

        System.out.println("Solution: "+solution.toString());
        //GameResult game1 = JellyGuess.Solve(solution);
        //System.out.println("Jelly solve: "+game1.toString(ColorPalette));
        //GameResult game2 = SimpleGuess.Solve(solution);
        //System.out.println("Reducer solve: "+game2.toString(ColorPalette));
        //GameResult game3 = FiveGuess1.Solve(solution);
        //System.out.println("5-guess solve: "+game3.toString(ColorPalette));
    }

}
