public class PriorityQueue<E extends Comparable<? super E>>{

    private Node<E> root;

    private class Node<E> {
        Node (E value) {
            this(value, null, null);
        }
        Node(E value, Node<E> left, Node<E> right) {
            this.value = value;
            this.left = left;
            this.right = right;
            npl = 0;
        }
        public E value;
        public Node<E> left;
        public Node<E> right;
        public int npl;
    }

    private Node<E> merge(Node<E> t1, Node<E> t2) {
        Node<E> small;
        if (t1 == null) return t2;
        if (t2 == null) return t1;
        if (t1.value.compareTo(t2.value) < 0) {
            t1.right = merge(t1.right, t2);
            small = t1;
        }
        else {
            t2.right = merge(t2.right, t1);
            small = t2;
        }
        if (getNpl(small.left) < getNpl(small.right))
            swapKids(small);

        setNullPathLength(small);

        return small;
    }

    private int getNpl(Node<E> t) {
        if (t == null) return -1;

        return t.npl;
    }

    private Node<E> swapKids(Node<E> t) {
        Node<E> rightTree = t.right;
        t.right = t.left;
        t.left = rightTree;
        return t;
    }

    public void enqueue(E value) {
        Node<E> treeWithValue = new Node<>(value);
        root = merge(root, treeWithValue);
    }

    public boolean isEmpty() {
        if (root == null) {
            return true;
        }
        else{
            return false;
        }
    }

    private void setNullPathLength(Node<E> t) {
        t.npl = getNpl(t.right) + 1;
    }

    public E dequeue(){
        E valueOfRoot = root.value;
        root = merge(root.right, root.left);
        return valueOfRoot;
    }

    public E getValue() {
        return root.value;
    }
}
