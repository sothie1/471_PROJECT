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
 * Lexicographic search algorithm, with basic evaluation.
 * Reduces search space by eliminating permutations of previous guesses:
 * 1. If guess X is not a permutation of the solution, then all guesses Y that
 * are permutations of X cannot be the solution either.
 * 2. If guess Z is a permutation of the solution, then all guesses Y that are
 * not permutations of Z cannot be the solution. (This happens at most once.)
 * The next guess is the next "in order" from unexplored guess space.
 * 
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class LexicoPlus {
    private static int GuessCount;

    public static GameResult Solve(Pegs solution, Pegs initialguess)
    {
        return LexicoPlus.Solve(solution, null, 0);
    }
    
    public static GameResult Solve(Pegs solution, Pegs initialguess, int verbose)
    {
        int NGuesses = 0;
        int PegsLength = solution.GetArray().length;
        boolean solved = false;
        ArrayList<int[]> unexplored = mastermind.Mastermind.SearchSpace();
        Pegs guess = new Pegs(unexplored.get(0));
        ArrayList<int[]> visited = new ArrayList<>();
        int do_once = 0;
        Reply reply = new Reply();
        
        while(NGuesses<MaxGuesses && !solved && unexplored.size()>0) {
            NGuesses++;
            unexplored.remove(guess.GetArray());
            visited.add(guess.GetArray());
            reply = reply.Evaluate(guess, solution);
            if (verbose!=0){
                System.out.print("Smarter lexicographic guess "+NGuesses+": "+guess.toString());
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
            guess = new Pegs(guess);
            guess.SetArray(unexplored.get(0));
        }
        GuessCount = NGuesses;
        return new GameResult(solution, guess, solved, NGuesses);
    }
    
}
