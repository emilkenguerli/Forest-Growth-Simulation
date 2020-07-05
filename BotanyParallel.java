/**
 Parallel program
*/
import java.io.*;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
public class BotanyParallel {
    static long startTime = 0;
	
    private static void tick() {
        startTime = System.currentTimeMillis();
    }

    private static float tock() {
        return (System.currentTimeMillis() - startTime) / 1000.0f;
    }
    static final ForkJoinPool fjPool = new ForkJoinPool();

    static double[] sum(String[] arr, double[][] arr2) {
        return fjPool.invoke(new MyThread(arr, 0, arr.length, arr2));
    }

    public static void main(String[] args) throws IOException {
	//Getting input data from file
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        ArrayList<String> fileArr = new ArrayList<>();
        String line = br.readLine();

        while (line != null) {
            fileArr.add(line);
            line = br.readLine();
        }
        
        String[] sumArr = new String[Integer.parseInt(fileArr.get(2))];
        
        for (int i = 0; i < sumArr.length; i++) {
            sumArr[i] = fileArr.get(i+3);
            
        }
	//Creates arrays of the cover and sizes data sets

        String cover[] = fileArr.get(1).split(" ");
        String sizes[] = fileArr.get(0).split(" ");

        double[][] cartesion = new double[Integer.parseInt(sizes[0])][Integer.parseInt(sizes[1])];

        //Populates 2D array
        for (int i = 0; i < cartesion.length; i++) {
            for (int j = 0; j < cartesion[i].length; j++) {
                cartesion[i][j] = Double.parseDouble(cover[i * cartesion[i].length + j]);
            }
        }
        //Parallises the tree coverage calculations and is run multiple times to optimise and get the best result
        double[] treeArr = {};
        for (int i = 0; i < 13; i++) {
            tick(); 
            treeArr = sum(sumArr, cartesion);
            float time = tock();       
            System.out.println("Run " + i + " took " + time + " seconds");           
        }
	//Printing out results and also writing output to a file
                
        double total = 0;
	PrintWriter pw = new PrintWriter(args[1], "utf-8");

        for (int i = 0; i < treeArr.length; i++) {
            total += treeArr[i];
        }
        System.out.println(total / Integer.parseInt(fileArr.get(2)));
	pw.println(total/Integer.parseInt(fileArr.get(2)));

        System.out.println(Integer.parseInt(fileArr.get(2)));
	pw.println(Integer.parseInt(fileArr.get(2)));

        for (int i = 0; i < treeArr.length; i++) {
            System.out.println(treeArr[i]);
	    pw.println(treeArr[i]);
        }
	pw.close();
    }
}
