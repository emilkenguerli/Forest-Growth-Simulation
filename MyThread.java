/**
 The Thread class
*/
import java.lang.reflect.Array;
import java.util.concurrent.RecursiveTask;
public class MyThread extends RecursiveTask<double[]> {

    int lo; // arguments
    int hi;
    String[] arr;
    double[][] arr2;
    static final int SEQUENTIAL_CUTOFF = 150000;

    MyThread(String[] a, int l, int h, double[][] a2) {
        lo = l;
        hi = h;
        arr = a;
        arr2 =a2;
    }

    @Override
    protected double[] compute() {// return answer - instead of run
        if ((hi - lo) < SEQUENTIAL_CUTOFF) {
            double[] ans = new double[arr.length];
            for (int i = lo; i < hi; i++) {
                ans[i] = coverCalc(i);
            }
            return ans;
        } else {
            MyThread left = new MyThread(arr, lo, (hi + lo) / 2, arr2);
            MyThread right = new MyThread(arr, (hi + lo) / 2, hi, arr2);
            left.fork();
            double[] rightAns = right.compute();
            double[] leftAns = left.join();
            return combine(leftAns, rightAns);
        }
    }
    /** Combines 2 int arrays into 1
        * @param a is the first array on the left side, b is the second array on the right side
	* @return a and b combined as a single int[] array
        */

    public double[] combine(double[] a, double[] b) {
        int length = a.length + b.length;
        double[] result = new double[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    /** Calculates the coverage of the tree
        * @param num is the number of the line where the tree data is found in the arr array
	* @return the total coverage of the tree as an int
        */

    public double coverCalc(int num) {
        double count = 0;

        String[] sTree = arr[num].split(" ");
        int[] tree = new int[sTree.length];

        for (int k = 0; k < sTree.length; k++) {
            tree[k] = Integer.parseInt(sTree[k]);
        }

        int maxRow = tree[0] + tree[2] - 1;
        int maxColumn = tree[1] + tree[2] - 1;

        if (tree[0] > arr2.length - 1 || tree[1] > arr2[0].length - 1) {
            return count;
        }

        if (maxRow > arr2.length - 1) {
            for (int row = tree[0]; row < arr2.length; row++) {
                if (maxColumn > arr2[0].length - 1) {
                    for (int column = tree[1]; column < arr2[0].length; column++) {
                        count += arr2[row][column];
                    }
                } else {
                    for (int column = tree[1]; column <= maxColumn; column++) {
                        count += arr2[row][column];
                    }
                }
            }
        } else {
            for (int row = tree[0]; row < tree[0] + tree[2]; row++) {
                if (maxColumn > arr2[0].length - 1) {
                    for (int column = tree[1]; column < arr2[0].length; column++) {
                        count += arr2[row][column];
                    }
                } else {
                    for (int column = tree[1]; column <= maxColumn; column++) {
                        count += arr2[row][column];
                    }
                }
            }
        }
        return count;
    }

}
