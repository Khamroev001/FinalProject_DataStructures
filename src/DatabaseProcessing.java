import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class DatabaseProcessing {

    // Path to the file, you may change it on different PC
    final String fileName = "C:\\Users\\satto\\IdeaProjects\\FinalProject_DataStructures\\src\\people.txt";
    MyBST myBST= new MyBST();
    MyHeap myHeap= new MyHeap();


    public void loadData(){
        try (
            BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Create a PeopleRecord object for each line
                PeopleRecord record = new PeopleRecord(line);
                myBST.insert(record);
                // You can also insert the record into BST, Heap, or Hashmap here
            }
        } catch (
                IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public PeopleRecord search(String givenName, String familyName){
        return myBST.search(givenName, familyName);
    }

    private void BSTtoHeap(MyBST.Node node, MyHeap myHeap) {
        if (node == null) return;

        BSTtoHeap(node.left, myHeap);
        myHeap.insert(node.data);
        BSTtoHeap(node.right, myHeap);
    }

    public ArrayList<PeopleRecord> sort(){
        BSTtoHeap(myBST.getRoot(),myHeap);
        return myHeap.heapSort();
    }

    


}
