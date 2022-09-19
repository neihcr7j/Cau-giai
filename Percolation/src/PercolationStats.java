import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private static final double C = 1.96;
  private final int trials;
  private final double[] list;

  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }

    this.trials = trials;

    int[] a = new int[n * n];

    list = new double[trials];

    for (int i = 0; i < n * n; ++i) {
      a[i] = i;
    }

    for (int i = 0; i < trials; ++i) {
      StdRandom.shuffle(a);
      Percolation percolation = new Percolation(n);

      int j = 0;
      while (!percolation.percolates()) {
        percolation.open(a[j] / n + 1, a[j] % n + 1);
        j++;
      }

      list[i] = (double) j / (double) (n * n);
    }
  }

  public double mean() {
    return StdStats.mean(list);
  }

  public double stddev() {
    return StdStats.stddev(list);
  }

  public double confidenceLo() {
    return mean() - C * stddev() / Math.sqrt(trials);
  }

  public double confidenceHi() {
    return mean() + C * stddev() / Math.sqrt(trials);
  }

  public static void main(String[] args) {
    int n = 2;
    int t = 100000;
    if (args.length >= 2) {
      n = Integer.parseInt(args[0]);
      t = Integer.parseInt(args[1]);
    }
    PercolationStats percolationStats = new PercolationStats(n, t);

    StdOut.println("mean                    = " + percolationStats.mean());
    StdOut.println("stddev                  = " + percolationStats.stddev());
    StdOut.println(
        "95% confidence interval = ["
            + percolationStats.confidenceLo()
            + ", "
            + percolationStats.confidenceHi()
            + "]");
  }
}
