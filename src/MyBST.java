import javax.swing.*;
import java.awt.*;

class MyBST {
    public class Node {
        PeopleRecord data;
        Node left, right;

        Node(PeopleRecord data) {
            this.data = data;
            left = right = null;
        }
    }

    private Node root;

    public Node getRoot(){
        return root;
    }
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
    public String getInfo() {
        int nodeCount = getNodeCount(root);
        int height = getHeight(root);
        return "Number of nodes: " + nodeCount + ", Height: " + height;
    }

    // Helper method to count nodes
    private int getNodeCount(Node root) {
        if (root == null) {
            return 0;
        }
        return 1 + getNodeCount(root.left) + getNodeCount(root.right);
    }

    // Helper method to calculate the height of the tree
    private int getHeight(Node root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        return 1 + Math.max(leftHeight, rightHeight);
    }

    public PeopleRecord search(String givenName, String familyName){
        //use helper recursive method
        return searchRec(root, givenName, familyName).data;
    }
    private Node searchRec(Node root, String givenName, String familyName) {
        if (root == null) {
            return null; // Base case: not found
        }

        // Combine family name and given name for comparison (you can adjust the logic if needed)
        String searchKey = givenName+familyName ;
        String currentKey = root.data.getGivenName()+root.data.getFamilyName() ;

        if (searchKey.compareTo(currentKey) < 0) {
            return searchRec(root.left, givenName, familyName); // Go left
        } else if (searchKey.compareTo(currentKey) > 0) {
            return searchRec(root.right, givenName, familyName); // Go right
        } else {
            return root; // Found the node
        }
    }
    // Function to draw the tree
    public void drawTree() {
        JFrame frame = new JFrame("Binary Search Tree Visualization");
        //close the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (root != null) {
                    //call the recursive method
                    drawNode(g, root, getWidth() / 2, 50, getWidth() / 4);
                }
            }
        };
        //make it scrollable cause there is too many objects
        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.add(scrollPanel);
        frame.setVisible(true);
    }
    // Recursive function to draw nodes and edges
    private void drawNode(Graphics g, Node node, int x, int y, int xOffset) {
        if (node == null) return;

        // Draw current node
        g.setColor(Color.BLUE);
        g.fillOval(x - 15, y - 15, 30, 30);
        g.setColor(Color.BLACK);
        g.drawString(node.data.getGivenName() + " " + node.data.getFamilyName(), x - 30, y - 20);

        // Draw left child
        if (node.left != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x, y, x - xOffset, y + 50); // Draw edge
            drawNode(g, node.left, x - xOffset, y + 50, xOffset / 2); // Recursively draw left subtree
        }

        // Draw right child
        if (node.right != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x, y, x + xOffset, y + 50); // Draw edge
            drawNode(g, node.right, x + xOffset, y + 50, xOffset / 2); // Recursively draw right subtree
        }
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
