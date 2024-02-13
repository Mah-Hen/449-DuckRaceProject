import java.util.ArrayList;
import java.util.HashSet;

public class Tree<E> {
    private class Node<E>{
        public E state;
		public Node<E> parent;
        public int cost;


		public Node(E state, Node<E> parent) {
			this.state = state;
			this.parent = parent;
            this.cost = 3; // user defined.
		}

        public Node(E state, Node<E> parent, int cost){
            this.state = state;
			this.parent = parent;
            this.cost = cost; // user defined.
        }



    }

    private int numElt;
    private Node<E> stateNode;
    private LinkedQueue frontier;
    private HashSet reach; // a set bc as the algorithms progresses through there's no telling of quantity of nodes

    public Tree(E initial){
        this.numElt = 0;
        this.stateNode = new Node<E>(initial, null);
        this.frontier = new LinkedQueue<>();
        this.reach = new HashSet<>(); // lookup table
        this.frontier.enqueue(stateNode);
        this.reach.add(stateNode);
    }

    public void add(E state) {
		// Recursive helper
		this.numElt++;
		this.stateNode = addHelper(state, this.stateNode);
	}


	private Node<E> addHelper(E state, Node<E> current) {
		// Recursive method to add nodes to BTree
		if(current == null) { // base case, if empty
			return new Node<E>(state, null); // initial state (root)
		}
		else{
            Node<E> childNode = new Node<E>(state, current);  
            return childNode;
        }
	}

    private ArrayList<E> expand(Node<E> current){
        ArrayList<E> successorNodes = new ArrayList<>(); 
        E state = current.state;

        Node <E> successNode = new Node<E>(state, current);
        return successorNodes;
    }


    public E breadthFirstSearch(E goal){
         Node <E> goalNode= breadthFirstSearchRecursive(goal);
         // test case for now. We can update it later by making a new node and adding Failure as the data.
         if (goalNode == null){
            return (E) "Failure";
         }
         else{
            return goalNode.state;
         }

    }

    @SuppressWarnings("unchecked")
    private Node<E> breadthFirstSearchRecursive(E goal){
        // O(1)
        // Starts at the root node, check is goal. If not, check to see if a left child exists. If it does add it to the frontier, check to see if a right child exist, if it does add it to the frontier.
        Node <E> current = this.stateNode;
        if(current.state.equals(goal)){ // if initial state is goal then return state space/node
            return current;
        }
        while(!(this.frontier.isEmpty())){
            E currentElement = (E) frontier.dequeue();}
            /* 
            # Generate successor nodes from the current node
            successors = generateSuccessors(currentNode)
        
            # Add the successors to the frontier
            for successor in successors:
                frontier.enqueue(successor)
            /* 
            for(Node<E> child : this.expand){
                E s = child.state;
                if(s.equals(goal)){ // if state matches goal
                    return null; // child
                }
                else{
                    this.frontier.enqueue(child);

                }
            }
            
        }*/
        
       


       return null;
    }

    
}
