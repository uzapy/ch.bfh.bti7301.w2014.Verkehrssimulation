package model;

import java.util.*;

/**
 * @author stahr2
 *
 * @param <T>
 * Implementet with help of article http://www.csee.umbc.edu/courses/undergraduate/341/fall01/Lectures/SkipLists/skip_lists/skip_lists.html
 */

public class SkipListNode<Integer, T> {
    private T element;
    private int key;
    public List<SkipListNode<Integer, T>> nextNodes;
	
    public T getValue() {
	return element;
    }

    public SkipListNode(int key, T value) {
	this.element = value;
	this.key = key;
	nextNodes = new ArrayList<SkipListNode<Integer, T>>();
    }

    public int level() {
	return nextNodes.size()-1;
    }
}
