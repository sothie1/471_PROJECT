/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */

package mastermind_strategies;
import mastermind.Pattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import mastermind.GameResult;
import static mastermind.Mastermind.*;

/**
 *
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class FiveGuess1 {
    
/*
    class Node extends Pattern{
        public Node(){ super(); }
        public Node(int[] values){ super(values); }
        public Node(Pattern parent) {super(parent); }
        private int equivalents;
        public int CalcEquivalents(ArrayList<int[]> possible){
            this.equivalents = CountEquivalents(this.GetArray(),possible);
            return this.equivalents;
        }
        public int Equivalents(){ return this.equivalents; }
    }
   */ 
    public static GameResult Solve(Pattern solution)
    {
        int NGuesses = 0;
        int PatternLength = solution.GetArray().length;
        boolean solved = false;
        Pattern guess = null;
        Pattern old_guess = null;
        ArrayList<int[]> unexplored = mastermind.Mastermind.SearchSpace();
        ArrayList<int[]> uniques = new ArrayList<>();
        ArrayList<int[]> visited = new ArrayList<>();
        int phase = 0;
        
        System.out.println(CountEquivalents(new int[]{0,1,2,3},unexplored));
        
        while(NGuesses<MaxGuesses && !solved && unexplored.size()>0) {
            // next best pattern is the one with the most permutations
            guess = NextBestPattern(unexplored);
            unexplored.remove(guess.GetArray());
            visited.add(guess.GetArray());
            guess.Evaluate(solution);
            System.out.print("Five guess strategy "+NGuesses+": "+guess.toString());
            System.out.print(";\t"+ guess.CountMatch()+" match, "+guess.CountMiss()+" miss.");
            System.out.println();
            
            // If guess is equivalent to solution, remove all non-equivalents:
            if (phase==0 && guess.CountMatch()+guess.CountMiss()==PatternLength)
            {
                phase = 1;
                for (int i=unexplored.size()-1; i>=0; i--){
                    if (!guess.EquivalentV(unexplored.get(i)))
                    {unexplored.remove(i);   }
                }
            } 
            if(guess.Equal(solution))
            {
                solved = true;
            }
            else
            {
                // Remove all equivalent guesses, because they are not
                // equivalent to the solution
                if (phase==0) {
                    for (int i=unexplored.size()-1; i>=0; i--)
                        if (EquivalentArrays(guess.GetArray(), unexplored.get(i)))
                            unexplored.remove(i);
                }
                if (old_guess!=null)
                { guess.SetPrevious(old_guess);
                old_guess.SetNext(guess); }
                old_guess = guess;
            }
            NGuesses++;
        }
        return new GameResult(solution, guess, solved, NGuesses);        
    }
    
    /**
     * The next best pattern is the one with the most permutations
     * @param possible
     * @return 
     */
    private static Pattern NextBestPattern(ArrayList<int[]> possible)
    {
        Collections.shuffle(possible, new Random(possible.size()));
        int[] bestvals = possible.get(0);
        int mostequivs = CountEquivalents(bestvals, possible);
        int nextequivs;
        for (int i=0; i<possible.size(); i++){
            nextequivs = CountEquivalents(possible.get(i),possible);
            if (nextequivs > mostequivs)
            {
                bestvals = possible.get(i);
                mostequivs = nextequivs;
            }
        }
        return new Pattern(bestvals);
    }
    
        
    private static boolean EquivalentArrays(int[] arr1, int[] arr2)
    {
        int i, j, found = 0;
        int a1copy[] = arr1.clone();
        int a2copy[] = arr2.clone();
        for (i=0; i<arr1.length; i++) {
            for (j=0; j<arr1.length; j++) {
                if (a1copy[i]==a2copy[j]) {
                    a1copy[i]=-1;
                    a2copy[j]=-2;
                    found++;
                    break;
                } } }
        if (found==arr1.length) return true;
        else return false;
    }
    
   
    /**
     * 
     * Count how many possible permutations a pattern can have.
     */
    private static int CountEquivalents(int[] vals, ArrayList<int[]> possible){
        int i, count = 0;
        for (i=0; i<possible.size(); i++)
        {
            if (EquivalentArrays(vals, possible.get(i))) count++;
        }
        return count;
    }

    /**
     * UNUSED----
     * Reduce the list of unexplored to only unique combinations
     * @param possible
     * @return 
     */
    private static ArrayList<int[]> UniqueSpace(ArrayList<int[]> possible){
        
        ArrayList<int[]> poscopy = new ArrayList<int[]>(possible);
        ArrayList<int[]> uniques = new ArrayList<>();
        int[] nextarray;
        int i;
        while (poscopy.size()>0)
        {
            nextarray = poscopy.get(0);
            uniques.add(nextarray);          
            for (i=poscopy.size()-1; i>=0; i--)
                if (EquivalentArrays(nextarray, poscopy.get(i))==true)
                    poscopy.remove(i);
        }
        return uniques;
    }
    
}
