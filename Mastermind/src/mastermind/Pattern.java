/*
 * Copyright (C) 2013, The Council of Elrond
 */
package mastermind;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class for row / node / set of pegs / sequence object
 * Note: indices and color values are zero-based.
 * 
 * CONSTRUCTORS:
 * Pattern()
 *   -> Create a new pattern object of depth 0 with random values
 * Pattern(int[] pegvalues)
 *   -> Create a new pattern obj of depth 0 with specific values
 * Pattern(Pattern parent)
 *   -> Create a new pattern that has values of Pattern parent. This Pattern's
 *      'previous' property points to the parent, and the parent's 'next'
 *      property points to this Pattern.
 * 
 * PSEUDO-CONSTRUCTOR:
 * Pattern Clone()
 *   -> Create a new object that is an exact copy of this one. Unlike the
 *      parent-based constructor, the cloned node has the same parent, child,
 *      and depth, and this and the clone do not point to one another.
 * 
 * GET/SET METHODS:
 * int Get(int index)
 * void Set(int index, int newvalue)
 *   -> The value of the sequence, at position 'index'.
 * int[] GetValues()
 * void SetValues(int[] newvalues)
 *   -> The array itself. Should not use in most circumstances.
 * int[] CopyValues()
 *   -> A new array that is a copy of the array of this Pattern's values. This
 *      way, modifying the new array does not alter this Pattern.
 * int Depth()
 *   -> Depth / current turn number.
 * Pattern Next()
 * void SetNext(Pattern nextpattern)
 *   -> For graphs, this is the child node. Or, it's the next guess. Either way,
 *      this is useful for getting the history of a game.
 * Pattern Previous()
 * void SetPrevious(Pattern previouspattern)
 *   -> For graphs, this is the parent node. Or, it's the previous guess. Either
 *      way, this is useful for getting the history of a game.
 * int CountMatch()
 *   -> The number of pegs that have the correct color and position. Important:
 *      the Evaluate() function MUST be called before this.
 * int CountMiss()
 *   -> The number of pegs that have the correct color but wrong position.
 *      Important: the Evaluate() function MUST be called before this.
 * 
 * COMPARING:
 * void Evaluate(Pattern solution)
 *   -> Crucial to evaluating this node. It compares the sequence against the
 *      solution sequence. Then, it sets the number of correct and missed
 *      values in this Pattern.
 * boolean Equals(Pattern other)
 *   -> Whether two Patterns have the same sequence of values.
 * boolean Equals(int[] othervalues)
 *   -> Whether this sequence of values matches a given sequence of values.
 * boolean HasVisited(ArrayList<Pattern> visitedlist)
 *   -> Whether this Pattern's values match the values of a visited Pattern.
 * boolean HasVisited(int[][] visitedvalueslist)
 *   -> Same as above, but compare to int array rather than a Pattern object.
 * 
 * OTHER:
 * Pattern Shuffle()
 *   -> Rearranges the values, without adding or removing values from the
 *      sequence. Returns this Pattern (not a copy).
 */

/**
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class Pattern {
    private int[] values;
    private int depth = 0; // entropy should be a function of depth?
    private Pattern previous = null;
    private Pattern next = null;
    private int countMatch = 0;
    private int countMiss = 0;
  
    int NumberOfPegs = Mastermind.NumberOfPegs;
    Random R = Mastermind.R;

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
        this.depth = parent.Depth() +1;
        this.previous = parent;
        parent.SetNext(this);
        this.values = parent.CopyValues();
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
    public void SetPrevious(Pattern prevpeg) {
        this.previous = prevpeg;
        this.depth = prevpeg.Depth()+1;
    }
    
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
        int i, j;
        int score_match = 0;
        int score_miss = 0;
        int [] solution_values = solution.CopyValues();
        int [] guess_values = this.CopyValues();
        for (i = 0; i<NumberOfPegs; i++) {
            if (solution_values[i]==guess_values[i])
            { // Check for full matches
                score_match++;
                guess_values[i] = -1;
                solution_values[i] = -2;    
        }}
        this.countMatch = score_match;
        for (i = 0; i<NumberOfPegs; i++) {
           for (j=0; j< NumberOfPegs; j++) {
                if (solution_values[i] == guess_values[j])
                { // Miss = correct color, wrong place.
                    guess_values[j] = -1;
                    solution_values[i] = -2;
                    score_miss++;
                    break;
        }}}
        this.countMiss = score_miss;
        //System.out.println("(Match: "+score_match+", miss: "+score_miss+")");
        // # match and # miss are set
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
                return true; }
        return false;
    }

    public boolean HasVisited(int[][] listvalues)
    {
        for (int i=0; i<listvalues.length; i++) {
            if (this.values==listvalues[i])
                    return true;
        }
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
     * @return formatted string
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
     * @return string of int array
     */
    @Override public String toString()
    {
        String out = "[";
        for (int i=0; i<NumberOfPegs-1; i++)
        { out += this.values[i]+", "; }
        out += this.values[NumberOfPegs-1]+"]";
        return out;
    }  
    
    /**
     * Rearrange the order, without changing pegs
     * You do this once CountMatch + CountMiss = NumberOfPegs
     * This is called the Fisher–Yates shuffle
     * @return 
     */
    public Pattern Shuffle()
    {
        int index, v;
 //       System.out.print("Shuffling "+this.toString());
        for (int i = NumberOfPegs - 1; i > 1; i--)
        {
          index = R.nextInt(i);
          v = this.values[index];
          this.values[index] = this.values[i];
          this.values[i] = v;
        }
 //       System.out.println(" to "+this.toString());    
        return this;
    }
}