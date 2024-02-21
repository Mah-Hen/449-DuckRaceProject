import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class Node<E> {
    public DuckState state;
    public Node<E> parent;
    public Action action;
    public int pathCost;
    // private Node<E> stateNode; //= new Node<E>(state, parent);
    private LinkedQueue frontier = new LinkedQueue<>();
    private HashSet reach = new HashSet<>(); // a set bc as the algorithms progresses through there's no telling of
                                             // quantity of nodes

    @SuppressWarnings("unchecked")
    public Node(DuckState initial, Node<E> parent) {
        this.state = initial;
        this.parent = parent;
        this.pathCost = 0; // user defined.
        // this.stateNode = new Node<E>(initial, null); // Initial state/node
        frontier.enqueue(this);
        reach.add(this);

    }

    public Node(DuckState state, Node<E> current, Action action, int cost) {
        this.state = state;
        this.parent = current;
        this.pathCost = cost; // user defined.
        this.action = action;
    }

    private class Action {
        // This is going to track every possible. Try the nested for loop with if
        // statements. Even try the separate methods too.
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

        /* 
        public List<DuckState> energySwapAction(int duck1Index, int duck2Index) {
            DuckState newState = new DuckState(state.getDuckCounter(), state.getNumofPos(), state.getDuckWithCap(),
                    state.getmaxEnergy());
            if (duck1Index < 0 || duck1Index >= newState.getNumofPos() ||
                    duck2Index < 0 || duck2Index >= newState.getNumofPos()) {
                return null;
            }
            Duck d1 = newState.getDucks()[duck1Index];
            Duck d2 = newState.getDucks()[duck2Index];

            if (d1.getEnergy() > 0) {
                d1.transferEnergy(d2);
            } else if (d2.getEnergy() > 0) {
                d2.transferEnergy(d1);
            } else {
                return null;
            }

            List<DuckState> result = new ArrayList<>();
            result.add(newState);
            return result;
        }
        */
    }

    private boolean canMoveLeft(Duck duck, DuckState state) {
        int middle = state.getNumofPos() / 2;
        if (duck.getPosition() < state.getNumofPos() && duck.getEnergy() > 0) {
            if (duck.getPosition() >= 0 || duck.getPosition() == middle && duck.hasCap()) {
                return true;
            }
        }
        return false;
    }

    private boolean canMoveRight(Duck duck, DuckState state) {
        int middle = state.getNumofPos() / 2;
        // So, if duck is within the board and has energy all while not at the start
        if (duck.getPosition() < state.getNumofPos() && duck.getEnergy() > 0 && duck.getPosition()!=0) {
            // also check if the duck has a flag or that the ducks without the cap are on the right side of the board 
            if (duck.hasFlag() || duck.getPosition() <= middle && !duck.hasCap()) {
                // then they're able to move
                return true;
            }
        }
        return false;
    }

    public boolean canTransferEnergy(Duck duck1, Duck duck2, DuckState currentState) {
        // If the two ducks are within the range and have enrgy and are above each other
        // then they can transfer energy
        if (duck1.getPosition() == duck2.getPosition() && duck1.getEnergy() > 1 && duck2.getEnergy() > 1 
        && duck1.getPosition() > 0 && duck1.getPosition() < currentState.getNumofPos() 
        && duck2.getPosition() > 0 && duck2.getPosition() < currentState.getNumofPos()) {
            // If they both have MAX energy then no transfer is available
            if (duck1.getEnergy() < currentState.getmaxEnergy() || duck2.getEnergy() < currentState.getmaxEnergy()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    private List<Action> generateActions(DuckState currenState) {
        List<Action> actions = new ArrayList<>();
        Duck[] ducks = currenState.getDucks();

        for (int i = 0; i < ducks.length; i++) {
            Duck duck = ducks[i];
            if (canMoveLeft(duck, state)) {
                actions.add(new Action("L", i)); // So duck0 or the duck at pos/row 0 can move left
            }
            if (canMoveRight(duck, state)) {
                actions.add(new Action("R", i));
            }
        }
        for (int i = 0; i < ducks.length; i++) {
            Duck duck1 = ducks[i];
            for (int j = 0; j < ducks.length; j++) {
                if (i == j) { // a case to make sure that it is not transferring energy to itself
                    continue;
                }
                Duck duck2 = ducks[j];
                if (canTransferEnergy(duck1, duck2, state)) {
                    actions.add(new Action("t->", i, j));
                }
            }
        }

        return actions;
    }

    private DuckState result(DuckState currentState, Action action) {
            DuckState changedState = new DuckState(currentState.getDuckCounter(), currentState.getNumofPos(),
                currentState.getDuckWithCap(), currentState.getmaxEnergy());
            Duck duck = changedState.getDuck(action.duckNumber);
            if (action.type.equals("L")) { // If canMoveLeft
                duck.decreaseEnergy();
                duck.incPosition();
            }
            else if (action.type.equals("R")) { // If canMoveRight
                duck.decreaseEnergy();
                duck.decPosition();
            }
            else if(action.type.equals("t->")) { // If can't move right or left then can only Transfer.
                Duck receiverDuck = changedState.getDuck(action.receiverDuckNumber);
                duck.transferEnergy(receiverDuck);
            }
        return changedState;
    }
    /* 
    private int actionCost(DuckState currentState, Action action, DuckState changedState) {
        int actionCost = 0;
        if (action.type.equals("L")) { // if moveLeft Action
                actionCost ++;
        }
        else if (action.type.equals("R")) {
                actionCost++;
        }
        else if (action.type.equals("t->")) {
            actionCost ++;
        }
        return actionCost;
    }
    */

    private String getPath(Node<DuckState> completedNode){ // This node reached the goal
        String goalPath = "";
        List<Node<DuckState>> pathNodes = new ArrayList<>();
        pathNodes = getPathRecursive(completedNode, pathNodes);
        for(int i=0; i<pathNodes.size();i++){
            Node<DuckState> pathNode = pathNodes.get(i);
            if(pathNode.action.type.equals("L") || pathNode.action.type.equals("R")){
            goalPath += pathNode.action.type + pathNode.action.duckNumber;
            }
            if(pathNode.action.type.equals("t->")){
                goalPath += pathNode.action.duckNumber + pathNode.action.type + pathNode.action.receiverDuckNumber;
            }
        }
        return goalPath;
    }

    private List<Node<DuckState>> getPathRecursive(Node<DuckState> current, List<Node<DuckState>> path){
        if(current.parent == null){ // first/initial node
            path.add(0, current);
            return path;
        }
        path.add(0, current);
        getPathRecursive(current.parent, path);
        return path;
    }

    private List<Node<DuckState>> expand(Node<DuckState> current) {
        DuckState currentState = current.state;
        List<Node<DuckState>> successorNodes = new ArrayList<>();

        for (Action action : generateActions(currentState)) {
            DuckState changedState = result(currentState, action);
            int cost = current.pathCost + 1;//actionCost(currentState, action, changedState);
            Node<DuckState> succesorNode = new Node<DuckState>(changedState, current, (Node<DuckState>.Action) action,
                    cost);
            successorNodes.add(succesorNode);
        }
        return successorNodes;

    }

    public String breadthFirstSearch() {
        Node<DuckState> completeNode = breadthFirstSearchHelper(); // the node that completed the puzzle or reached the goal
        // test case for now. We can update it later by making a new node and adding
        // Failure as the data.
        if (completeNode == null) {
            return "Failuer";
        } else {
            return getPath(completeNode);
        }

    }

    @SuppressWarnings("unchecked")
    private Node<DuckState> breadthFirstSearchHelper() {
        // O(1)
        // Starts at the root node, check is goal. 
        Node<DuckState> current = new Node<DuckState>(this.state, null); // initial state
        if (this.isgoal(current.state)) { // if initial state is goal then return state space/node
            return current;
        }
        while (!(this.frontier.isEmpty())) {
            Node<DuckState> currentNode = (Node<DuckState>) frontier.dequeue(); // Node
            for (Node<DuckState> child : expand(currentNode)) {
                DuckState nodeState = (DuckState) child.state;
                if (this.isgoal(nodeState)) {
                    return child; // nodeState/Child same thing. Can change later
                } else {
                    this.reach.add(nodeState);
                    this.frontier.enqueue(child);
                }
            }
        }
        return null; // failure

        /*
         * # Generate successor nodes from the current node
         * successors = generateSuccessors(currentNode)
         * 
         * # Add the successors to the frontier
         * for successor in successors:
         * frontier.enqueue(successor)
         * /*
         * for(Node<E> child : this.expand){
         * E s = child.state;
         * if(s.equals(goal)){ // if state matches goal
         * return null; // child
         * }
         * else{
         * this.frontier.enqueue(child);
         * 
         * }
         * }
         * 
         * }
         */
    }

    public DuckState bestfirstsearch() {
        Node<DuckState> goalNode = bestfirstsearchhelper();
        if (goalNode == null) {
            return null;
        } else {
            return goalNode.state;
        }
    }

    @SuppressWarnings("unchecked")
    private Node<DuckState> bestfirstsearchhelper() {
        if (frontier.isEmpty()) {
            return null;
        }
        while (!frontier.isEmpty()) {
            Node<DuckState> currentNode = (Node<DuckState>) frontier.dequeue();
            if (this.isgoal(currentNode.state)) {
                return currentNode;
            }
            List<Node<DuckState>> child = expand(currentNode);
            for (Node<DuckState> newchild : child) {
                DuckState childstate = newchild.state;
                if (!reach.contains(childstate)) {
                    reach.add(childstate);
                    frontier.enqueue(newchild);
                }
            }
        }
        return null;
    }

    public DuckState aStarSearch(){
        PriorityQueue<DuckState> pQueue = new PriorityQueue<>();
        return null;
        }

    public boolean isgoal(DuckState State) {
        Duck[] ducks = State.getDucks();
        int counter = 0;
        for (int i = 0; i < ducks.length; i++) {
            if (ducks[i].hasCap() && ducks[i].hasFlag() && ducks[i].getPosition() == 0) {
                counter++;
            } else if (!ducks[i].hasCap() && !ducks[i].hasFlag() && ducks[i].getPosition() == 0) {
                counter++;
            }
        }
        if (counter == ducks.length) {
            return true;
        }
        return false;

    }
}
