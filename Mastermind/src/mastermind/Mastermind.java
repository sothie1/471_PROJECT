/*
 * Copyright (C) 2013, Wayne Enterprises
 */

package mastermind;
import java.util.Random;
import java.util.ArrayList;
import mastermind_strategies.*;

/**
 * Main driver class
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class Mastermind  {
    
    public static int NumberOfPegs = 4;
    public static int NumberOfColors = 6;
    public static int MaxGuesses = 10;
    private static Random R = new Random();

    // Probably won't use all of these
    public static String[] ColorPalette = {"Red", "Blue", "Green", "White",
            "Yellow", "Magenta", "Cyan", "Black",
//            "Gold", "Silver", "Indigo", "Beige", "Alpha",
//            "Neon", "Infrared", "Burnt Sienna", "8fb380",
             "Orange", "Purple", "Pink", "Brown"};
    
    public static void main(String[] args) {
        
        int i; // basic iterator, which probably gets reused
        
        R.nextInt();
        Pegs solution = new Pegs();
        Pegs initialguess = new Pegs();
        
        System.out.println("Solution:\t"+solution.toString()+
                "("+solution.toString(ColorPalette)+")");
        System.out.println("Initial guess:\t"+initialguess.toString()+
                "("+initialguess.toString(ColorPalette)+")");
        
        GameResult game1 = UninformedGuess.Solve(solution,initialguess);
        System.out.println("Uninformed guessing:");
        System.out.println(game1.toString(ColorPalette));
        
        GameResult game11 = SimpleGuess.Solve(solution,initialguess);
        System.out.println("Basic informed guessing:");
        System.out.println(game1.toString(ColorPalette));
        
        GameResult game2 = Lexicographic.Solve(solution,null);
        System.out.println("Lexicographic guessing:");
        System.out.println(game2.toString(ColorPalette));     
        
        GameResult game21 = LexicoPlus.Solve(solution,null);
        System.out.println("Smarter lexicographic guessing:");
        System.out.println(game21.toString(ColorPalette));    
        
        GameResult game3 = ScoreChecking.Solve(solution,initialguess);
        System.out.println("With score-equivalence guessing:");
        System.out.println(game3.toString(ColorPalette));
        
        GameResult game4 = TypeCulling.Solve(solution,initialguess);
        System.out.println("With color-culling guessing:");
        System.out.println(game4.toString(ColorPalette));
        
        GameResult game5 = JamesBond.Solve(solution,initialguess);
        System.out.println("Combined strategy guessing:");
        System.out.println(game5.toString(ColorPalette));
    }

    // Search space is in lexicographic order
    public static ArrayList<int[]> SearchSpace()
    {
        ArrayList<int[]> list = new ArrayList<int[]>();
        int[] startArray = new int[NumberOfPegs];
        RecurseSS(list, startArray, 0);
        //    Collections.shuffle(list,R);
        return list;
    }
    
    // don't call
    private static void RecurseSS(ArrayList<int[]> list, int[] array, int depth)
    {
        int i;
        for (i=0; i<NumberOfColors; i++) {
            array[depth] = i;
            if (depth == NumberOfPegs-1) list.add(array.clone());
            else RecurseSS(list, array, depth+1);
        }
    }
    
    
}
