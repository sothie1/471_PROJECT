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
 * Advanced random search algorithm, with color(type)-based culling.
 * Checks for special cases where a guess has no misses or matches. In
 * this case, it iterates through all of the colored pegs in the guess,
 * then eliminates all guesses from unexplored space. It is a very expensive
 * operation but is very effective when the number of colors is large.
 * 
 * 1. If guess X is not a permutation of the solution, then all guesses Y that
 * are permutations of X cannot be the solution either.
 * 2. If guess Z is a permutation of the solution, then all guesses Y that are
 * not permutations of Z cannot be the solution. (This happens at most once.)
 * 3. If guess X has pegs A,B,C but the solution does not have A, B, or C,
 * then all guesses Y containing A, B, or C cannot be the solution. (Culling)
 * The next guess is randomly chosen from the remaining guess space.

 * 
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class TypeCulling {
    private static int GuessCount;
    


    public static GameResult Solve(Pegs solution, Pegs initialguess)
    {
        return TypeCulling.Solve(solution, null, 0);
    }
    
    public static GameResult Solve(Pegs solution, Pegs initialguess, int verbose)
    {
        int i, j, nextindex, NGuesses = 0;
        Random R = new Random();
        int PegsLength = solution.GetArray().length;
        boolean solved = false;
        Pegs guess = new Pegs(initialguess);
        ArrayList<int[]> unexplored = mastermind.Mastermind.SearchSpace();
        ArrayList<int[]> visited = new ArrayList<>();
        int do_once = 0;
        Reply reply = new Reply();
        
        while(NGuesses<MaxGuesses && !solved && unexplored.size()>0) {
            NGuesses++;
            unexplored.remove(guess.GetArray());
            visited.add(guess.GetArray());
            reply = reply.Evaluate(guess, solution);
            if (verbose!=0){
                System.out.print("Color-culling guess "+NGuesses+": "+guess.toString());
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
        {
            if (array[i]==value) return true;
        }
        return false;
    }
    
}
