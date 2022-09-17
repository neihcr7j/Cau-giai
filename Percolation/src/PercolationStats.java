import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private final int trials;

  private final double[] list;
  private static final double c = 1.96;

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
    return mean() - c * stddev() / Math.sqrt(trials);
  }

  public double confidenceHi() {
    return mean() + c * stddev() / Math.sqrt(trials);
  }

  public static void main(String[] args) {
    PercolationStats percolationStats = new PercolationStats(2, 100000);

    System.out.println(percolationStats.mean());
    System.out.println(percolationStats.stddev());
    System.out.println(percolationStats.confidenceLo());
    System.out.println(percolationStats.confidenceHi());
  }
}
