/*
 * Copyright (C) 2013, The Council of Elrond
 */

package mastermind_strategies;
import mastermind.Pegs;
import java.util.ArrayList;
import mastermind.GameResult;
import mastermind.Reply;
import static mastermind.Mastermind.*;
/**
 * Advanced random search algorithm.
 * Because DIFFERENCE(guess, solution) = DIFFERENCE(solution, guess)
 * the solution is in the set of X where
 * DIFFERENCE(guess, solution) = DIFFERENCE(guess, X)
 * Each guess must be better than the previous one.
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class MayoGuess {
    private static int GuessCount;

    public static GameResult Solve(Pegs solution)
    {
        int i, nextindex, NGuesses = 0;
        int PegsLength = solution.GetArray().length;
        boolean solved = false;
        Pegs guess = new Pegs();
        Pegs dummyguess = new Pegs();
        Pegs old_guess = null;
        ArrayList<int[]> unexplored = mastermind.Mastermind.SearchSpace();
        ArrayList<int[]> visited = new ArrayList<>();
        int do_once = 0;
        Reply reply = new Reply();
        Reply dummyreply = new Reply();
        
        while(NGuesses<MaxGuesses && !solved && unexplored.size()>0) {
            NGuesses++;
            nextindex = R.nextInt(unexplored.size());
            guess = new Pegs(unexplored.get(nextindex));
            unexplored.remove(guess.GetArray());
            visited.add(guess.GetArray());
            reply = reply.Evaluate(guess, solution);
           /* System.out.print("MayoGuess guess "+NGuesses+": "+guess.toString());
            System.out.print(";\t"+ reply.Match()+" match, "+reply.Miss()+" miss.");
            System.out.println(); */
            if (reply.Match()==PegsLength) { solved = true; break ;}
            else if (do_once==0 && reply.Both()==PegsLength)
            {
                do_once = 1;
                for (i=unexplored.size()-1; i>=0; i--){
                    if (!guess.EquivalentV(unexplored.get(i)))
                    {unexplored.remove(i);   }
            }}
            else if (do_once==0)
            {
                for (i=unexplored.size()-1; i>=0; i--){
                    if (guess.EquivalentV(unexplored.get(i)))
                    {   unexplored.remove(i);   }
            }}
            // Compare this guess to unexplored guesses, see which ones
            // could logically be the answer
            for (i=unexplored.size()-1; i>=0; i--)
            {
                dummyguess.SetArray(unexplored.get(i));
                dummyreply = dummyreply.Evaluate(guess, dummyguess);
                if (dummyreply.Match()!=reply.Match() ||
                        dummyreply.Miss()!=reply.Miss())
                {
                    unexplored.remove(i);
                }
            }
            if (old_guess!=null)
                { guess.SetPrevious(old_guess);
                old_guess.SetNext(guess); }
                old_guess = guess;
            
        }
        GuessCount = NGuesses;
        //System.out.println("Solved? "+solved);
        return new GameResult(solution, guess, solved, NGuesses);
    }
    
}
