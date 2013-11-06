/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package traveling_saleman;

/**
 *
 * @author Beautiful
 */
public class Coordinate {
    private double x;
    private double y;
    
    /**
     * Constructor that takes in x and y
     * @param double x
     * @param double y 
     */
    public Coordinate(double x , double y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * Get the x Coordinate
     * @return double x
     */
    public double getX(){
        return x;
    }
    
    /**
     * Get the y Coordinate
     * @return double y
     */
    public double getY(){
        return y;
    }
    
    /**
     * Return the absolute value of the cartesian distance between two points
     * @param Coordinate other
     * @return double distance
     */
    public double getDistant(Coordinate other){
        return Math.abs(Math.sqrt(Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2)));
    }
    
    /**
     * To String method to return the coordinates in a printable format
     * @return String print
     */
    public String toString(){
        return x + " " + y;
    }
}
