/* 
 @Authors: Mahlaki Henry and Jahlil Owens
 @ Date: 2/23/24
@ Title: LinkedQueue
 This is our original work
*/
public class LinkedQueue<E> {
	private class Node<E> {
		public E data; // data
		public Node<E> next; // reference to next node

		public Node(E _data, Node<E> _next) {
			this.data = _data;
			this.next = _next;
		}
	}

	private Node<E> front_ptr; // The first element to go out
	private Node<E> back_ptr; // The last element to go out - first element to go in
	private int numOfElems;

	public LinkedQueue() {
		this.numOfElems = 0;
		this.back_ptr = null;
		this.front_ptr = null;
	}

	public void enqueue(E element) {
		// O(1)
		if (this.back_ptr == null) {
			Node<E> box = new Node<E>(element, null);
			this.back_ptr = box;
			this.front_ptr = box;
			this.numOfElems++;
			return; // Similar to else case;
		}
		Node<E> box = new Node<E>(element, null);
		this.back_ptr.next = box;
		this.back_ptr = back_ptr.next;
		this.numOfElems++;

	}

	public E dequeue() {
		//O(1)
		
		if(this.numOfElems == 0) {
			return this.back_ptr.data; // throw new DSQueueException
		}
		E temp = this.front_ptr.data;
		this.front_ptr = this.front_ptr.next;
		this.numOfElems--;
		
		if(this.numOfElems == 0) { // If it is the last element in the list after decrementing the number of elements^. 
			this.back_ptr = this.front_ptr;
		}
		
		return temp;
	}
		
		
		public boolean isEmpty() {
			return this.numOfElems == 0;
		}
	
		
		public E peek() {
			return this.front_ptr.data;
		}
		
		
		public String toString() {
			String s = "";
			Node <E> traverse_ptr = this.front_ptr;
			while(traverse_ptr != null) {
				s += traverse_ptr.data + " ";
				traverse_ptr = traverse_ptr.next;
			}
			return s;
		}
	}//change


