package skiplist;

/**
 * 
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

//import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/**
 * @author ps
 *
 */
public class MySkipList<K extends Comparable<? super K>, E> implements
		OrderedDictionary<K, E> {
	
	// the class for the nodes:
	class SLNode implements Locator<K,E> {
		SLNode prev,next,above,below; // neighbours
		Object owner = MySkipList.this;
		K key;
		E elem;

		/* (non-Javadoc)
		 * @see examples.Position#element()
		 */
		@Override
		public E element() {
			return elem;
		}

		/* (non-Javadoc)
		 * @see examples.Locator#key()
		 */
		@Override
		public K key() {
			return key;
		}
	}

	
	// instance variables
	// 4 inital nodes which remain alway the same
	private SLNode topLeft,bottomLeft,topRight,bottomRight;
	// min, max
	private K minKey,maxKey;
	// 
	private int size;
	private Random rand = new Random(65435);
	private double p =0.5; // index probability
	private int height = 2;
	
	public MySkipList(K min, K max){
		topLeft = new SLNode();
		topLeft.key = min;
		topRight = new SLNode();
		topRight.key = max; 
		bottomLeft = new SLNode();
		bottomLeft.key = min;
		bottomRight = new SLNode();
		bottomRight.key = max; 
		// connect them
		topLeft.next = topRight;
		topRight.prev = topLeft;
		bottomLeft.next = bottomRight;
		bottomRight.prev = bottomLeft;

		topLeft.below = bottomLeft;
		topRight.below = bottomRight;
		bottomLeft.above = topLeft;
		bottomRight.above = topRight;
		
		minKey = min;
		maxKey = max;
	}
	
	
	

	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#size()
	 */
	@Override
	public int size() {
		return size;
	}


	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#find(java.lang.Comparable)
	 */
	@Override
	public Locator<K, E> find(K key) {
		if (key.compareTo(minKey)<=0) throw new RuntimeException("key not bigger than minKey!");
		if (key.compareTo(maxKey)>=0) throw new RuntimeException("key not smaller than maxKey!");
		SLNode pos = search(key);
		if (pos.key.compareTo(key)!=0) return null; // we found nothing
		else {
			// we take the leftmost Locator with valid key
			while (pos.prev.key.compareTo(key)== 0) pos=pos.prev;
			return pos;
		}
	}

	private SLNode search(K key){
		SLNode pos = topLeft;
		while (pos.below!= null){
			pos = pos.below;
			// go to the right until ...
			while (key.compareTo(pos.next.key) >= 0)
					pos = pos.next;
		}
		return pos; 
	}
	
	
	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#findAll(java.lang.Comparable)
	 */
	@Override
	public Locator<K, E>[] findAll(K key) {
		SLNode n = ((SLNode) closestBefore(key)).next;
		if (n==null) return new Locator[0];
		ArrayList<Locator<K,E>> al = new ArrayList<>();
		while(n.key.compareTo(key)==0){
			al.add(n);
			n=n.next;
		}
		return al.toArray(new Locator[0]);
	}


	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#insert(java.lang.Comparable, java.lang.Object)
	 */
	@Override
	public Locator<K, E> insert(K key, E o) {
		if (key.compareTo(minKey)<=0) throw new RuntimeException("key not bigger than minKey!");
		if (key.compareTo(maxKey)>=0) throw new RuntimeException("key not smaller than maxKey!");
		SLNode pos = search(key);
		// we take the rightmost Locator with valid key
		while (pos.next.key.compareTo(key)== 0) pos=pos.next;		
		// now we want to insert a node at the position pos.next:
		SLNode nNew = new SLNode();
		nNew.key = key;
		nNew.elem = o;
		nNew.prev = pos;
		nNew.next = pos.next;
		pos.next.prev = nNew;
		pos.next = nNew;
		SLNode pb = nNew;
		boolean generateIndexNode = rand.nextDouble()<p;
		while (generateIndexNode){
			// System.out.println("index generated");
			while (pos.above==null) pos = pos.prev;
			pos = pos.above;
			// create a new index
			SLNode index = new SLNode();
			index.key = key;			
			index.prev = pos;
			index.next = pos.next;
			pos.next.prev = index;
			pos.next = index;
			index.below=pb;
			pb.above = index;
			pb=index;
			
			// if pos is topLeft we have to expand by one index level
			if (pos == topLeft) expand();
			generateIndexNode = rand.nextDouble() < p;
		}
		size++;
		return nNew;
	}

	private void expand(){
		// adds one index level
		// System.out.println("expanding..");
		SLNode n1 = new SLNode();
		n1.key = minKey;
		SLNode n2 = new SLNode();
		n2.key = maxKey;
		n1.next = n2;
		n2.prev = n1;
		
		n1.below = topLeft;
		n2.below = topRight;
		
		topLeft.above = n1;
		topRight.above = n2;

		topLeft = n1;
		topRight = n2;
		height++;
	}

	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#remove(examples.Locator)
	 */
	@Override
	public void remove(Locator<K, E> loc) {
		SLNode n = (SLNode) loc;
		if (n.owner != this) throw new RuntimeException("invalid locator "+loc.key());
		n.owner=null;
		int lev=0;
		while (n!=null){
			n.prev.next=n.next;
			n.next.prev=n.prev;
			n=n.above;
			lev++;
		}
		if (lev==height-1) shrink();
		size--;
	}


	/**
	 * 
	 */
	private void shrink() {
		// System.out.println("shrink called");
		while (height>2 && topLeft.below.next==topRight.below){
			// System.out.println("shrinking...");
			topLeft = topLeft.below;
			topRight = topRight.below;
			topLeft.above = null;
			topRight.above = null;
			height--;
		}
	}


	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#closestBefore(java.lang.Comparable)
	 */
	@Override
	public Locator<K, E> closestBefore(K key) {
		if (key.compareTo(minKey)<=0) throw new RuntimeException("key not bigger than minKey!");
		if (key.compareTo(maxKey)>=0) throw new RuntimeException("key not smaller than maxKey!");
		SLNode pos = search(key);
		int comp = key.compareTo(pos.key);
		if (comp==0){
			pos = pos.prev;
			// still equal?
			if (pos == bottomLeft) pos = null;
			while (key.compareTo(pos.key)==0) pos=pos.prev;
		}
		else if (comp>0){
			// in case we have sevearal equal keys take the rightmost locator
			while (pos.key.compareTo(pos.next.key)==0) pos=pos.next;
			if (pos == bottomLeft) pos = null;
			
		}
		else 
			throw new RuntimeException("should never happen!");
		return pos;

	}

	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#closestAfter(java.lang.Comparable)
	 */
	@Override
	public Locator<K, E> closestAfter(K key) {
		if (key.compareTo(minKey)<=0) throw new RuntimeException("key not bigger than minKey!");
		if (key.compareTo(maxKey)>=0) throw new RuntimeException("key not smaller than maxKey!");
		SLNode pos = search(key);
		int comp = key.compareTo(pos.key);
		if (comp==0){
			pos = pos.next;
			// still equal?
			while (key.compareTo(pos.key)==0) pos=pos.next;
			if (pos == bottomRight) pos = null;
		}
		else if (comp>0){
			// in case we have sevearal equal keys take the rightmost locator
			while (pos.key.compareTo(pos.next.key)==0) pos=pos.next;
			pos = pos.next;
			if (pos == bottomRight) pos = null;
			
		}
		else throw new RuntimeException("should never happen!");
		return pos;
	}


	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#next(examples.Locator)
	 */
	@Override
	public Locator<K, E> next(Locator<K, E> loc) {
		SLNode n = (SLNode) loc;
		if (n.owner != this) throw new RuntimeException("invalid locator "+loc.key());
		n = n.next;
		if (n==bottomRight) n=null;
		return n;
	}


	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#previous(examples.Locator)
	 */
	@Override
	public Locator<K, E> previous(Locator<K, E> loc) {
		SLNode n = (SLNode) loc;
		if (n.owner != this) throw new RuntimeException("invalid locator "+loc.key());
		n = n.prev;
		if (n==bottomLeft) n=null;
		return n;
	}


	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#min()
	 */
	@Override
	public Locator<K, E> min() {
		if (size>0) return bottomLeft.next; 
		else return null;
	}


	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#max()
	 */
	@Override
	public Locator<K, E> max() {
		if (size>0) return bottomRight.prev; 
		else return null;
	}


	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#sortedLocators()
	 */
	@Override
	public Iterator<Locator<K, E>> sortedLocators() {
		return new Iterator<Locator<K, E>>(){
			SLNode pos = bottomLeft.next;
			@Override
			public boolean hasNext() {
				return pos != bottomRight;
			}

			@Override
				public Locator<K, E> next() {
				SLNode ret = pos;
				pos = pos.next;
				return ret;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException(
						"use remove method of MySkipList!");
			}
			
		};
	}

	public void print(){
		System.out.println("-------start------");
		SLNode n = bottomLeft;
		n=n.next;
		StringBuffer lev = new StringBuffer();
		while (n!=bottomRight){
			lev.delete(0,lev.length());
			SLNode m = n;
			int index = 0;
			while (m.above != null) {
				index++;
				m=m.above;
				lev.append("+");
			}
			while(index<height-2){
				index++;
				lev.append("|");
			}
			System.out.println(String.format("%11d", n.key())+lev.toString()+"    elem: "+n.elem);
			n=n.next;
		}
		System.out.println("--------end-------");
		
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MySkipList<Integer, String> sl = new MySkipList<>(Integer.MIN_VALUE,Integer.MAX_VALUE);
		Random rand = new Random(266743);
		int n  = 1000;
		Locator<Integer,String>[] locs = new Locator[n];
		long time1 = System.currentTimeMillis();
		for (int i=0;i<n;i++) {
			locs[i]=sl.insert(rand.nextInt(n),""+i); 
		}
//		for (int i=0;i<n;i++) {
//			sl.remove(locs[i]); 
//		}
		Locator<Integer,String>[] ll = sl.findAll(33);
		for (int i=0;i<ll.length;i++)System.out.println(ll[i].key());
		long time2 = System.currentTimeMillis();
		System.out.println("elapsed time: "+(time2-time1)/1000.0+" s");
		System.out.println("height of index: "+sl.height);
//		Iterator<Locator<Integer,String>> it = sl.sortedLocators();
//		while (it.hasNext()){
//			Locator<Integer, String> loc = it.next();
//			System.out.println(loc.key()+" element: "+loc.element());
//		}
		sl.print();
//		sl.remove(locs[15]);
//		sl.print();
//		Locator<Integer,String> loc = sl.closestBefore(83);
//		if (loc!= null)System.out.println(loc.key()+":"+loc.element());
	}

}
