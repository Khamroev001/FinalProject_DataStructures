import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseProcessing {

    // Path to the file, you may change it on different PC
    final String fileName = "C:\\Users\\satto\\IdeaProjects\\FinalProject_DataStructures\\src\\people.txt";
    MyBST myBST= new MyBST();
    MyHeap myHeap= new MyHeap(500);

    public MyBST getMyBST() {
        return myBST;
    }

    public MyHeap getMyHeap() {
        return myHeap;
    }
    public void visualizeBST(){
        myBST.drawTree();
    }
    public void visualizeHeap(){
        myHeap.drawHeap();
    }
    public String getInfoBST(){
       return myBST.getInfo();
    }
    public String getInfoHeap(){
        return myHeap.getInfo();
    }

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

    //call the heapsort
    public PeopleRecord[] sort(){
        BSTtoHeap(myBST.getRoot(),myHeap);
        return myHeap.heapSort();
    }

    public List<Map.Entry<String, Integer>> getMostFrequentWords(int count, int len) throws Exception {
        if (len < 3) {
            throw new Exception("Error: The minimum word length must be at least 3.");
        }

        MyHashmap<String, Integer> wordFrequencyMap = new MyHashmap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            Pattern pattern = Pattern.compile("[a-zA-Z]{3,}"); // Matches words with 3 or more letters

            while ((line = br.readLine()) != null) {
                // Extract the required fields (assuming fields are separated by ';')
                String[] fields = line.split(";");
                for (int i = 0; i <= 6; i++) { // Process the first 7 fields
                    if (i < fields.length) { // Ensure the field exists
                        Matcher matcher = pattern.matcher(fields[i]);
                        while (matcher.find()) {
                            String word = matcher.group().toLowerCase(); // Normalize to lowercase
                            wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        // Collect and sort entries
        List<Map.Entry<String, Integer>> wordList = wordFrequencyMap.entrySet();
        wordList.removeIf(entry -> entry.getKey().length() < len); // Filter by word length
        wordList.sort((a, b) -> b.getValue() - a.getValue()); // Sort by frequency in descending order

        // Return the top 'count' most frequent words
        return wordList.subList(0, Math.min(count, wordList.size()));
    }



}
