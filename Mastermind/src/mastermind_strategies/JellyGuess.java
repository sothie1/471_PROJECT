/*
 * Copyright (C) 2013, The Council of Elrond
 */

package mastermind_strategies;
import mastermind.Pegs;
import java.util.ArrayList;
import java.util.Random;
import mastermind.GameResult;
import static mastermind.Mastermind.*;
/**
 * Random search algorithm. Uses no heuristic and is very stupid.
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class JellyGuess {
    public static int GuessCount;
    
    public static GameResult Solve(Pegs solution, int[] initialguess)
    {
        Random R = new Random();
        int NGuesses = 0;
        boolean solved = false;
        Pegs guess = new Pegs(initialguess);
        ArrayList<int[]> unexplored = mastermind.Mastermind.SearchSpace();
        ArrayList<int[]> visited = new ArrayList<>();
        int nextindex;
        
        while(NGuesses<MaxGuesses && !solved && unexplored.size()>0) {
            NGuesses++;
            unexplored.remove(guess.GetArray());
            visited.add(guess.GetArray());
            guess.Evaluate(solution);
            if(guess.CountMatch()==guess.GetArray().length) solved = true;
            nextindex = R.nextInt(unexplored.size());
            guess = new Pegs(guess);    
            guess.SetArray(unexplored.get(nextindex));
        }
        GuessCount = NGuesses;
        return new GameResult(solution, guess, solved, NGuesses);
    }
    
}
