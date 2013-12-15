/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mastermind;

import java.util.ArrayList;

/**
 *
 * @author Noxus Vileus, Dark Lord of Evil and King of the Doomlands
 */
public class Pattern {
    private int[] values;
    private int depth = 0; // entropy should be a function of depth?
    private Pattern previous = null;
    private Pattern next = null;
    private int countMatch = 0;
    private int countMiss = 0;
  
    int NumberOfPegs = Mastermind.NumberOfPegs;

    /**
     * Constructor A: Pass in an array of integer values
     * Can be used for root node
     * @param pegvalues 
     */
    public Pattern(int[] pegvalues)
    {
        this.values = pegvalues;
    }
    
    /**
     * Constructor B:
     * Pass in no arguments: it randomly sets the integer values
     * Used for root node
     */
    public Pattern()
    {
        this.values = new int [NumberOfPegs];
        for (int i=0; i<NumberOfPegs; i++)
        {
            this.values[i] = Mastermind.R.nextInt(Mastermind.NumberOfColors);
        }
    }
    
    /**
     * Constructor C: Make a child node of the arg parent Pattern
     * Not used as the root node
     * @param parent 
     */
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
    /**
     * Duplicates a Pattern node object, with all of the same values
     * @return 
     */
    public Pattern Clone()
    {
        Pattern clone = new Pattern(this);
        clone.previous = this.previous;
        clone.depth = this.depth;
        clone.next = this.next;
        clone.countMatch = this.countMatch;
        clone.countMiss = this.countMiss;
        return clone;
    }
    
    /**
     * Used to test whether or not a Pattern node has been visited
     * @param other
     * @return 
     */
    public boolean Equals(Pattern other)
    {
        boolean equal = true;
        for(int i=0; i<NumberOfPegs; i++) {
            if (this.values[i]!=other.Get(i)) {
                equal = false;
                break;
            }}
        return equal;
    }
    
    /**
     * Polymorphism is cool.
     * @param othervalues
     * @return 
     */
    public boolean Equals(int[] othervalues)
    {
        boolean equal = true;
        for(int i=0; i<NumberOfPegs; i++) {
            if (this.values[i]!=othervalues[i]) {
                equal = false;
                break;
            }}
        return equal;
    }
    
    /**
     * Individual integer values
     * @param index
     * @return 
     */
    public int Get(int index) { return this.values[index]; }
    public void Set(int index, int newvalue) { this.values[index] = newvalue; }
    
    /**
     * Parent node
     * @return 
     */
    public Pattern Previous() { return this.previous; }
    public void SetPrevious(Pattern prevpeg) { this.previous = prevpeg; }
    
    /**
     * Child node
     * @return 
     */
    public Pattern Next() { return this.next; }
    public void SetNext(Pattern nextpeg) { this.next = nextpeg; }
    
    /**
     * The number of correct position + color pegs
     * @return 
     */
    public int CountMatch() {return this.countMatch; }
    //public void SetCountMatch(int newvalue) {this.countMatch = newvalue;}
    
    /**
     * The number of correct color / incorrect position pegs
     * @return 
     */
    public int CountMiss() {return this.countMiss; }
   // public void SetCountMiss(int newvalue) {this.countMiss = newvalue;}
    
    /**
     * Refers to individual pegs
     * @return 
     */
    public int[] GetValues() { return this.values; }
    public void SetValues(int[] newvalues) {
        for (int i=0; i<NumberOfPegs; i++)
        {
            this.values[i] = newvalues[i];
        }
    }
    
    /**
     * Pass in the solution Pattern
     * Sets the number of matching pegs
     * Sets the number of missed pegs
     * @param solution 
     */
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
        this.countMatch = score_match;
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
        this.countMiss = score_miss;
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
    
    /**
     * Keep track of an ArrayList of visited nodes
     * @param list
     * @return 
     */
    public boolean HasVisited(ArrayList<Pattern> list)
    {
        Pattern p;
        for (int i=0; i<list.size(); i++) { 
            p = list.get(i);
            if (this.Equals(p)==true)
            {
                return true;
            }}
        return false;
    }

    /**
     * Make a new int[] array with the same values as this Pattern
     * @return 
     */
    public int[] CopyValues()
    {
        int[] clone = new int[NumberOfPegs];
        System.arraycopy(this.values, 0, clone, 0, NumberOfPegs);
        return clone;
    }
            
    public int Depth() { return this.depth; }
    
    /**
     * Print with a palette format -> map integers to strings
     * @param palette 
     */
    public String toString(String[] palette)
    {
        String out = "[";
        for (int i=0; i<NumberOfPegs-1; i++)
        { out += palette[this.values[i]]+", "; }
        out += palette[this.values[NumberOfPegs-1]]+"]";
        return out;
    }
   
    /**
     * Print with no args -> just display int values
     */
    public String toString()
    {
        String out = "[";
        for (int i=0; i<NumberOfPegs-1; i++)
        { out += this.values[i]+", "; }
        out += this.values[NumberOfPegs-1]+"]";
        return out;
    }
}