import javax.swing.*;
import java.awt.*;
import java.util.PriorityQueue;

public class MyHeap {

    // Worst case complexity, insert, delete O(logn), heapify O(n)

    private PeopleRecord[] heap; // Array to store the heap elements
    private int size;           // Number of elements in the heap
    private final int capacity; // Maximum capacity of the heap

    public MyHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new PeopleRecord[capacity];
        this.size = 0;
    }

    //double the capacity of the array,
    public void doubleCapacity(){
        PeopleRecord[] temp = heap;

        heap = new PeopleRecord[capacity*2]; // new array with bigger capacity
        //get the values back
        for (int i = 0; i < temp.length; i++) {
            heap[i] = temp[i];
        }
    }


    // Insert a new record into the heap
    public void insert(PeopleRecord record) {
        while (size >= capacity) {
            doubleCapacity();
        }
        heap[size] = record;
        heapify(size, true); // Heapify up for the new element
        size++;
    }

    // Remove and return the maximum element from the heap
    public PeopleRecord removeMax() {
        if (size == 0) {
            return null;
        }
        PeopleRecord max = heap[0];
        heap[0] = heap[size - 1]; // Replace root with the last element
        size--;
        heapify(0, false); // Heapify down from the root
        return max;
    }
    //in case you want to remove some elements
    public PeopleRecord remove(int index) {
        if (size == 0) {
            return null;
        }
        PeopleRecord removed = heap[index];
        heap[index] = heap[size - 1];
        size--;
        heapify(index, false);
        return removed;
    }
    //heapify to maintain heap properties
    private void heapify(int index, boolean isUp) {
        if (isUp) {
            // Heapify up, used when we insert node from bottow to up
            int parentIndex = (index - 1) / 2;
            while (index > 0 && heap[index].compareTo(heap[parentIndex]) > 0) {
                swap(index, parentIndex);
                index = parentIndex;
                parentIndex = (index - 1) / 2;
            }
        } else {
            // Heapify down, used when removing some element to maintain the heap property
            while (true) {
                int leftChild = 2 * index + 1;
                int rightChild = 2 * index + 2;
                int largest = index;

                if (leftChild < size && heap[leftChild].compareTo(heap[largest]) > 0) {
                    largest = leftChild;
                }
                if (rightChild < size && heap[rightChild].compareTo(heap[largest]) > 0) {
                    largest = rightChild;
                }
                if (largest == index) {
                    break;
                }
                swap(index, largest);
                index = largest;
            }
        }
    }

    // Swap two elements in the heap
    private void swap(int index1, int index2) {
        PeopleRecord temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    // Perform heap sort and return sorted list of PeopleRecord
    public PeopleRecord[] heapSort() {
        PeopleRecord[] sortedArray = new PeopleRecord[size];
        int originalSize = size;

        for (int i = 0; i < originalSize; i++) {
            sortedArray[i] = removeMax();
        }

        size = originalSize; // Restore the original size after sorting
        return sortedArray;
    }

    // Get number of nodes and height of the heap
    public String getInfo() {
        int height = (int) Math.ceil(Math.log(size + 1) / Math.log(2)) - 1;
        return "Number of nodes: " + size + ", Height: " + height;
    }

    // Function to draw the heap as a tree
    public void drawHeap() {
        JFrame frame = new JFrame("Heap Visualization");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (size > 0) {
                    drawNode(g, 0, getWidth() / 2, 50, getWidth() / 4);
                }
            }
        };

        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Dynamically set the panel size based on heap size
        int depth = (int) Math.ceil(Math.log(size + 1) / Math.log(2));
        int width = Math.max((int) Math.pow(2, depth) * 50, 800);
        int height = depth * 100;
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(scrollPanel);
        frame.setVisible(true);
    }

    // Recursive function to draw nodes and edges
    private void drawNode(Graphics g, int index, int x, int y, int xOffset) {
        if (index >= size) return;

        // Draw current node
        g.setColor(Color.BLUE);
        g.fillOval(x - 15, y - 15, 30, 30);
        g.setColor(Color.BLACK);
        g.drawString(heap[index].getGivenName() + " " + heap[index].getFamilyName(), x - 30, y - 20);

        // Draw left child
        int leftChildIndex = 2 * index + 1;
        if (leftChildIndex < size) {
            g.setColor(Color.BLACK);
            g.drawLine(x, y, x - xOffset, y + 50);
            drawNode(g, leftChildIndex, x - xOffset, y + 50, xOffset / 2);
        }

        // Draw right child
        int rightChildIndex = 2 * index + 2;
        if (rightChildIndex < size) {
            g.setColor(Color.BLACK);
            g.drawLine(x, y, x + xOffset, y + 50);
            drawNode(g, rightChildIndex, x + xOffset, y + 50, xOffset / 2);
        }
    }

    // Print the heap as an array (for debugging)
    public void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.println(heap[i].getGivenName() + " " + heap[i].getFamilyName());
        }
    }
}
