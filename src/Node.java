import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.lang.Comparable;

public class Node<E> {
    private DuckState state;
    private Node<E> parent;
    private Action action;
    private int pathCost; // g Cost
    private int h_cost; // heuristic function cost
    private int f_cost; // g_cost + h_cost
    // private Node<E> stateNode; //= new Node<E>(state, parent);
    private LinkedQueue frontier = new LinkedQueue<>();
    private HashMap<DuckState, Node<E>> reach = new HashMap<>(); // a set bc as the algorithms progresses through
                                                                 // there's no telling of
    // quantity of nodes

    @SuppressWarnings("unchecked")
    public Node(DuckState initial, Node<E> parent) {
        this.state = initial;
        this.parent = parent;
        this.pathCost = 0;
        // this.stateNode = new Node<E>(initial, null); // Initial state/node
        frontier.enqueue(this);

    }

    public Node(DuckState state, Node<E> current, Action action, int cost) {
        this.state = state;
        this.parent = current;
        this.pathCost = cost;
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
         * public List<DuckState> energySwapAction(int energyFromDuckIndex, int
         * energyToDuckIndex) {
         * DuckState newState = new DuckState(state.getDuckCounter(),
         * state.getNumofPos(), state.getDuckWithCap(),
         * state.getmaxEnergy());
         * if energyFromDuckIndex < 0 || energyFromDuckIndex >= newState.getNumofPos()
         * ||
         * energyToDuckIndex < 0 || energyToDuckIndex >= newState.getNumofPos()) {
         * return null;
         * }
         * Duck d1 = newState.getDucks() energyFromDuckIndex];
         * Duck d2 = newState.getDucks() energyToDuckIndex];
         * 
         * if (d1.getEnergy() > 0) {
         * d1.transferEnergy(d2);
         * } else if (d2.getEnergy() > 0) {
         * d2.transferEnergy(d1);
         * } else {
         * return null;
         * }
         * 
         * List<DuckState> result = new ArrayList<>();
         * result.add(newState);
         * return result;
         * }
         */
    }

    private int getPathCost() {
        return this.pathCost;
    }

    private int getHeursticCost() {
        return this.h_cost;
    }

    private void setHeursticCost(int h_cost) {
        this.h_cost = h_cost;
    }

    private int getTotalCost() {
        return this.f_cost;
    }

    private void setTotalCost(int f_cost) {
        this.f_cost = f_cost;
    }

    private boolean canMoveLeft(Duck duck, DuckState state) {
        int middle = state.getNumofPos() / 2;
        if (duck.getPosition() < state.getNumofPos() - 1 && duck.getEnergy() > 0) {
            if (duck.getPosition() <= middle + 1 || duck.hasCap()) {
                if (duck.getPosition() + 1 == state.getNumofPos() - 1 && !duck.hasCap()) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private boolean canMoveRight(Duck duck, DuckState state) {
        int middle = state.getNumofPos() / 2;
        // So, if duck is within the board and has energy, all while not at the start
        if (duck.getPosition() < state.getNumofPos() && duck.getEnergy() > 0 && duck.getPosition() != 0) {
            // then check if the duck has a flag or that the ducks without the cap are on
            // the right side of the board
            if (duck.hasFlag() || duck.hasCap() || duck.getPosition() <= middle + 1 && !duck.hasCap()) {
                // then they're able to move
                return true;
            }
        }
        return false;
    }

    public boolean canTransferEnergy(Duck energyFromDuck, Duck energyToDuck, DuckState currentState) {
        // If the two ducks are within the range and have enough energy to
        // commit/receive and are above each other
        // then they can transfer energy
        if (energyFromDuck.getPosition() == energyToDuck.getPosition() && energyFromDuck.getEnergy() > 0
                && energyToDuck.getEnergy() >= 0
                && energyFromDuck.getPosition() >= 0 && energyFromDuck.getPosition() < currentState.getNumofPos()
                && energyToDuck.getPosition() >= 0 && energyToDuck.getPosition() < currentState.getNumofPos()) {
            // If they both have MAX energy then no transfer is available
            if (energyFromDuck.getEnergy() <= currentState.getmaxEnergy()
                    && energyToDuck.getEnergy() < currentState.getmaxEnergy()) {
                return true;
            }
        }
        return false;

    }

    private List<Action> generateActions(DuckState currenState) {
        List<Action> actions = new ArrayList<>(); // Create list of actions
        Duck[] ducks = currenState.getDucks(); // Retrieve list of ducks

        for (int i = 0; i < ducks.length; i++) { // iterate through list of ducks
            Duck duck = ducks[i]; //
            if (canMoveLeft(duck, currenState)) {
                actions.add(new Action("L", i)); // So duck0 or the duck at pos/row 0 can move left
            }
            if (canMoveRight(duck, currenState)) {
                actions.add(new Action("R", i));
            }
        }
        for (int i = 0; i < ducks.length; i++) {
            Duck energyFromDuck = ducks[i];
            for (int j = 0; j < ducks.length; j++) {
                if (i == j) { // a case to make sure that it is not transferring energy to itself
                    // all while also checking to see if the ducks are adjacent
                    continue;
                }
                if ((i + 1 == (j)) || (i - 1 == (j))) {
                    Duck energyToDuck = ducks[j];
                    if (canTransferEnergy(energyFromDuck, energyToDuck, currenState)) {
                        actions.add(new Action("t->", i, j));
                    }
                }
            }
        }

        return actions;
    }

    private DuckState result(DuckState currentState, Action action) {
        DuckState changedState = new DuckState(currentState.getDuckCounter(), currentState.getNumofPos(),
                currentState.getDuckWithCap(), currentState.getmaxEnergy());

        Duck[] ducks = changedState.getDucks(); // get list of ducks to iterate over
        if (!changedState.equals(currentState)) { // If the two states do not have the same state
            for (int i = 0; i < currentState.getDuckCounter(); i++) { // then loop through each duck
                Duck prevStateDuck = currentState.getDuck(i);
                ducks[i] = new Duck(prevStateDuck.getName(), prevStateDuck.hasFlag(), prevStateDuck.getEnergy(),
                        prevStateDuck.hasCap(), prevStateDuck.getPosition()); // and alter the currentState to match the
                                                                              // previous state. We don't want to
                                                                              // reference the previous state and
                                                                              // creating a new instance requires an
                                                                              // update.
            } // Everytime we alter the currentState in the result method, it will alter the
              // reference variable and even take that modification past this method.
        }
        Duck duck = changedState.getDuck(action.duckNumber); // retrieve the duck that is doing the action

        if (action.type.equals("L")) { // If action type is L or canMoveLeft
            duck.decreaseEnergy(); // then decrease the duck's energy
            duck.incPosition(); // move its position left
            if (duck.getPosition() == currentState.getNumofPos() - 1 && duck.hasCap()) { // All while checking to see if
                                                                                         // the duck with cap made it to
                                                                                         // the end
                duck.pickUpFlag(); // to retrieve the flag
            }
        } else if (action.type.equals("R")) { // If canMoveRight
            duck.decreaseEnergy(); // then decrease the duck's energy
            duck.decPosition(); // move its position left
        } else if (action.type.equals("t->")) { // If can't move right or left then can only Transfer.
            Duck receiverDuck = changedState.getDuck(action.receiverDuckNumber); // Receive the receiver duck based on
                                                                                 // the action type receiver duck number
            duck.transferEnergy(receiverDuck); // commit the energy transfer from current duck to receiver duck
        }
        return changedState;
    }
    /*
     * private int actionCost(DuckState currentState, Action action, DuckState
     * changedState) {
     * int actionCost = 0;
     * if (action.type.equals("L")) { // if moveLeft Action
     * actionCost ++;
     * }
     * else if (action.type.equals("R")) {
     * actionCost++;
     * }
     * else if (action.type.equals("t->")) {
     * actionCost ++;
     * }
     * return actionCost;
     * }
     */

    private String getPath(Node<E> completedNode) { // This node reached the goal
        String goalPath = ""; // Declare our goalPath string
        List<Node<E>> pathNodes = new ArrayList<>(); // initialize our path via nodes as a list
        pathNodes = getPathRecursive(completedNode, pathNodes); // retrieve the path via nodes from the method using the
                                                                // completedNode and the empty list
        for (int i = 0; i < pathNodes.size(); i++) {
            Node<E> pathNode = pathNodes.get(i); // retrieve each path
            if (pathNode.pathCost == 0) { // if pathNode is initial state node or there was no action
                goalPath += ""; // then do nothing
            }
            // else if the action is a horizontal move, left or right
            else if (pathNode.action.type.equals("L") || pathNode.action.type.equals("R")) {
                goalPath += pathNode.action.type + pathNode.action.duckNumber + " "; // then append the action type
                                                                                     // (Left or Right) with the duck's
                                                                                     // number
            }
            // else if the action is a transfer
            else if (pathNode.action.type.equals("t->")) {
                // then append the transmitter duck's number with the transfer action type to
                // the receiver duck's number
                goalPath += pathNode.action.duckNumber + pathNode.action.type + pathNode.action.receiverDuckNumber
                        + " ";
            }
        }
        return goalPath;
    }

    private List<Node<E>> getPathRecursive(Node<E> current, List<Node<E>> path) {
        if (current.parent == null) { // if the node is the initial state node
            path.add(0, current); // then we can add that to the beginning of our path
            return path;
        }
        // else the node is backtracing to the intial state node
        path.add(0, current); // add the current node to the beginning of the path, almost backwards
        getPathRecursive(current.parent, path); // finally, recall the function but with the parent and the path list of
                                                // nodes
        return path;
    }

    private List<Node<E>> expand(Node<E> current) {
        DuckState currentState = new DuckState(current.state.getDuckCounter(), current.state.getNumofPos(),
                current.state.getDuckWithCap(), current.state.getmaxEnergy());// current.state; // +1 because the
                                                                              // constructor takes 1 one away.
        Duck[] ducks = currentState.getDucks();
        if (!currentState.equals(current.state)) { // If the two states do not have the same state
            for (int i = 0; i < currentState.getDuckCounter(); i++) { // then loop through each duck
                Duck prevStateDuck = current.state.getDuck(i);
                ducks[i] = new Duck(prevStateDuck.getName(), prevStateDuck.hasFlag(), prevStateDuck.getEnergy(),
                        prevStateDuck.hasCap(), prevStateDuck.getPosition()); // and alter the currentState to match the
                                                                              // previous state. We don't want to
                                                                              // reference the previous state and
                                                                              // creating a new instance requires an
                                                                              // update.
            } // Everytime we alter the currentState in the expand method, it will alter the
              // reference variable and even take that modification past this method.
        }

        List<Node<E>> successorNodes = new ArrayList<>(); // Create a list of successorNodes

        for (Action action : generateActions(currentState)) { // Loop through each generated action from the newly
                                                              // updated current state
            DuckState changedState = result(currentState, action);
            int cost = current.pathCost + 1;// actionCost(currentState, action, changedState);
            Node<E> succesorNode = new Node<E>(changedState, current, action,
                    cost);
            successorNodes.add(succesorNode);
        }
        return successorNodes;

    }

    public String breadthFirstSearch() {
        Node<E> completeNode = breadthFirstSearchHelper(); // the node that completed the puzzle or reached the goal
        // test case for now. We can update it later by making a new node and adding
        // Failure as the data.
        if (completeNode == null) {
            return "Failure";
        } else {
            return getPath(completeNode);
        }

    }

    @SuppressWarnings("unchecked")
    private Node<E> breadthFirstSearchHelper() {
        // O(N)
        // Starts at the root node, check is goal.
        Node<E> initial = new Node<E>(this.state, null); // initial state
        reach.put(this.state, initial);
        if (this.isGoal(initial.state)) { // if initial state is goal then return state space/node
            return initial;
        }
        while (!(this.frontier.isEmpty())) {
            Node<E> currentNode = (Node<E>) frontier.dequeue(); // Node
            for (Node<E> child : expand(currentNode)) {
                DuckState nodeState = (DuckState) child.state;
                if (this.isGoal(nodeState)) {
                    return child; // nodeState/Child same thing. Can change later
                } else {
                    this.reach.put(nodeState, child);
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

    public String bestFirstSearch() {
        Node<E> completedNode = bestfirstsearchhelper();
        if (completedNode == null) {
            return "failure";
        } else {
            return getPath(completedNode);
        }
    }

    @SuppressWarnings("unchecked")
    private Node<E> bestfirstsearchhelper() {
        if (frontier.isEmpty()) {
            return null;
        }
        while (!frontier.isEmpty()) {
            Node<E> currentNode = (Node<E>) frontier.dequeue();
            if (this.isGoal(currentNode.state)) {
                return currentNode;
            }
            for (Node<E> child : expand(currentNode)) {
                DuckState childState = child.state;
                if (!reach.containsKey(childState) || child.getPathCost() < reach.get(childState).getPathCost()) {
                    reach.put(childState, child);
                    frontier.enqueue(child);
                }
            }
        }
        return null;
    }

    private Node<E> aStarSearchHelper() {
        PriorityQueue<Node<E>> pQueue = new PriorityQueue<>(); // frontier
        Node<E> intialNode = new Node<>(this.state, null);
        pQueue.add(intialNode);
        if (pQueue.isEmpty()) {
            return null;
        }
        while (!pQueue.isEmpty()) {
            Node<E> currentNode = (Node<E>) pQueue.poll();
            if (this.isGoal(currentNode.state)) {
                return currentNode;
            }
            for (Node<E> child : expand(currentNode)) {
                DuckState childState = child.state;
                if (!reach.containsKey(childState) || child.getPathCost() < reach.get(childState).getPathCost()) {
                    reach.put(childState, child);
                    frontier.enqueue(child);
                    child.setHeursticCost(heuristicFunction(childState));
                    child.setTotalCost(child.h_cost + child.getPathCost());
                    for (Node<E> stateNode : pQueue) {
                        if (child.getTotalCost() > stateNode.getTotalCost()) {
                            pQueue.add(child);
                        }
                    }
                }
            }
        }
        return null;
    }

    private int heuristicFunction(DuckState state) {
        int estimatedStateMoveCost = 0; // our cost for this state
        for (int i = 0; i < state.getDuckCounter(); i++) { // iterate through each duck
            Duck duck = state.getDuck(i);
            if (duck.hasCap()) { // if duck has capture the flag
                if (duck.hasFlag()) { // count his steps to reach back to his goalstate
                    estimatedStateMoveCost += duck.getPosition() - 0; // 0 is the initial/beginning of the grid
                }
                estimatedStateMoveCost += (state.getNumofPos() - 1) - duck.getPosition() + (state.getNumofPos() - 1);
            } else {
                estimatedStateMoveCost += duck.getPosition() - 0; // 0 is the initial/beginning of the grid, so we'll
                                                                  // subtract their position relative to the beginnning
            }
        }
        return estimatedStateMoveCost; // return the sum of costs of individual moves away from the goal.
    }

    private boolean isGoal(DuckState state) {
        DuckState goalState = new DuckState(this.state.getDuckCounter(), this.state.getNumofPos(),
                this.state.getDuckWithCap(), this.state.getmaxEnergy());
        for (int i = 0; i < goalState.getDuckCounter(); i++) {
            Duck goalStateDuck = goalState.getDuck(i);
            if (goalStateDuck.hasCap()) {
                goalStateDuck.pickUpFlag();
            }
        }
        return goalState.equals(state);
        /*
         * Duck[] ducks = State.getDucks();
         * int counter = 0;
         * for (int i = 0; i < ducks.length; i++) {
         * Duck duck = ducks[i];
         * if (duck.hasCap() && duck.hasFlag() && duck.getPosition() == 0) {
         * counter++;
         * }
         * if (!duck.hasCap() && !duck.hasFlag() && duck.getPosition() == 0) {
         * counter++;
         * }
         * }
         * if (counter == ducks.length) {
         * return true;
         * }
         */

    }

}
