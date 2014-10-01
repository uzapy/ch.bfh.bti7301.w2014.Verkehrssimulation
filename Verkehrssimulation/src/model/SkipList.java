package model;



/**
 * @author stahr2
 *
 * @param <T>
 */

public class SkipList<T> {
	private SkipListNode<T> head;
    private int maxLevel;
    private int size;
    
    public SkipList()
    {
    	this.size = 0;
    	this.maxLevel = 0;
    	this.head = new SkipListNode<T>(null); //Anfang der Liste ist ein null-Objekt
    	
    	this.head.nextNodes.add(null); //Ende der Liste ebenfalls ein null-Objekt
    }
    
    public SkipListNode<T> getHeadNode(){
    	return this.head;
    }
    
    public boolean addNode(T newNode){
    	return true;
    }
    
    public boolean removeNode(T Node){
    	return true;
    }
    
    public boolean containsNode(T Node){
    	return true;
    }
    
    public SkipListNode<T> find(T findNode)
    {
    	return new SkipListNode<T>(null);
    }

}
