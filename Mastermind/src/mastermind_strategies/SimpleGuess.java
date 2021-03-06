/*
 * Copyright (C) 2013, Wayne Enterprises
 */

package mastermind_strategies;
import mastermind.Pegs;
import java.util.ArrayList;
import java.util.Random;
import mastermind.GameResult;
import mastermind.Reply;
import static mastermind.Mastermind.*;
/**
 * Random search algorithm, with basic evaluation.
 * Reduces search space by eliminating permutations of previous guesses:
 * 1. If guess X is not a permutation of the solution, then all guesses Y that
 * are permutations of X cannot be the solution either.
 * 2. If guess Z is a permutation of the solution, then all guesses Y that are
 * not permutations of Z cannot be the solution. (This happens at most once.)
 * The next guess is randomly chosen from the remaining guess space.
 * 
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class SimpleGuess {
    private static int GuessCount;

    public static GameResult Solve(Pegs solution, Pegs initialguess)
    {
        return SimpleGuess.Solve(solution, initialguess, 0);
    }
    
    public static GameResult Solve(Pegs solution, Pegs initialguess, int verbose)
    {
        int NGuesses = 0;
        Random R = new Random();
        int PegsLength = solution.GetArray().length;
        boolean solved = false;
        Pegs guess = new Pegs(initialguess);
        ArrayList<int[]> unexplored = mastermind.Mastermind.SearchSpace();
        ArrayList<int[]> visited = new ArrayList<>();
        int do_once = 0;
        int nextindex;
        Reply reply = new Reply();
        
        while(NGuesses<MaxGuesses && !solved && unexplored.size()>0) {
            NGuesses++;
            unexplored.remove(guess.GetArray());
            visited.add(guess.GetArray());
            reply = reply.Evaluate(guess, solution);
            if (verbose!=0){
                System.out.print("Simple guess "+NGuesses+": "+guess.toString());
                System.out.print(";\t"+ reply.Match()+" match, "+reply.Miss()+" miss.");
                System.out.println();
            }
            if (guess.CountMatch()==PegsLength)
            {
                solved = true;
                break;
            }
            else if (do_once==0 && reply.Both()==PegsLength)
            {
                do_once = 1;
                for (int i=unexplored.size()-1; i>=0; i--){
                    if (!guess.EquivalentV(unexplored.get(i)))
                    {unexplored.remove(i);   }
                }
            }
            else if (reply.Both()!=PegsLength) {
                for (int i=unexplored.size()-1; i>=0; i--){
                    if (guess.EquivalentV(unexplored.get(i)))
                    {   unexplored.remove(i);   }
                }
            }
            nextindex = R.nextInt(unexplored.size());
            guess = new Pegs(guess);
            guess.SetArray(unexplored.get(nextindex));
        }
        GuessCount = NGuesses;
        return new GameResult(solution, guess, solved, NGuesses);
    }
    
}
