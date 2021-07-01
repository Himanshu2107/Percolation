/* *************************************************************************************************
 *  Compilation : javac PercolationStats.java
 *  Execution : java PercolationStats n trials
 *  Dependencies : Percolation.java
 *
 *  This program creates a new data type that performs a number of MonteCarlo simulation to
 *  estimate the threshold probability p*. It uses the Percolation data type to model the sites.
 *  Takes command line arguments 'n' and 'trials' to perform 'trials' number of trials on an n-by-n
 *  percolation grid. The output is the mean value of p* of all the experiments, along with the
 *  standard deviation and the 95% confidence level.
 * ********************************************************************************************** */


import java.util.Random;
import java.lang.Math;

public class PercolationStats {

    // saves the user provided number of trials for later use
    private final int trials;
    // saves magic number 1.96
    private static final double CONFIDENCE_95 = 1.96;
    // mean value after experiment
    private final double mean;
    // standard deviation
    private final double stddev;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) throw new IllegalArgumentException("Illegal Value");

        // saves the array of values at whcih system percolates
        double[] values = new double[trials];
        this.trials = trials;

        Random rand = new Random();
        for (int i = 0; i < trials; i++) {
            // creates a grid to perform the experiment on
            Percolation grid = new Percolation(n);
            while (!grid.percolates()) {
                int r1 = rand.nextInt(n) + 1;
                int r2 = rand.nextInt(n) + 1;
                grid.open(r1, r2);
            }
            values[i] = (double) grid.numberOfOpenSites() / (n * n);
        }

        mean = findMean(values);
        stddev = findStddev(values);
    }

    // get the mean of the input array
    private double findMean(double[] nums) {
        double total = 0;
        for (double d : nums) total += d;
        return (total / nums.length);
    }

    // get the standard deviation of the input array
    private double findStddev(double[] values) {
        double mean = findMean(values);
        double sum = 0.0;
        for (double d : values) {
            double sq_dev = (d - mean) * (d - mean);
            sum += sq_dev;
        }
        double variance = sum / values.length;
        return Math.sqrt(variance);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }


    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return ((this.mean) - (CONFIDENCE_95 * this.stddev / Math.sqrt(trials)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return ((this.mean) + (CONFIDENCE_95 * this.stddev / Math.sqrt(trials)));
    }

    // test client
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats a = new PercolationStats(n, trials);
        System.out.println("mean             \t= " + a.mean());
        System.out.println("stddev                 \t= " + a.stddev());
        System.out.println("95% confidence interval\t= [" + a.confidenceLo() + ", " + a.confidenceHi() + "]");
    }

}
