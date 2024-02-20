import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Node<E>{
    public E state;
    public Node<E> parent;
    public int cost;
    private int numElt;
    private Node<E> stateNode;
    private LinkedQueue frontier;
    private HashSet reach; // a set bc as the algorithms progresses through there's no telling of quantity of nodes
    private DuckState goalState;



    @SuppressWarnings("unchecked")
    public Node(E initial, Node<E> parent) {
        this.state = initial;
        this.parent = parent;
        this.cost = 3; // user defined.
        this.numElt = 0;
        this.stateNode = new Node<E>(initial, null); // Initial state/node
        this.frontier = new LinkedQueue<>();
        this.reach = new HashSet<>(); // lookup table
        this.frontier.enqueue(stateNode);
        this.reach.add(stateNode);
        
    }

    public Node(E state, Node<E> parent, int cost){
        this.state = state;
        this.parent = parent;
        this.cost = cost; // user defined.
    }

    private class Action{
        // This is going to track every possible. Try the nested for loop with if statements. Even try the separate methods too. 
        public DuckState state;
        private String type;
        private int duckNumber;
        private int receiverDuckNumber;
        List<DuckState> duckStates = new ArrayList<>();

        /*public Action(DuckState s){ // state
            this.state = s;
            
        }*/
        public Action(String type, int duckNumber) {
            this.type = type;
            this.duckNumber = duckNumber;
        }

        public Action(String type, int duckNumber, int receiverDuckNumber) {
            this(type, duckNumber);
            this.receiverDuckNumber = receiverDuckNumber;
        }

        public List<DuckState> moveAction(){
            DuckState newState = new DuckState(state.getDuckCounter(), state.getNumofPos(), state.getDuckWithCap(), state.getmaxEnergy());
            Duck[] ducks = newState.getDucks();
            for(int i=0; i<ducks.length; i++){
                if(ducks[i].getPosition() < newState.getNumofPos() && ducks[i].getEnergy() > 0){ // If the duck's position is not at the end of the board, and they have enough energy.
                    ducks[i].decreaseEnergy();
                    if(ducks[i].getPosition()==0){
                        ducks[i].incPosition();// go left
                    }
                    if(ducks[i].hasCap() && ducks[i].getPosition()==newState.getNumofPos()){ // if has cap and at the end of board 
                        ducks[i].pickUpFlag(); // flag == true
                    }
                    if(ducks[i].hasFlag()){ // 
                        ducks[i].decPosition(); // go right
                    }
                    else{
                        movementDecison();
                    }

                /// test
                }
            }

            return this.duckStates;
        }
    
    
        public List<DuckState> energySwapAction(int duck1Index, int duck2Index){
            DuckState newState = new DuckState(state.getDuckCounter(), state.getNumofPos(), state.getDuckWithCap(), state.getmaxEnergy());
            if (duck1Index < 0 || duck1Index >= newState.getNumofPos() || 
            duck2Index < 0 || duck2Index >= newState.getNumofPos()) {
                return null;
            }
            Duck d1 = newState.getDucks()[duck1Index];
            Duck d2 = newState.getDucks()[duck2Index];

            if (d1.getEnergy() > 0) {
                d1.transferEnergy(d2);
                } 
            else if (d2.getEnergy() > 0) {
                d2.transferEnergy(d1);
            }
            else {
                return null;
            }

            List<DuckState> result = new ArrayList<>();
            result.add(newState);
            return result;
        }
        public List<Action> generateActions(DuckState s){
            List<Action> actions = new ArrayList<>();
            //Action action = new Action(s);
    
            for(int i=0; i < s.getDucks().length; i++){
                Duck duck = s.getDucks().get(i);
                if (canMoveLeft(duck, state)) {
                    actions.add(new Action("L", duck.getNumber()));
                }
                if (canMoveRight(duck, state)) {
                    actions.add(new Action("R", duck.getNumber()));
                }
            }
            for (int i = 0; i < state.getDucks().size(); i++) {
                Duck duck1 = state.getDucks().get(i);
                for (int j = i + 1; j < state.getDucks().size(); j++) {
                    Duck duck2 = state.getDucks().get(j);
                    if (canTransferEnergy(duck1, duck2, state)) {
                        actions.add(new Action("t->", duck1.getNumber(), duck2.getNumber()));
                    }
                }
            }
        
            }
            return actions;
        }
    }
    /*public List<Action> generateActions(DuckState s){
        List<Action> actions = new ArrayList<>();
        Action action = new Action(s);

        for(int i=0; i<0; i++){

        }
        return actions;
    }*/

    private ArrayList<Node <E>> expand(Node<E> current){
        ArrayList<Node<E>> successorNodes = new ArrayList<>(); 
        DuckState state = (DuckState) current.state;
        Action newaction = new Action(goalState);
        for(Action action: newaction.generateActions(state)){
            successorNodes.add(action.generateActions());
        }
        return successorNodes;
    }
    

    public E breadthFirstSearch(){
         Node <E> goalNode= breadthFirstSearchHelper();
         // test case for now. We can update it later by making a new node and adding Failure as the data.
         if (goalNode == null){
            return null;
         }
         else{
            return goalNode.state;
         }

    }

    @SuppressWarnings("unchecked")
    private Node<E> breadthFirstSearchHelper(){
        // O(1)
        // Starts at the root node, check is goal. If not, check to see if a left child exists. If it does add it to the frontier, check to see if a right child exist, if it does add it to the frontier.
        Node <E> current = this.stateNode;
        if(this.isgoal((DuckState)current.state)){ // if initial state is goal then return state space/node
            return current;
        }
        while(!(this.frontier.isEmpty())){
            Node <E> currentNode = (Node<E>) frontier.dequeue(); // Node
            for(Node<E> child : expand(currentNode)){
                DuckState nodeState = (DuckState) child.state;
                if(this.isgoal(nodeState)){
                    return child; // nodeState/Child same thing. Can change later
                }
                else{
                    this.reach.add(nodeState);
                    this.frontier.enqueue(child);
                }
            }
        }
        return null; // failure
            
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
    }

    public E bestfirstsearch() {
        Node<E> goalNode = bestfirstsearchhelper();
        if (goalNode == null) {
            return null;
        }
        else {
            return goalNode.state;
        }
    }

    @SuppressWarnings("unchecked")
    private Node<E> bestfirstsearchhelper() {
        if (frontier.isEmpty()) {
            return null;
        }
        while(!frontier.isEmpty()) {
            Node<E> currentNode = (Node<E>) frontier.dequeue();
            if (this.isgoal(currentNode.goalState)) {
                return currentNode;
            }
            ArrayList<Node<E>> child = expand(currentNode);
            for (Node<E> newchild : child) {
                E childstate = newchild.state;
                if(!reach.contains(childstate)) {
                    reach.add(childstate);
                    frontier.enqueue(newchild);
                }
            }
        }
        return null;
    }

    public boolean isgoal(DuckState State) {
            Duck[] ducks = State.getDucks();
            int counter = 0;
            for(int i=0; i<ducks.length; i++){
                if(ducks[i].hasCap() && ducks[i].hasFlag() && ducks[i].getPosition() == 0) {
                    counter++;
                }
                else if(!ducks[i].hasCap() && !ducks[i].hasFlag() && ducks[i].getPosition() == 0) {
                    counter++;
                }
            }
            if (counter == ducks.length) {
                return true;
            }
        return false;

    }

}