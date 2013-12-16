/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */

package mastermind;

/**
 *
 * @author MDavis
 */
public class Reply {
    private Pegs pegs1;
    private Pegs pegs2;
    
    private int matches;
    private int misses;
    
    public int Match() { return this.matches; }
    
    public int Miss() { return this.misses; }
    
    public int Both() {return this.matches+this.misses;}
    
    public boolean Equal() {return (this.matches==pegs1.GetArray().length);}
    
    public boolean Equivalent()
    {return (this.matches+this.misses==pegs1.GetArray().length);}
    
    public Reply()
    {;
    }
    
    public Reply(Pegs p1, Pegs p2)
    {
        this.Evaluate(p1, p2);
    }
    
    public Reply Evaluate (Pegs p1, Pegs p2)
    {
        this.pegs1 = p1; this.pegs2 = p2;
        int i, j;
        this.matches = 0; this.misses =0;
        int [] p1values = p1.CopyArray();
        int [] p2values = p2.CopyArray();
        for (i = 0; i<p2values.length; i++) {
            if (p1values[i]==p2values[i])
            { // Check for full matches
                this.matches++;
                p1values[i] = -1;
                p2values[i] = -2;    
        }}
        for (i = 0; i<p1values.length; i++) {
           for (j=0; j< p2values.length; j++) {
                if (p1values[i] == p2values[j])
                { // Miss = correct color, wrong place.
                    p1values[i] = -1;
                    p2values[j] = -2;
                    this.misses++;
                    break;
           }}}
        return this;
    }
}
