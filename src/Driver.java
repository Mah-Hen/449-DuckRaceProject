public class Driver {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Tree tree = new Tree<String>("Robert");
        tree.add("Jlo", "L");
        tree.add("Justina", "R");
        System.out.println(tree.breadthFirstSearch("Justina"));
        

    }
}
