public class Driver {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Tree tree = new Tree<String>("Justina");
        System.out.println(tree.breadthFirstSearch("Justina"));
    }
}
