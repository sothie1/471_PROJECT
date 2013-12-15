/*
 * Copyright (C) 2013 Noxus Vile, Dark Lord of Blood and Master of the Doomlands
 *
 * This program is copyrighted under the Bro Code. It was written
 * for a school project by some cool dudes, who are much, much
 * cooler than any other programmer dude. This program is not to be
 * distributed, sold, donated, eaten, tickled, or defenestrated.
 * The creators of this software are not liable for the actions of
 * the user, nor what the user does with all that junk, all that
 * inside the user's trunk.  Violating the Bro Code voids the
 * warranty on this program, if such warranty would exist, and also
 * nullifies any degree of coolness of the user.
 */

package mastermind_strategies;
import mastermind.Pattern;
import java.util.ArrayList;
import static mastermind.Mastermind.NumberOfColors;
import static mastermind.Mastermind.NumberOfPegs;
import static mastermind.Mastermind.MaxGuesses;
import static mastermind.Mastermind.R;
/**
 *
 * @author Noxus Vileus, Dark Lord of Evil and King of the Doomlands
 */
public class JellySearch {
    private static Pattern SolutionPattern;
    private static Pattern ResponsePattern;
    
    public static Pattern Solve(Pattern solution)
    {
        System.out.println("Clueless solve for: "+solution.toString());
        int i = 0;
        boolean solved = false;
        Pattern guess = new Pattern();
        Pattern old_guess;
        ArrayList<Pattern> visited = new ArrayList<>();

        for (i=0; i<MaxGuesses; i++){
            if (guess.HasVisited(visited))
            {
                i--;
            }
            else {
                System.out.println("Guess "+i+": "+guess.toString());
                visited.add(guess);
                if(guess.Equals(solution))
                {
                    solved = true; break;
                }
            }
            old_guess = guess;
            guess = new Pattern();
            guess.SetPrevious(old_guess);
        }
        System.out.println("Solved: "+solved);
        return guess;
    }
    
}
