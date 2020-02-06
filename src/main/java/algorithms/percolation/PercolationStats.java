package algorithms.percolation;

/**
 * @author Cui
 * @create 2020-02-05 13:52
 **/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private final double[] results;
    private final double CONFIDENCE_95 = 1.96;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if(n<=0 || trials<=0){
            throw new IllegalArgumentException("size " + n + " or trials " + trials + "out of bounds");
        }
        results = new double[trials];
        for(int i=0; i<trials; i++){
            results[i] = oneTrial(n);
        }
    }

    private double oneTrial(int n){
        Percolation percolation = new Percolation(n);
        int[] orders = StdRandom.permutation(n*n);
        for (int i=0; i<orders.length; i++) {
            int row = orders[i] / n + 1;
            int col = orders[i] % n + 1;
            percolation.open(row, col);
            if(percolation.percolates()){
                break;
            }
        }
        return (double)percolation.numberOfOpenSites()/(n*n);
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(results.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(results.length));
    }

    // test client (see below)
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, T);
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = "
                + percolationStats.confidenceLo() + ", "
                + percolationStats.confidenceHi());
    }
}
