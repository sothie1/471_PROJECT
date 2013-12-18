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
    private long time;

    public GameResult(Pegs solution, Pegs result, boolean solved, int turns, long time)
    {
        this.solution = solution;
        this.result = result;
        this.solved = solved;
        this.turns = turns;
        this.time = time;
    }
    // we probably won't even need to check time
    public GameResult(Pegs solution, Pegs result, boolean solved, int turns)
    {
        this(solution, result, solved, turns, (long)0.00);
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
    public long Time()
    {
        return this.time;
    }
    public int Turns()
    {
        return this.turns;
    }
    @Override public String toString()
    {
        return "{Result: " + this.result.toString()+
                ", solved: "+this.solved+" turns: "+this.turns;
//                + ", time: "+this.time+"}";
    }
    public String toString(String[] palette)
    {
        return "{Result: " + this.result.toString(palette)+
                ", solved: "+this.solved+", turns: "+this.turns;
//                + ", time: "+this.time+"}";
    }
    
}
