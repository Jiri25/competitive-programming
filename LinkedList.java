public class LinkedList<T>{
	private Node<T> head;
	private Node<T> tail;
	private int size;
	
	public LinkedList() {
		this.head = null;
		this.tail = null;
	}
	
	public void add(T value) {
		Node<T> newNode = new Node<>(value);
		if(isEmpty()) {
			head = tail = newNode;
		}else {
			tail.nextNode = newNode;
			newNode.prevNode = tail;
			tail = newNode;
		}
		size++;
	}
	
	public void addFirst(T value) {
		Node<T> newNode = new Node<>(value);
		if(isEmpty()) {
			head = tail = newNode;
		}else {
			newNode.nextNode = head;
			head.prevNode = newNode;
			head = newNode;
		}
		size++;
	}
	
	public void addLast(T value) {
		add(value);
	}
	
	/**
	 * Inserts a value at the specified index.
	 * Valid indices: 0 to size - 1.
	 * Throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public void insert(int index, T value) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds: " + index + " for length " + size );
		}else if(index == 0) {
			addFirst(value);
		}else {
			Node<T> target = getNode(index);
			Node<T> newNode = new Node<>(value);
			newNode.nextNode = target;
			newNode.prevNode = target.prevNode;
			target.prevNode.nextNode = newNode;
			target.prevNode = newNode;
			size++;
		}
	}
	
	public T removeFirst() {
		T ret;
		if(isEmpty()) {
			throw new IllegalArgumentException("Cannot remove, the list is empty!");
		}else if(size == 1) {
			ret = head.value;
			head = tail = null;
		}else {
			ret = head.value;
			Node<T> newHead = head.nextNode;
			head.nextNode = null; //enable for garbage collection
			newHead.prevNode = null;
			head = newHead;
		}
		size--;
		return ret;
	}
	
	public T removeLast() {
		T ret;
		if(isEmpty()) {
			throw new IllegalArgumentException("Cannot remove, the list is empty!");
		}else if (size == 1) {
			ret = tail.value;
			tail = head = null;
		}else {
			ret = tail.value;
			Node<T> newTail = tail.prevNode;
			tail.prevNode = null; //make eligible to get garbage collected
			newTail.nextNode = null;
			tail = newTail;
		}
		size--;
		return ret;
	}
	
	public T remove(int index) {
		T ret;
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds: " + index + " for length " + size );
		}else if (index == 0) {
			return removeFirst();
		}else if (index == size - 1) {
			return removeLast();
		}else {
			Node<T> target = getNode(index);
			ret = target.value;
			target.prevNode.nextNode = target.nextNode;
			target.nextNode.prevNode = target.prevNode;
			target.prevNode = null; //unchain the addresses for garbage collection
			target.nextNode = null; //unchain the addresses
		}
		size--;
		return ret;
	}
	
	public T get(int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds: " + index + " for length " + size );
		}else {
			Node<T> target = getNode(index);
			return target.value;
		}
	}
	
	public void clear() {
		Node<T> current = head;
		while(current != null) {
			Node<T> next = current.nextNode;
			current.nextNode = current.prevNode = null;
			current = next;
		}
	/*The pointers (head and tail) will still hold references to the original head and
	*  tail nodes, even if their nextNode and prevNode fields are null*/
		head = tail = null; //which is why we need this step.
		size = 0;
	}
	
	public boolean contains(T value) {
		Node<T> current = head;
		while(current != null) {
			if(current.value.equals(value)) {
				return true;
			}
			current = current.nextNode;
		}
		return false;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	private Node<T> getNode(int index){
		boolean isCloserToHead = index <= (size - 1) / 2;
		Node<T> current = isCloserToHead? head : tail;
		for (int i = isCloserToHead? 0 : size - 1; i != index; i += isCloserToHead? 1 : -1) {
			current = isCloserToHead? current.nextNode : current.prevNode;
		}
		return current;
	}
	
	public String forwardStructure() {
		if(isEmpty()) {
			return "[]";
		}else if (size == 1) {
			return "head = tail = (" + head.value + ')';
		}else {
			StringBuilder ret = new StringBuilder();
			ret.append("[Head: (").append(head.value).append(")]");
			Node<T> current = head.nextNode;
			while(current != null) {
				//check for tail
				if(current.nextNode == null) {
					ret.append(" <--> [Tail: (").append(tail.value).append(")]");
				}else {
					ret.append(" <--> (").append(current.value).append(')');
				}
				current = current.nextNode;
			}
			return ret.toString();
		}
	}
	
	public String backwardStructure() {
		if(isEmpty()) {
			return "[]";
		}else if (size == 1) {
			return "tail = head = (" + head.value + ')';
		}else {
			StringBuilder ret = new StringBuilder();
			ret.append("[Tail: (").append(tail.value).append(")]");
			Node<T> current = tail.prevNode;
			while(current != null) {
				//check for tail
				if(current.prevNode == null) {
					ret.append(" <--> [Head: (").append(head.value).append(")]");
				}else {
					ret.append(" <--> (").append(current.value).append(')');
				}
				current = current.prevNode;
			}
			return ret.toString();
		}
	}
	
	public static void main(String[] args) {
		LinkedList<Integer> list = new LinkedList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		System.out.println(list.forwardStructure());
		System.out.println(list.size());
		System.out.println(list.backwardStructure());
		list.insert(list.size() - 1, 6);
		list.insert(0, 7);
		System.out.println(list.forwardStructure());
		list.insert(3, 88);
		System.out.println(list.forwardStructure());
		System.out.println(list.size());
		list.remove(3);
		System.out.println(list.forwardStructure());
		System.out.println(list.size());
		list.add(9);
		System.out.println(list.forwardStructure());
		list.addFirst(33);
		System.out.println(list.forwardStructure());
		System.out.println(list.size());
		System.out.println("Does 7 exist? "+ list.contains(7));
		list.clear();
		System.out.println(list.forwardStructure());
		System.out.println(list.size());
	}
	
	private static class Node<T>{
		T value;
		Node<T> nextNode;
		Node<T> prevNode;
		
		public Node(T value) {
			this.value = value;
		}
	}
}