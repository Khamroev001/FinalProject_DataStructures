import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class MyHeap {
    private ArrayList<Node> heap;

    private class Node {
        PeopleRecord data;

        Node(PeopleRecord data) {
            this.data = data;
        }
    }

    public MyHeap() {
        heap = new ArrayList<>();
    }

    // Insert a new record into the heap
    public void insert(PeopleRecord record) {
        Node newNode = new Node(record);
        heap.add(newNode);
        heapifyUp(heap.size() - 1);
    }

    // Heapify up to maintain heap properties
    private void heapifyUp(int index) {
        int parentIndex = (index - 1) / 2; //3
        while (index > 0 && heap.get(index).data.compareTo(heap.get(parentIndex).data) > 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    // Swap two elements in the heap
    private void swap(int index1, int index2) {
        Node temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }

    // Remove and return the maximum element from the heap
    public PeopleRecord removeMax() {
        if (heap.isEmpty()) return null;
        Node maxNode = heap.get(0);
        Node lastNode = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, lastNode);
            heapifyDown(0);
        }
        return maxNode.data;
    }

    // Heapify down to maintain heap properties
    private void heapifyDown(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int largest = index;

        if (leftChild < heap.size() && heap.get(leftChild).data.compareTo(heap.get(largest).data) > 0) {
            largest = leftChild;
        }

        if (rightChild < heap.size() && heap.get(rightChild).data.compareTo(heap.get(largest).data) > 0) {
            largest = rightChild;
        }

        if (largest != index) {
            swap(index, largest);
            heapifyDown(largest);
        }
    }

    // Perform heap sort and return sorted list of PeopleRecord
    public ArrayList<PeopleRecord> heapSort() {
        ArrayList<PeopleRecord> sortedList = new ArrayList<>();
        ArrayList<Node> originalHeap = new ArrayList<>(heap); //get a temporary list for heap, cause we will remove each Node, place it to sortedList and then restore
        while (!heap.isEmpty()) {
            sortedList.add(removeMax());
        }

        heap = originalHeap; // Restore original heap from the temporary variable
        return sortedList;
    }

    // Get number of nodes and height of the heap
    public String getInfo() {
        int nodeCount = heap.size();
        int height = (int) Math.ceil(Math.log(nodeCount + 1) / Math.log(2)) - 1;
        return "Number of nodes: " + nodeCount + ", Height: " + height;
    }

    // Function to draw the heap as a tree
    public void drawHeap() {
        JFrame frame = new JFrame("Heap Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!heap.isEmpty()) {
                    drawNode(g, 0, getWidth() / 2, 50, getWidth() / 4);
                }
            }
        };

        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Dynamically set the panel size based on heap size
        int depth = (int) Math.ceil(Math.log(heap.size() + 1) / Math.log(2));
        int width = Math.max((int) Math.pow(2, depth) * 50, 800);
        int height = depth * 100;
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(scrollPanel);
        frame.setVisible(true);
    }

    // Recursive function to draw nodes and edges
    private void drawNode(Graphics g, int index, int x, int y, int xOffset) {
        if (index >= heap.size()) return;

        // Draw current node
        g.setColor(Color.BLUE);
        g.fillOval(x - 15, y - 15, 30, 30);
        g.setColor(Color.BLACK);
        g.drawString(heap.get(index).data.getGivenName() + " " + heap.get(index).data.getFamilyName(), x - 30, y - 20);

        // Draw left child
        int leftChildIndex = 2 * index + 1;
        if (leftChildIndex < heap.size()) {
            g.setColor(Color.BLACK);
            g.drawLine(x, y, x - xOffset, y + 50);
            drawNode(g, leftChildIndex, x - xOffset, y + 50, xOffset / 2);
        }

        // Draw right child
        int rightChildIndex = 2 * index + 2;
        if (rightChildIndex < heap.size()) {
            g.setColor(Color.BLACK);
            g.drawLine(x, y, x + xOffset, y + 50);
            drawNode(g, rightChildIndex, x + xOffset, y + 50, xOffset / 2);
        }
    }

    // Print the heap as an array (for debugging)
    public void printHeap() {
        for (Node node : heap) {
            System.out.println(node.data.getGivenName() + " " + node.data.getFamilyName());
        }
    }
}
