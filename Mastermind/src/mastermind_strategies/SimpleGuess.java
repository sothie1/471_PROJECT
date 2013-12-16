/*
 * Copyright (C) 2013, The Council of Elrond
 */

package mastermind_strategies;
import mastermind.Pegs;
import java.util.ArrayList;
import mastermind.GameResult;
import static mastermind.Mastermind.*;
/**
 * Random search algorithm. Reduces search space by eliminating
 * permutations of previous guesses, because they must be wrong.
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class SimpleGuess {
    private static int ReducerGuesses;

    public static GameResult Solve(Pegs solution)
    {
        int NGuesses = 0;
        int PegsLength = solution.GetArray().length;
        boolean solved = false;
        Pegs guess = new Pegs();
        Pegs old_guess = null;
        ArrayList<int[]> unexplored = mastermind.Mastermind.SearchSpace();
        ArrayList<int[]> visited = new ArrayList<>();
        int phase = 0;
        int nextindex;
        
        while(NGuesses<MaxGuesses && !solved && unexplored.size()>0) {
            nextindex = R.nextInt(unexplored.size());
            guess = new Pegs(unexplored.get(nextindex));
            unexplored.remove(guess.GetArray());
            visited.add(guess.GetArray());
            guess.Evaluate(solution);
            System.out.print("Reduction guess "+NGuesses+": "+guess.toString());
            System.out.print(";\t"+ guess.CountMatch()+" match, "+guess.CountMiss()+" miss.");
            System.out.println();
            if (phase==0 && guess.CountMatch()+guess.CountMiss()==PegsLength)
            {
                phase = 1;
                for (int i=unexplored.size()-1; i>=0; i--){
                    if (!guess.EquivalentV(unexplored.get(i)))
                    {unexplored.remove(i);   }
                }
            }
            if(guess.CountMatch()==PegsLength)
            {
                solved = true;
                //System.out.println("Solved!");
            }
            else
            {   
                if (phase==0) {
                    for (int i=unexplored.size()-1; i>=0; i--){
                        if (guess.EquivalentV(unexplored.get(i)))
                        {   unexplored.remove(i);   }
                }}
                /* THIS IS WHERE THE HEURISTIC GOES */
                if (old_guess!=null)
                { guess.SetPrevious(old_guess);
                old_guess.SetNext(guess); }
                old_guess = guess;
            }
            NGuesses++;
        }
        ReducerGuesses = NGuesses;
        //System.out.println("Solved? "+solved);
        return new GameResult(solution, guess, solved, NGuesses);
    }
    
}
