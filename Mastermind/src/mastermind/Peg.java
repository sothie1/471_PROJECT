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
public class Peg {
    private int color;
    String[] ColorPalette = Mastermind.ColorPalette;
    
    public Peg(int color_index)
    {
        this.color = color_index;
    }
    
    public int GetColor()
    {
        return this.color;
    }
    
    public void SetColor(int newvalue)
    {
        this.color = newvalue;
    }
    
    public String Print()
    {
        if (this.color < -1)
        {
            return "++";
        }
        else if (this.color <0)
        {
            return "--";
        }
        else
            return ColorPalette[this.color];
    }

}