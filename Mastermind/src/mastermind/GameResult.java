/*
 * Copyright (C) 2013 Council of Elrond
 */

package mastermind;

/**
 *
 * @author Michael Davis, Sothiara Em, Jamison Hyman
 */
public class GameResult {
    private Pattern solution;
    private Pattern result;
    private boolean solved;
    private int turns;
    public GameResult(Pattern solution, Pattern result, boolean solved, int turns)
    {
        this.solution = solution;
        this.result = result;
        this.solved = solved;
        this.turns = turns;
    }
    public Pattern Solution()
    {
        return this.solution;
    }
    public Pattern Result()
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
