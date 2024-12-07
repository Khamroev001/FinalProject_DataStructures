import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class MyHeap {
    private ArrayList<PeopleRecord> heap;

    public MyHeap() {
        heap = new ArrayList<>();
    }

    // Insert a new record into the heap
    public void insert(PeopleRecord record) {
        heap.add(record);
        heapifyUp(heap.size() - 1);
    }

    // Heapify up to maintain heap properties
    private void heapifyUp(int index) {
        int parentIndex = (index - 1) / 2;
        while (index > 0 && heap.get(index).compareTo(heap.get(parentIndex)) > 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    // Swap two elements in the heap
    private void swap(int index1, int index2) {
        PeopleRecord temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
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
        g.drawString(heap.get(index).getGivenName() + " " + heap.get(index).getFamilyName(), x - 30, y - 20);

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
        for (PeopleRecord record : heap) {
            System.out.println(record.getGivenName() + " " + record.getFamilyName());
        }
    }
}
