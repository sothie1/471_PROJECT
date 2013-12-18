/*
 * Copyright (C) 2013, Wayne Enterprises
 */

package mastermind_strategies;
import mastermind.Pegs;
import java.util.ArrayList;
import java.util.Random;
import mastermind.GameResult;
import static mastermind.Mastermind.*;
import mastermind.Reply;
/**
 * Lexicographic search algorithm. Uninformed search that tries new
 * peg patterns one at a time. Picks in order from the set of possible
 * guesses, ordered by pegs (e.g. 0000,0001,...,3332,3333)
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class Lexicographic {
    public static int GuessCount;
    
    
    public static GameResult Solve(Pegs solution, Pegs initialguess)
    {
        return Lexicographic.Solve(solution, null,0);
    }
    
    public static GameResult Solve(Pegs solution, Pegs initialguess, int verbose)
    {
        int NGuesses = 0;
        boolean solved = false;
        /// initialguess argument is unused
        // Return the search space, in order
        ArrayList<int[]> unexplored = mastermind.Mastermind.SearchSpace();
        ArrayList<int[]> visited = new ArrayList<>();
        Pegs guess = new Pegs(unexplored.get(0));
        Reply reply = new Reply();
        
        int nextindex;
        
        while(NGuesses<MaxGuesses && !solved && unexplored.size()>0) {
            NGuesses++;
            unexplored.remove(guess.GetArray());
            visited.add(guess.GetArray());
            reply = reply.Evaluate(guess, solution);
            if (verbose!=0){
                System.out.print("Lexicographic guess "+NGuesses+": "+guess.toString());
                System.out.print(";\t"+ reply.Match()+" match, "+reply.Miss()+" miss.");
                System.out.println();
            }
            if(guess.CountMatch()==guess.GetArray().length) solved = true;
            nextindex = 0;//R.nextInt(unexplored.size());
            guess = new Pegs(guess);    
            guess.SetArray(unexplored.get(nextindex));
        }
        GuessCount = NGuesses;
        return new GameResult(solution, guess, solved, NGuesses);
    }
    
}
