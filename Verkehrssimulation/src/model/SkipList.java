package model;



/**
 * @author stahr2
 *
 * @param <T>
 * Implementet with help of article http://www.csee.umbc.edu/courses/undergraduate/341/fall01/Lectures/SkipLists/skip_lists/skip_lists.html
 */

public class SkipList<T> {
	private SkipListNode<Integer, T> head;
    private int maxLevel;
    private int size;
    
    public SkipList()
    {
    	this.size = 0;
    	this.maxLevel = 0;
    	this.head = new SkipListNode<Integer, T>(Integer.MIN_VALUE, null); //Anfang der Liste ist ein null-Objekt
    	
    	this.head.nextNodes.add(null); //Ende der Liste ebenfalls ein null-Objekt
    }
    
    public SkipListNode<Integer, T> getHeadNode(){
    	return this.head;
    }
    
    public boolean addNode(SkipListNode<Integer,T> newNode){
    	return true;
    }
    
    public boolean removeNode(Integer Node){
    	return true;
    }
    
    public boolean containsNode(Integer Node){
    	return true;
    }
    
    public SkipListNode<Integer, T> find(Integer findNode)
    {
    	return new SkipListNode<Integer, T>(Integer.MAX_VALUE,null);
    }

}
