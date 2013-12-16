/*
 * Copyright (C) 2013, The Council of Elrond
 */

package mastermind_strategies;
import mastermind.Pegs;
import java.util.ArrayList;
import mastermind.GameResult;
import static mastermind.Mastermind.*;
/**
 * Random search algorithm.
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class JellyGuess {
    public static int JellyGuesses;

    public static GameResult Solve(Pegs solution)
    {
        int NGuesses = 0;
        boolean solved = false;
        Pegs guess = new Pegs();
        Pegs old_guess = null;
        ArrayList<int[]> unexplored = mastermind.Mastermind.SearchSpace();
        ArrayList<int[]> visited = new ArrayList<>();
        int nextindex;
        
        while(NGuesses<MaxGuesses && !solved && unexplored.size()>0) {
            nextindex = R.nextInt(unexplored.size());
            guess = new Pegs(unexplored.get(nextindex));            
            unexplored.remove(guess.GetArray());
            visited.add(guess.GetArray());
            guess.Evaluate(solution);
            if(guess.CountMatch()==guess.GetArray().length) solved = true;
            else
            {
                /* THIS IS WHERE THE HEURISTIC GOES */
                if (old_guess!=null)
                { guess.SetPrevious(old_guess);
                old_guess.SetNext(guess); }
                old_guess = guess;
            }
            NGuesses++;
            
        }
        //System.out.println("Solved? "+solved);
        JellyGuesses = NGuesses;
        return new GameResult(solution, guess, solved, NGuesses);
    }
    
}
