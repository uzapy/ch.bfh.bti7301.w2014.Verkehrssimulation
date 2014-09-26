package model;

public class SkipList<T> {
	private SkipListNode<T> head;
    private int maxLevel;
    private int size;
    
    public SkipList()
    {
    	this.size = 0;
    	this.maxLevel = 0;
    	this.head = new SkipListNode<T>(null); //Anfang der Liste ist ein null-Objekt
    	
    	//!! HIER DAS TAIL ELEMENT EINFÜGEN SOBALD DIE ADD METHODE IMPLEMENTIERT IST!!
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
