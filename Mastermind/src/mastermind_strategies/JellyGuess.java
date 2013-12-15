/*
 * Copyright (C) 2013, The Council of Elrond
 */

package mastermind_strategies;
import mastermind.Pattern;
import java.util.ArrayList;
import static mastermind.Mastermind.NumberOfPegs;
import static mastermind.Mastermind.MaxGuesses;
/**
 * Random search algorithm.
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class JellyGuess {
    private static int JellyGuesses;
    
    public static Pattern Solve(Pattern solution)
    {
        JellyGuesses = 0;
        System.out.println("Random jelly solve: "+solution.toString());
        boolean solved = false;
        Pattern guess = new Pattern();
        Pattern old_guess;
        ArrayList<Pattern> visited = new ArrayList<>();
        int phase = 0;
        /* once correct SET of values is found, there is 
           no need to introduce new values; just shuffle current values. */
        while(JellyGuesses<MaxGuesses && !solved) {
            if (guess.HasVisited(visited)) {
                if (phase==1) guess.Shuffle();
                else guess = new Pattern();
            }
            else
            {
                JellyGuesses++;
                guess.Evaluate(solution);
                System.out.print("Jelly guess "+JellyGuesses+": "+guess.toString());
                System.out.print(" vs. solution: "+solution.toString());
                System.out.print("; "+ guess.CountMatch()+" match, "+guess.CountMiss()+" miss.");
                System.out.println();
                visited.add(guess);
                if (phase==0 && guess.CountMatch()+guess.CountMiss()==NumberOfPegs)
                {
                    //System.out.println("Jelly guess has the right pieces!");
                    phase = 1;
                }
                if(guess.Equals(solution))
                {
                    solved = true;
                    System.out.println("Solved!");
                }
                else
                {
                    old_guess = guess;
                    if (phase==1)
                    { 
                        guess = new Pattern(old_guess);
                        guess.Shuffle(); // Same values, random order
                    }
                    else
                    {
                        guess = new Pattern(); // Random values and order
                        guess.SetPrevious(old_guess);
                    }
                }
            }
        }
        System.out.println("Solved? "+solved);
        return guess;
    }
    
}
