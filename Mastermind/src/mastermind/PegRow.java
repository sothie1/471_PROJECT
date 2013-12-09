/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mastermind;
/**
 *
 * @author MDavis
 */
public class PegRow {
    protected Peg[] pegs;
    protected int[] real_values;
    
    int NumberOfPegs = Mastermind.NumberOfPegs;
    String[] ColorPalette = Mastermind.ColorPalette;
    
    public PegRow(int[] pegvalues)
    {
        pegs = new Peg[pegvalues.length];
        real_values = pegvalues;
        for (int i=0; i<pegvalues.length; i++)
        {
            pegs[i] = new Peg(pegvalues[i]);
        }
    }

    public void Reset()
    {
        for (int i = 0; i<NumberOfPegs; i++)
        {
            pegs[i].SetColor(real_values[i]);
        }
    }
    
    
    public Peg Get(int index)
    {
        return pegs[index];
    }
    
    public int GetValue(int index)
    {
        return pegs[index].GetColor();
    }
    
    public void Set(int index, int newvalue)
    {
        pegs[index].SetColor(newvalue);
    }
    
    public Peg[] GetPegs()
    {
        return pegs;
    }
            
    public void PrintRow()
    {
        for (Peg peg : pegs) {
            System.out.print("\t"+peg.Print());
        }
        System.out.print("\n");
    }
}