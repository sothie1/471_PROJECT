/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mastermind_strategies;
import java.util.ArrayList;
import mastermind.Pegs;
import static mastermind.Mastermind.NumberOfPegs;
import static mastermind.Mastermind.NumberOfColors;
/**
 * Genetic algorithm
 * @author MDavis
 */
public class Genetic {
    private static int ComboWeights[][];
    private static int MaximumSteps = 5;
    private static int PopulationSize = 3;
    private static ArrayList<Pegs> visited;
    private static Pegs best[];
    
    int Evaluate(Pegs solution, Pegs guess)
    {
        boolean hit;
        int i, j;
        int score_match = 0;
        int score_miss = 0;
        int [] solution_values = solution.ValuesCopy();
        int [] guess_values = guess.ValuesCopy();
        // Reset:

        // Check for full matches
        for (i = 0; i<NumberOfPegs; i++)
        {
            if (solution_values[i]==guess_values[i])
            {
                score_match++;
                guess_values[i] = solution_values[i] = -2;
            }     
          }
        for (i = 0; i<NumberOfPegs; i++)
        {
            hit = false;
            if (guess_values[i] <0) continue;
           for (j=0; j< NumberOfPegs; j++)
            {
                if (!hit && solution_values[j] ==guess_values[j])
                {
                    guess_values[i] = solution_values[j] = -1;
                    hit = true;
                }
            }
            if (hit==true) score_miss++;
        }

        System.out.print("Guess "+guess.Depth()+": ");
        for(i=0; i<score_match; i++)
            System.out.print("(=) ");
        for(i=0; i<score_miss; i++)
            System.out.print("(/) ");
        for(i=0; i<(NumberOfPegs-score_match-score_miss); i++)
            System.out.print("( ) ");
        System.out.println();
        return 2*score_match + score_miss;
        // The program will evaluate this somehow
    }
    
    public static void Solve(Pegs initial, Pegs solution)
    {
        visited = new ArrayList<>();
        int i, j; // iterators
        int topScore = 0;
        int newScore;
        ComboWeights = new int [NumberOfPegs][NumberOfColors];
        best = new Pegs [PopulationSize];
        for (i=0; i<NumberOfPegs; i++)
            for (int j=0; j<NumberOfColors; j++)
                ComboWeights[i][j]=0;
        visited.add(initial);
        for (i=0; i<MaximumSteps; i++){
            Pegs nextnode = new Pegs(parent);
            if (new >= topScore)
            {
                for (j=0;j<NumberOfPegs;j++)
                {
                    ComboWeights[j][nextnode.Get(j)] += 1; 
                }
            }
        }
    }

}
