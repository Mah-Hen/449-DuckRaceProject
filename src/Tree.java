import java.util.HashSet;

public class Tree<E> {
    private class Node<E>{
        public E data;
		public Node<E> lChild; // left
		public Node<E> rChild; // right


		public Node(E data, Node<E> lChild, Node<E> rChild) {
			this.data = data;
			this.lChild = lChild; // left child
			this.rChild = rChild; // right child
		}

    }

    private int numElt;
    private Node<E> stateNode;
    private LinkedQueue frontier;
    private HashSet reach; // a set bc as the algorithms progresses through there's no telling of quantity of nodes

    public Tree(E initial){
        this.numElt = 0;
        this.stateNode = new Node<E>(initial, null, null);
        this.frontier = new LinkedQueue<>();
        this.reach = new HashSet<>();
        this.frontier.enqueue(stateNode);
    }

    public void add(E data, String directions) {
		// Recursive helper
		this.numElt++;
		this.stateNode = addRecursive(data, directions, this.stateNode);
	}


	private Node<E> addRecursive(E data, String directions, Node<E> current) {
		// Recursive method to add nodes to BTree
		if(directions.equals("")) { // base case, if empty
			return new Node<E>(data, null, null);
		}
		if(directions.charAt(0) == 'L') { // if first character in directions is L
			current.lChild = addRecursive(data, directions.substring(1), current.lChild); // recursively call 
		}
		else {
			current.rChild = addRecursive(data, directions.substring(1), current.rChild); // recursively call

		}
		return current;
	}


    public E breadthFirstSearch(E goal){
         Node <E> goalNode= breadthFirstSearchRecursive(goal);
         // test case for now. We can update it later by making a new node and adding Failure as the data.
         if (goalNode == null){
            return (E) "Failure";
         }
         else{
            return goalNode.data;
         }

    }

    @SuppressWarnings("unchecked")
    private Node<E> breadthFirstSearchRecursive(E goal){
        // O(1)
        // Starts at the root node, check is goal. If not, check to see if a left child exists. If it does add it to the frontier, check to see if a right child exist, if it does add it to the frontier.
        Node <E> current = this.stateNode;
        if(current.data.equals(goal)){ // if initial state is goal then return state space/node
            return current;
        }
        while(!(this.frontier.isEmpty())){
        E currentElement = (E) frontier.dequeue();}
        
        else if(current.lChild == null){ // if not, then check the left child to see if 
            return null; // placeholder for now
        }
        else{
            this.frontier.enqueue(current.lChild);
            if(current.rChild == null){
                return null; // placeholder for now
            }
            else{
                this.frontier.enqueue(current.rChild);
                return null;
            }
        }
        */
    }

    
}
