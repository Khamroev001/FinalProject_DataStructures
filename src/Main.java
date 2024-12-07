import javax.xml.transform.stream.StreamSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        String fileName = "C:\\Users\\satto\\IdeaProjects\\FinalProject_DataStructures\\src\\people.txt"; // Path to the file
        MyBST records = new MyBST();
        MyHeap heap = new MyHeap();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Create a PeopleRecord object for each line
                PeopleRecord record = new PeopleRecord(line);
                records.insert(record);
                heap.insert(record);

                // You can also insert the record into BST, Heap, or Hashmap here
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        System.out.println(records.getInfo());
        System.out.println(heap.getInfo());
        heap.drawHeap();
        records.drawTree();
//      records.inorderTraversal();
        

    }
}