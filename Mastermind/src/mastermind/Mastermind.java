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
    public static int NumberOfColors = 8;
    public static int MaxGuesses = 12;
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
            "Yellow", "Magenta", "Cyan", "Black", "Orange", "Lime",
            "Brown", "Pink", "Gold", "Silver", "Indigo", "Purple"};
    
    public static void main(String[] args) {
        
        int i; // basic iterator, which probably gets reused
        
        R.nextInt();
        Pegs solution = new Pegs();
        
        System.out.println("Solution:\t"+solution.toString()+"("+solution.toString(ColorPalette)+")");
        //GameResult game1 = JellyGuess.Solve(solution);
        //System.out.println("Jelly solve: "+game1.toString(ColorPalette));
        
        int[] initialguess = new int[]{2,1,7,2};
        
        GameResult game2 = SimpleGuess.Solve(solution,initialguess);
        System.out.println("Simple solve: "+game2.toString(ColorPalette));     
        
        GameResult game3 = MickeyMouse.Solve(solution,initialguess);
        System.out.println("With score-equivalent solve: "+game3.toString(ColorPalette));
        
        GameResult game4 = WalterWhite.Solve(solution,initialguess);
        System.out.println("With no-value checking: "+game4.toString(ColorPalette));
        
        GameResult game5 = JamesBond.Solve(solution,initialguess);
        System.out.println("Combined strategy: "+game5.toString(ColorPalette));
    }

}
