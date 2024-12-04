class MyBST {
    private class Node {
        PeopleRecord data;
        Node left, right;

        Node(PeopleRecord data) {
            this.data = data;
            left = right = null;
        }
    }

    private Node root;

    public void insert(PeopleRecord record) {
        root = insertRec(root, record);
    }

    private Node insertRec(Node root, PeopleRecord record) {
        if (root == null) {
            root = new Node(record);
            return root;
        }

        if (record.compareTo(root.data) < 0) {
            root.left = insertRec(root.left, record);
        } else {
            root.right = insertRec(root.right, record);
        }
        return root;
    }

    public void inorderTraversal() {
        inorderRec(root);
    }
    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root.data);
            inorderRec(root.right);
        }
    }
}
