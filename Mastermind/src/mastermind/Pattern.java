/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mastermind;

import java.util.ArrayList;
import static mastermind.Mastermind.NumberOfPegs;

/**
 *
 * @author MDavis
 */
public class Pattern {
    private int[] values;
    private int depth = 0; // entropy should be a function of depth?
    private Pattern previous = null;
    private Pattern next = null;
    private int countMatch = 0;
    private int countMiss = 0;
  
    int NumberOfPegs = Mastermind.NumberOfPegs;

    // For a root node, manually specify a code
    public Pattern(int[] pegvalues)
    {
        this.values = pegvalues;
    }
    
    // Randomly generate root node
    public Pattern()
    {
        this.values = new int [NumberOfPegs];
        for (int i=0; i<NumberOfPegs; i++)
        {
            this.values[i] = Mastermind.R.nextInt(Mastermind.NumberOfColors);
        }
    }
    
    // Returns a "child"
    public Pattern(Pattern parent)
    {
        this.previous = parent;
        this.values = new int [NumberOfPegs];
        this.depth = parent.Depth() +1;
        parent.SetNext(this);
        for(int i=0; i<NumberOfPegs; i++)
        {
            this.values[i] = parent.Get(i);
        }
    }
    
    // Returns an absolute clone
    public Pattern Clone()
    {
        Pattern clone = new Pattern(this);
        clone.previous = this.previous;
        clone.depth = this.depth;
        clone.next = this.next;
        return clone;
    }
    
    public boolean Equals(Pattern other)
    {
        boolean diff = true;
        for(int i=0; i<NumberOfPegs; i++) {
            if (this.values[i]!=other.Get(i)) {
                diff = false; }}
        return diff;
    }
    
    public int Get(int i) { return this.values[i]; }
    
    public void Set(int i, int newvalue) { this.values[i] = newvalue; }
    
    public Pattern Previous() { return this.previous; }
    public void SetPrevious(Pattern prevpeg) { this.previous = prevpeg; }
    
    public Pattern Next() { return this.next; }
    public void SetNext(Pattern nextpeg) { this.next = nextpeg; }
    
    public int CountMatch() {return this.countMatch; }
    public void SetCountMatch(int newvalue) {this.countMatch = newvalue;}
    
    public int CountMiss() {return this.countMiss; }
    public void SetCountMiss(int newvalue) {this.countMiss = newvalue;}
    
    public int[] GetValues() { return this.values; }
    public void SetValues(int[] newvalues) {
        for (int i=0; i<NumberOfPegs; i++)
        {
            this.values[i] = newvalues[i];
        }
    }
    
    public void Evaluate(Pattern solution)
    {
        boolean hit;
        int i, j;
        int score_match = 0;
        int score_miss = 0;
        int [] solution_values = solution.CopyValues();
        int [] guess_values = this.CopyValues();
        // Check for full matches
        for (i = 0; i<NumberOfPegs; i++)
        {
            if (solution_values[i]==guess_values[i])
            {
                score_match++;
                guess_values[i] = solution_values[i] = -2;
            }     
        }
        this.SetCountMatch(score_match);
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
        this.SetCountMiss(score_miss);
        System.out.print("Guess "+this.Depth()+": ");
        for(i=0; i<score_match; i++)
            System.out.print("(Match) ");
        for(i=0; i<score_miss; i++)
            System.out.print("(Miss) ");
        for(i=0; i<(NumberOfPegs-score_match-score_miss); i++)
            System.out.print("(-) ");
        System.out.println();
        // The program will evaluate this somehow
    }
    
    public boolean HasVisited(ArrayList<Pattern> list)
    {
        boolean check = false;
        for (Pattern p : list) {
            if (this.Equals(p))
            {
                check = true;
                break;
            }}
        return check;
    }

    public int[] CopyValues()
    {
        int[] clone = new int[NumberOfPegs];
        System.arraycopy(this.values, 0, clone, 0, NumberOfPegs);
        return clone;
    }
            
    public int Depth() { return this.depth; }
    
    public void Print(String[] palette)
    {
        System.out.print("[");
        for (int i=0; i<NumberOfPegs-1; i++)
        { System.out.print(palette[this.values[i]]+", "); }
        System.out.println(palette[this.values[NumberOfPegs-1]]+"]");
    }
   
    public void Print()
    {
        System.out.print("[");
        for (int i=0; i<NumberOfPegs-1; i++)
        { System.out.print(this.values[i]+", "); }
        System.out.println(this.values[NumberOfPegs-1]+"]");
    }
}