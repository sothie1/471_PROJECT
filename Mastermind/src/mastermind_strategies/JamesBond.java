/*
 * Copyright (C) 2013, The Council of Elrond
 */

package mastermind_strategies;
import mastermind.Pegs;
import java.util.ArrayList;
import java.util.Random;
import mastermind.GameResult;
import mastermind.Reply;
import static mastermind.Mastermind.*;
/**
 * Advanced random search algorithm.
 * Because DIFFERENCE(guess, solution) = DIFFERENCE(solution, guess)
 * the solution is in the set of X where
 * DIFFERENCE(guess, solution) = DIFFERENCE(guess, X)
 * Each guess must be better than the previous one.
 * 
 * Also includes culling feature in case no colors were found
 * in a guess; it will eliminate any guess with any of those
 * colors from guess space.
 * 
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class JamesBond {
    private static int GuessCount;

    public static GameResult Solve(Pegs solution, int[] initialguess){
        return JamesBond.Solve(solution, initialguess, 0);
    }
    
    public static GameResult Solve(Pegs solution, int[] initialguess, int verbose)
    {
        int i, j, nextindex, NGuesses = 0;
        Random R = new Random();
        int PegsLength = solution.GetArray().length;
        boolean solved = false;
        Pegs guess = new Pegs();
        Pegs dummyguess = new Pegs(initialguess);
        ArrayList<int[]> unexplored = mastermind.Mastermind.SearchSpace();
        ArrayList<int[]> visited = new ArrayList<>();
        int do_once = 0;
        Reply reply = new Reply();
        Reply dummyreply = new Reply();
        
        while(NGuesses<MaxGuesses && !solved && unexplored.size()>0) {
            NGuesses++;
            unexplored.remove(guess.GetArray());
            visited.add(guess.GetArray());
            reply = reply.Evaluate(guess, solution);
            if (verbose!=0){
                System.out.print("James Bond guess "+NGuesses+": "+guess.toString());
                System.out.print(";\t"+ reply.Match()+" match, "+reply.Miss()+" miss.");
                System.out.println();
            }
            if (reply.Match()==PegsLength) { solved = true; break ;}
            else if (do_once==0 && reply.Both()==PegsLength)
            {
                do_once = 1;
                for (i=unexplored.size()-1; i>=0; i--){
                    if (!guess.EquivalentV(unexplored.get(i)))
                    {unexplored.remove(i);   }
            }}
            else if (reply.Both()!=PegsLength)
            {
                for (i=unexplored.size()-1; i>=0; i--){
                    if (guess.EquivalentV(unexplored.get(i)))
                    {   unexplored.remove(i);   }}
                
                // Added special culling case:
                if (reply.Both()==0){
                    for (i=0; i<PegsLength; i++) {
                        for (j=unexplored.size()-1; j>=0; j--) {
                            if (AContains(unexplored.get(j),guess.Get(i))) {
                                unexplored.remove(j); }}}}
            }
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
            nextindex = R.nextInt(unexplored.size());
            guess = new Pegs(guess);
            guess.SetArray(unexplored.get(nextindex));
        }
        GuessCount = NGuesses;
        //System.out.println("Solved? "+solved);
        return new GameResult(solution, guess, solved, NGuesses);
    }
    
        // if array contains a value
    private static boolean AContains(int[] array, int value)
    {
        for (int i=0; i<array.length; i++)
        { if (array[i]==value) return true; }
        return false;
    }
    
}
