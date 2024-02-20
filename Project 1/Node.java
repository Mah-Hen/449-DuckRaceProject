import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Node<E>{
    public DuckState state;
    public Node<E> parent;
    public Action action;
    public int cost;
    //private Node<E> stateNode; //= new Node<E>(state, parent);
    private LinkedQueue frontier = new LinkedQueue<>();
    private HashSet reach = new HashSet<>(); // a set bc as the algorithms progresses through there's no telling of quantity of nodes
    


    @SuppressWarnings("unchecked")
    public Node(DuckState initial, Node<E> parent) {
        this.state = initial;
        this.parent = parent;
        this.cost = 3; // user defined.
        //this.stateNode = new Node<E>(initial, null); // Initial state/node
        frontier.enqueue(this);
        reach.add(this);

    }

    public Node(DuckState state, Node<E> current, Action action2, int cost){
        this.state = state;
        this.parent = current;
        this.cost = cost; // user defined.
        this.action = action2;
    }

    private class Action{
        // This is going to track every possible. Try the nested for loop with if statements. Even try the separate methods too. 
        private String type;
        private int duckNumber;
        private int receiverDuckNumber;

        public Action(String type, int duckNumber) {
            this.type = type;
            this.duckNumber = duckNumber;
        }

        public Action(String type, int duckNumber, int receiverDuckNumber) {
            this.type = type;
            this.duckNumber = duckNumber;
            this.receiverDuckNumber = receiverDuckNumber;
        }
        
    }
    

    private boolean canMoveLeft(Duck duck, DuckState state){
        int middle = state.getNumofPos()/2;
        if(duck.getPosition() < state.getNumofPos() && duck.getEnergy() > 0){
            if(duck.getPosition() >= 0 || duck.getPosition()==middle && duck.hasCap()){
                return true;
            }
        }
        return false;
    }

    private boolean canMoveRight(Duck duck, DuckState state){
        int middle = state.getNumofPos()/2;
        if(duck.getPosition() < state.getNumofPos() && duck.getEnergy() > 0){
            if(duck.hasFlag() || duck.getPosition() <= middle && !duck.hasCap()){
                return true;
            }
        }
        return false;
    }

    public boolean canTransferEnergy(Duck duck1, Duck duck2, DuckState currentState){
        if (duck1.getPosition() < 0 || duck1.getPosition() >= currentState.getNumofPos() || duck2.getPosition() < 0 || duck2.getPosition() >= currentState.getNumofPos()){
            return false;
        }
        if(duck1.getEnergy() < 1 || duck2.getEnergy() < 1){
            return false;
        }
        else{
            return true;
        }

    }

    private List<Action> generateActions(DuckState currenState){
        List<Action> actions = new ArrayList<>();
        Duck [] ducks = currenState.getDucks();

        for(int i=0; i < ducks.length; i++){
            Duck duck = ducks[i];
            if (canMoveLeft(duck, state)) {
                actions.add(new Action("L", duck.getPosition()));
            }
            if (canMoveRight(duck, state)) {
                actions.add(new Action("R", duck.getPosition()));
            }
        }
        for (int i = 0; i <ducks.length; i++) {
            Duck duck1 = ducks[i];
            for (int j = i + 1; j < ducks.length; j++) {
                Duck duck2 = ducks[j];
                if (canTransferEnergy(duck1, duck2, state)) {
                    actions.add(new Action("t->", duck1.getPosition(), duck2.getPosition()));
                }
            }
        }
    
        return actions;
    }

    private DuckState result(Node<DuckState> currentState, Action action){
        DuckState newState = currentState.state; // Create a copy of the current state
        Duck[] ducks = newState.getDucks();
        for(int i = 0; i<ducks.length; i++){
            Duck duck = ducks[i];
            if(action.type.equalsIgnoreCase("L")){
                canMoveLeft(duck, newState);
                break;
        }
        if (action.type.equalsIgnoreCase("R")) {
            canMoveRight(duck, newState);
            break;
        }
        for (int j = 0; j < ducks.length; j++) {
            Duck duck2 = ducks[j];
        
            if (action.type.equalsIgnoreCase("t->")) {
                canTransferEnergy(duck, duck2, newState);
                break;
            }   
        }
        
    }
        return newState;
    }   
    

    private int actionCost(DuckState currentState, Action action, DuckState changedState){
        cost = 1;
        if (action.type.equalsIgnoreCase("L")) {
            if (changedState.getDuckWithCap() < currentState.getDuckWithCap()) {
                cost += 1;
            }
            else if (action.type.equalsIgnoreCase("R")) {
                if (changedState.getDuckWithCap() < currentState.getDuckWithCap()) {
                    cost += 1;
                }
            }
            else if (action.type.equalsIgnoreCase("t->")) {
                Duck duckwithCap = changedState.getDuck(changedState.getDuckWithCap());
                if (duckwithCap.getPosition() < currentState.getDuckWithCap()) {
                    cost += 1;
                }
            }
            return cost;
        }
        return cost;
    }

    private List<Node <DuckState>> expand(Node<DuckState> current){ 
        DuckState currentState = current.state;
        List<Node<DuckState>> successorNodes = new ArrayList<>();

        for(Action action: generateActions(currentState)){
           DuckState changedState = result(currentState, action);
           int cost = current.cost + actionCost(currentState, action, changedState);
           Node<DuckState> succesorNode =  new Node<DuckState>(changedState, current, (Node<DuckState>.Action) action, cost);
           successorNodes.add(succesorNode);
        }
        return successorNodes;
        
    }
    

    public DuckState breadthFirstSearch(){
         Node <DuckState> goalNode= breadthFirstSearchHelper();
         // test case for now. We can update it later by making a new node and adding Failure as the data.
         if (goalNode == null){
            return null;
         }
         else{
            return goalNode.state;
         }

    }

    @SuppressWarnings("unchecked")
    private Node<DuckState> breadthFirstSearchHelper(){
        // O(1)
        // Starts at the root node, check is goal. If not, check to see if a left child exists. If it does add it to the frontier, check to see if a right child exist, if it does add it to the frontier.
        Node <DuckState> current = new Node<DuckState>(this.state, null); // initial state
        if(this.isgoal(current.state)){ // if initial state is goal then return state space/node
            return current;
        }
        while(!(this.frontier.isEmpty())){
            Node <DuckState> currentNode = (Node<DuckState>) frontier.dequeue(); // Node
            for(Node<DuckState> child : expand(currentNode)){
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

    public DuckState bestfirstsearch() {
        Node<DuckState> goalNode = bestfirstsearchhelper();
        if (goalNode == null) {
            return null;
        }
        else {
            return goalNode.state;
        }
    }

    @SuppressWarnings("unchecked")
    private Node<DuckState> bestfirstsearchhelper() {
        if (frontier.isEmpty()) {
            return null;
        }
        while(!frontier.isEmpty()) {
            Node<DuckState> currentNode = (Node<DuckState>) frontier.dequeue();
            if (this.isgoal(currentNode.state)) {
                return currentNode;
            }
            List<Node<DuckState>> child = expand(currentNode);
            for (Node<DuckState> newchild : child) {
                DuckState childstate = newchild.state;
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