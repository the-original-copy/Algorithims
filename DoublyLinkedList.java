public class DoublyLinkedList <T> implements Iterable<T> {
	private int size = 0;
	private Node <T> head = null;
	private Node <T> tail = null;
	
	//Internal node to represent the data container ie. the node
	private class Node<T>{
		T data;
		Node <T> prev,next;
		//Constructor that takes datae,next_Node pointer and previous_Node pointer
		public Node(T data,Node <T> prev,Node <T> next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
		@Override
		public String toString() {
			return data.toString();
		}
	}
	
	public void clear() {
		Node <T> trav = head;
		while(trav != null) {
			Node <T> next = trav.next;
			trav.prev = trav.next = null;
			trav.data = null;
			trav = next;
		}
		head = tail = trav = null;
		size = 0;
	}
	
	public int size() {
		return size();
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public void add(T elem) {
		addLast(elem);
	}
	
	public void addLast(T elem) {
		if (isEmpty()) {
			head = tail = new Node<T>(elem,null,null);
		}
		else {
			tail.next = new Node<T>(elem,tail,null);
			tail = tail.next;
		}
		size++;
	}
	
	public void addFirst(T elem) {
		if (isEmpty()) {
			head = tail = new Node<T>(elem,null,null);
		}
		else {
			head.prev = new Node<T>(elem,null,head);
			head = head.prev;
		}
		size++;
	}
	
	public T peekFirst() {
		if (isEmpty()) throw new RuntimeException("Empty list");
		return head.data;
	}
	
	public T peekLast() {
		if (isEmpty()) throw new RuntimeException("Empty list");
		return tail.data;
	}
	
	public T removeFirst() {
		if (isEmpty()) throw new RuntimeException("Empty list");
		T data = head.data;
		head = head.next;
		size--;
		
		//Set tail as null if the list is empty
		if(isEmpty()) tail = null;
		
		else head.prev = null;
		
		return data;
	}
	
	public T removeLast() {
		if (isEmpty()) throw new RuntimeException("Empty list");
		
		T data = tail.data;
		tail = tail.prev;
		--size;
		
		if(isEmpty()) head = null;
		
		else tail.next = null;
		
		return data;
	}
	
	public T remove(Node <T> node) {
		//Handle if the selected node is the first or the last one
		if(node.prev == null) return removeFirst();
		if(node.next == null) return removeLast();
		
		//Change the pointers
		node.next.prev = node.prev;
		node.prev.next = node.next;
		
		//Temporarily store the data to be removed
		T data = node.data;
		
		//Memory cleanup
		node.data = null;
		node = node.next = node.prev = null;
		--size;
		
		return data;
	}
	
	public T removeAt(int index) {
		//Make sure the index provided is valid
		//Index are always size -1 , there is no index == size
		if(index < 0 || index >= size ) {
			throw new IllegalArgumentException();
		}
		
		int i;
		Node<T> trav;
		
		//Search from the front
		if(index < size /2) {
			for(i = 0, trav = head;i != index;i++) {
				trav = trav.next;
			}
		}else
			for(i = size -1,trav = tail; i != index;i--) {
				trav = trav.prev;
			}
		return remove(trav);
	}
	
	//Remove the value and the node 
	public boolean remove(Object obj) {
		Node<T> trav = head;
		
		//Remove node whose value is null,doesn't have a value stored
		if(obj == null) {
			//Until the end of the list
			//trav = trav.next functions like i++, increment after the loop ends
			for(trav = head; trav != null; trav = trav.next) {
				if(trav.data == null) {
					remove(trav);
					return true;
				}
			}
		}
		else {
			for(trav = head; trav != null; trav = trav.next) {
				if(obj.equals(trav.data)) {
					remove(trav);
					return true;
				}
			}
		}
		return false;
	}
	
	public int indexOf(Object obj) {
		int index = 0;
		Node<T> trav = head;
		
		//Support serching for nodes whose data is null
		if(obj == null) {
			for(trav = head;trav != null;trav = trav.next,index++) {
				if(trav.data == null) return index;
			}
		}else {
			for(trav = head;trav != null;trav = trav.next,index++) {
				if(obj.equals(trav.data)) return index;
			}
		}
		return -1;
	}
	
	public boolean contains(Object obj) {
		return indexOf(obj) != -1;
	}
	
	@Override
	public java.util.Iterator<T> iterator(){
		return new java.util.Iterator<T>() {
			private Node<T> trav = head;
			
			@Override
			public boolean hasNext() {
				return trav != null;
			}
			@Override
			public T next() {
				T data = trav.data;
				trav = trav.next;
				return data;
			}
		};
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		Node<T> trav = head;
		while(trav != null) {
			sb.append(trav.data + ", ");
			trav = trav.next;
		}
		sb.append("]");
		return sb.toString();
	}
	

}
