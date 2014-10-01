package model;

import java.util.*;

/**
 * @author stahr2
 *
 * @param <T>
 */

public class SkipListNode<T> {
    private T element;
    public List<SkipListNode<T>> nextNodes;
	
    public T getValue() {
	return element;
    }

    public SkipListNode(T value) {
	this.element = value;
	nextNodes = new ArrayList<SkipListNode<T>>();
    }

    public int level() {
	return nextNodes.size()-1;
    }
}
