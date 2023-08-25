import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials;
    private double[] thresholds;
    private static final double CONST = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Error N, Trials, cannot be 0 or below");
        }
        thresholds = new double[trials];
        this.trials = trials;
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniformInt(n) + 1;
                int col = StdRandom.uniformInt(n) + 1;
                perc.open(row, col);
            }
            double threshold = (double) perc.numberOfOpenSites() / (n * n);
            this.thresholds[i] = threshold;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (trials == 1) {
            return Double.NaN;
        }

        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONST * stddev() / Math.sqrt(thresholds.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONST * stddev() / Math.sqrt(thresholds.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "Must provide two arguments: grid size and number of trials");
        }


        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);


        PercolationStats stats = new PercolationStats(n, trials);


        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println(
                "95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi()
                        + "]");


    }


}

