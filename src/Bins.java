import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * Runs a number of algorithms that try to fit files onto disks.
 */
public class Bins {
    public static final String DATA_FILE = "example.txt";

    /**
     * Reads list of integer data from the given input.
     *
     * @param input tied to an input source that contains space separated numbers
     * @return list of the numbers in the order they were read
     */
    public List<Integer> readData (Scanner input) {
        List<Integer> results = new ArrayList<Integer>();
        while (input.hasNext()) {
            results.add(input.nextInt());
        }
        return results;
    }

    /**
     * The main program.
     */
    public static void main (String args[]) {
        Bins b = new Bins();
        b.go();     
    }
    /**
     * 
     */
    public void go(){
    	 Scanner input = new Scanner(Bins.class.getClassLoader().getResourceAsStream(DATA_FILE));
    	 List<Integer> data = this.readData(input);
         PriorityQueue<Disk> pq = new PriorityQueue<Disk>();
         worstFitDecreasing(pq, data);
         worstFit(pq, data);
         Integer [] nums = {1, 2, 3, 4, 5, 6, 7, 8};
         List<Integer> toTransform = Arrays.asList(nums);
         fitDisksAndPrint(toTransform, (in) -> in.stream()
        		 .filter((elem) -> elem%2 == 0).collect(Collectors.toList()));
         
    }
    
    public void fitDisksAndPrint(List<Integer> toTransform, Function<List<Integer>, List<Integer>> func){
    	List<Integer> transformed = func.apply(toTransform);
    	transformed.forEach(System.out::println);
    	
    }

   public void worstFitDecreasing(PriorityQueue<Disk> pq, List<Integer> data){
	   Collections.sort(data, Collections.reverseOrder());
       pq.add(new Disk(0));

       int diskId = 1;
       for (Integer size : data) {
           Disk d = pq.peek();
           if (d.freeSpace() >= size) {
               pq.poll();
               d.add(size);
               pq.add(d);
           } else {
               Disk d2 = new Disk(diskId);
               diskId++;
               d2.add(size);
               pq.add(d2);
           }
       }

       System.out.println();
       System.out.println("worst-fit decreasing method");
       System.out.println("number of pq used: " + pq.size());
       while (!pq.isEmpty()) {
           System.out.println(pq.poll());
       }
       System.out.println();
   }
    
   public void worstFit (PriorityQueue<Disk> pq, List<Integer> data ) {
	 //algo
      
       pq.add(new Disk(0));

       int diskId = 1;
       int total = 0;
       for (Integer size : data) {
           Disk d = pq.peek();
           if (d.freeSpace() > size) {
               pq.poll();
               d.add(size);
               pq.add(d);
           } else {
               Disk d2 = new Disk(diskId);
               diskId++;
               d2.add(size);
               pq.add(d2);
           }
           total += size;
       }

       System.out.println("total size = " + total / 1000000.0 + "GB");
       System.out.println();
       System.out.println("worst-fit method");
       System.out.println("number of pq used: " + pq.size());
       while (!pq.isEmpty()) {
           System.out.println(pq.poll());
       }
       System.out.println();
   }
    
}
