/**
 Sequential program
*/
import java.io.*;
import java.util.*;
public class BotanySequential {
    //Methods for testing time
    static long startTime = 0;

    private static void tick(){
	startTime = System.currentTimeMillis();
    }

    private static float tock(){
	return (System.currentTimeMillis() - startTime) / 1000.0f;
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
        
        double[] sum = new double[Integer.parseInt(fileArr.get(2))];

	//Sums the total coverage of each tree and stores it in sum array
	tick();
        for (int i = 0; i < Integer.parseInt(fileArr.get(2)); i++) {
            String[] sTree = fileArr.get(3 + i).split(" ");
            int[] tree = new int[sTree.length];

            for (int k = 0; k < sTree.length; k++) {
                tree[k] = Integer.parseInt(sTree[k]);
            }

            int maxRow = tree[0] + tree[2] - 1;
            int maxColumn = tree[1] + tree[2] - 1;

            if (tree[0] > cartesion.length - 1 || tree[1] > cartesion[0].length - 1) {
                continue;
            }
            

            if (maxRow > cartesion.length - 1) {
                for (int row = tree[0]; row < cartesion.length; row++) {
                    if (maxColumn > cartesion[0].length - 1) {
                        for (int column = tree[1]; column < cartesion[0].length; column++) {
                            sum[i] += cartesion[row][column];
                        }
                    } else {
                        for (int column = tree[1]; column <= maxColumn; column++) {
                            sum[i] += cartesion[row][column];
                        }
                    }
                }
            } else {
                for (int row = tree[0]; row < tree[0] + tree[2]; row++) {
                    if (maxColumn > cartesion[0].length - 1) {
                        for (int column = tree[1]; column < cartesion[0].length; column++) {
                            sum[i] += cartesion[row][column];
                        }
                    } else {
                        for (int column = tree[1]; column <= maxColumn; column++) {
                            sum[i] += cartesion[row][column];
                        }
                    }
                }
            }
        }
	float time = tock();
	System.out.println("Run took " + time + " seconds");
	//Printing out results and also writing output to a file

        double total = 0;
	PrintWriter pw = new PrintWriter(args[1], "utf-8");
           
        for (int i = 0; i < sum.length; i++) {
            total += sum[i];
        }
        System.out.println(total/Integer.parseInt(fileArr.get(2)));
	pw.println(total/Integer.parseInt(fileArr.get(2)));
        
        System.out.println(Integer.parseInt(fileArr.get(2)));
	pw.println(Integer.parseInt(fileArr.get(2)));
               
        for (int i = 0; i < sum.length; i++) {
            System.out.println(sum[i]);
	    pw.println(sum[i]);    
        }  

	pw.close();
    
    }
}
