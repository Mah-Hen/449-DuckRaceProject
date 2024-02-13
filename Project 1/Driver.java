public class Driver {
    public static void main(String[] args) {
        Tree tree = new Tree<String>("Robert");
        tree.add("Jlo");
        tree.add("Justina");
        System.out.println("Is Robert in the tree? ")
        System.out.println(tree.breadthFirstSearch("Justina"));
    }
}
