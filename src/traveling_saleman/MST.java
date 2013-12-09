/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package traveling_saleman;

import java.util.ArrayList;

/**
 *
 * @author Beautiful
 */
public class MST {
    
    private ArrayList<Node> mst_graph;
    
    public MST(){
        mst_graph = new ArrayList<Node>();
    }
    
    public void addNode(int node){
        mst_graph.add(new Node(node));
    }
    
    public void addConnection(int node, int connection){
        for (Node n: mst_graph){
            if (n.getRef() == node)
                n.addConnection(connection);
        }
    }
    
    public boolean isConnected(int node, int otherNode){
        for(Node n : mst_graph){
            if (n.getConenctions().contains(otherNode))
                return true;
        }
        return false;
    }
    
    public ArrayList<Integer> getConnection(int node){
        for(Node n : mst_graph){
            if (n.getRef() == node)
                return n.getConenctions();
        }
        return null;
    }
    
    private class Node{
        private ArrayList<Integer> connections;
        private int node_ref;
        
        private Node(int node_ref){
            this.node_ref = node_ref;
            connections = new ArrayList<Integer>();
        }
        private int getRef(){
            return node_ref;
        }
        private void addConnection(int connection){
            connections.add(connection);
        }
        private ArrayList<Integer> getConenctions(){
            return connections;
        }
    }
            
}
