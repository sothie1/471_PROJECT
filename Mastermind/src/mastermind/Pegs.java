/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mastermind;
import java.util.List;
/**
 *
 * @author MDavis
 */
public class Pegs {
    private int[] values;
    private int[] weights;
    private int rating;
    private int depth; // entropy should be a function of depth
    private Pegs previous = null;
    private Pegs next = null;
  
    int NumberOfPegs = Mastermind.NumberOfPegs;

    // For a root node
    public Pegs(int[] pegvalues)
    {
        this.values = pegvalues;
        this.weights = new int[NumberOfPegs];
        for (int i=0; i<NumberOfPegs; i++) weights[i] = 0;
        depth = 0;
    }
    
    // Returns a "child"
    public Pegs(Pegs parent)
    {
        this.previous = parent;
        this.values = new int [NumberOfPegs];
        this.depth = parent.Depth() +1;
        parent.SetNext(this);
        for(int i=0; i<NumberOfPegs; i++)
        {
            this.values[i] = parent.Get(i);
            this.weights[i] = parent.Weight(i);
        }
    }
    
    // Returns an absolute clone
    public Pegs Clone()
    {
        Pegs clone = new Pegs(this);
        clone.previous = this.previous;
        clone.depth = this.depth;
        clone.next = this.next;
        return clone;
    }
    
    public boolean Equals(Pegs other)
    {
        boolean diff = true;
        for(int i=0; i<NumberOfPegs; i++) {
            if (this.values[i]!=other.Get(i)) {
                diff = false; }}
        return diff;
    }
    
    public boolean HasVisited(List<Pegs> list)
    {
        boolean check = false;
        for (Pegs p : list) {
            if (this.Equals(p))
            {
                check = true;
                break;
            }}
        return check;
    }
    
    public int Get(int i) { return this.values[i]; }
    
    public void Set(int i, int newvalue) { this.values[i] = newvalue; }
    
    public Pegs Previous() { return this.previous; }
    public void SetPrevious(Pegs prevpeg) { this.previous = prevpeg; }
    
    public Pegs Next() { return this.next; }
    public void SetNext(Pegs nextpeg) { this.next = nextpeg; }
    
    public int[] Values() { return this.values; }
    
    public int[] ValuesCopy()
    {
        int[] clone = new int[NumberOfPegs];
        System.arraycopy(this.values, 0, clone, 0, NumberOfPegs);
        return clone;
    }
    
    public int Weight(int i) { return this.weights[i]; }
    
    public int Score()
    {
        int i=0;
        for (int j=0; j<NumberOfPegs; j++)
            i+= weights[j];
        return i;
    }
    
    public int Depth() { return this.depth; }
   
    
}