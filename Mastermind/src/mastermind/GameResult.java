/*
 * Copyright (C) 2013 Council of Elrond
 */

package mastermind;

/**
 *
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class GameResult {
    private Pegs solution;
    private Pegs result;
    private boolean solved;
    private int turns;
    public GameResult(Pegs solution, Pegs result, boolean solved, int turns)
    {
        this.solution = solution;
        this.result = result;
        this.solved = solved;
        this.turns = turns;
    }
    public Pegs Solution()
    {
        return this.solution;
    }
    public Pegs Result()
    {
        return this.result;
    }
    public boolean IsSolved()
    {
        return this.solved;
    }
    public int Turns()
    {
        return this.turns;
    }
    @Override public String toString()
    {
        return "{Result: " + this.result.toString()+
                ", solved: "+this.solved+" turns: "+this.turns+"}";
    }
    public String toString(String[] palette)
    {
        return "{Result: " + this.result.toString(palette)+
                ", solved: "+this.solved+", turns: "+this.turns+"}";
    }
    
}
