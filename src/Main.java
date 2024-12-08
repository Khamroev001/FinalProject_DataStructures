import javax.xml.transform.stream.StreamSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        PeopleRecord[] sorted;
        List<Map.Entry<String, Integer>> mostfrequent;
        DatabaseProcessing databaseProcessor = new DatabaseProcessing();
        databaseProcessor.loadData();
        databaseProcessor.visualizeBST();
        sorted=databaseProcessor.sort();
        databaseProcessor.visualizeHeap();
        try {
            mostfrequent=databaseProcessor.getMostFrequentWords(10,4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (Map.Entry<String, Integer> entry : mostfrequent) {
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
        System.out.println("Sorted ArrayList");

        System.out.println(Arrays.toString(sorted));

    }
}