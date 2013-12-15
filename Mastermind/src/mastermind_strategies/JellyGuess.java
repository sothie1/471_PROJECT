/*
 * Copyright (C) 2013, The Council of Elrond
 */

package mastermind_strategies;
import mastermind.Pattern;
import java.util.ArrayList;
import static mastermind.Mastermind.NumberOfPegs;
import static mastermind.Mastermind.MaxGuesses;
import static mastermind.Mastermind.R;
import static mastermind.Mastermind.ColorPalette;
/**
 * Random search algorithm.
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class JellyGuess {
    private static int JellyGuesses;

    public static Pattern Solve(Pattern solution)
    {
        JellyGuesses = 0;
        System.out.println("Random jelly solve: "+solution.toString(ColorPalette));
        boolean solved = false;
        Pattern guess = new Pattern();
        Pattern old_guess;
        ArrayList<int[]> unexplored = mastermind.Mastermind.SearchSpace();
        ArrayList<int[]> visited = new ArrayList<int[]>();
        int phase = 0;
        int nextindex;
        
        while(JellyGuesses<MaxGuesses && !solved && unexplored.size()>0) {
            unexplored.remove(guess.GetArray());
            visited.add(guess.GetArray());
            guess.Evaluate(solution);
            System.out.print("Jelly guess "+JellyGuesses+": "+guess.toString(ColorPalette));
            //System.out.print(" vs. solution: "+solution.toString(ColorPalette));
            System.out.print(";\t"+ guess.CountMatch()+" match, "+guess.CountMiss()+" miss.");
            System.out.println();
            JellyGuesses++;
            if (phase==0 && guess.CountMatch()+guess.CountMiss()==NumberOfPegs)
            {
                phase = 1;
                for (int i=unexplored.size()-1; i>=0; i--){
                    if (!guess.EquivalentV(unexplored.get(i)))
                    {   unexplored.remove(i);   }
                }
                //System.out.println("Reduced space to "+unexplored.size());
            }
            if(guess.Equal(solution))
            {
                solved = true;
                System.out.println("Solved!");
            }
            else
            {
                old_guess = guess;
                /* THIS IS WHERE THE HEURISTIC GOES */
                nextindex = R.nextInt(unexplored.size());
                guess.SetPrevious(old_guess);
                old_guess.SetNext(guess);
                guess = new Pattern(unexplored.get(nextindex));
            }
            
        }
        System.out.println("Solved? "+solved);
        return guess;
    }
    
}
