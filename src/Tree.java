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
    private Queue frontier;

    public Tree(E initial){
        this.numElt = 0;
        this.stateNode = new Node<E>(initial, null, null);
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
         Node <E> goalNode= breadthFirstSearchRecursive(this.stateNode, goal);
         if (goalNode == null){
            return (E) "Failure";
         }
         else{
            return goalNode.data;
         }

    }

    @SuppressWarnings("unchecked")
    private Node<E> breadthFirstSearchRecursive(Node <E> current, E goal){
        if(current.data.equals(goal)){ // if initial state is goal then return state space/node
            return current;
        }
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
    }
}
