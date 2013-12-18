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
 * Random search algorithm. Uses no heuristic and is very stupid.
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class UninformedGuess {
    public static int GuessCount;
    
    public static GameResult Solve(Pegs solution, Pegs initialguess)
    {
        return UninformedGuess.Solve(solution,initialguess,0);
    }
    
    public static GameResult Solve(Pegs solution, Pegs initialguess, int verbose)
    {
        Random R = new Random();
        int NGuesses = 0;
        boolean solved = false;
        Pegs guess = new Pegs(initialguess);
        ArrayList<int[]> unexplored = mastermind.Mastermind.SearchSpace();
        ArrayList<int[]> visited = new ArrayList<>();
        int nextindex;
        Reply reply = new Reply();
        
        while(NGuesses<MaxGuesses && !solved && unexplored.size()>0) {
            NGuesses++;
            unexplored.remove(guess.GetArray());
            visited.add(guess.GetArray());
            reply = reply.Evaluate(guess, solution);
            if (verbose!=0){
                System.out.print("Uninformed guess "+NGuesses+": "+guess.toString());
                System.out.print(";\t"+ reply.Match()+" match, "+reply.Miss()+" miss.");
                System.out.println();
            }
            if(guess.CountMatch()==guess.GetArray().length) solved = true;
            nextindex = R.nextInt(unexplored.size());
            guess = new Pegs(guess);    
            guess.SetArray(unexplored.get(nextindex));
        }
        GuessCount = NGuesses;
        return new GameResult(solution, guess, solved, NGuesses);
    }
    
}
