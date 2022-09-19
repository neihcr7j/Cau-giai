public class Percolation {
  private static final int S = 4;
  private final int n;
  private final Dsu dsu;
  private final boolean[][] arr;

  private int cnt = 0;

  private final int[] ii = {1, -1, 0, 0};
  private final int[] jj = {0, 0, -1, 1};

  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }
    this.n = n;
    arr = new boolean[n][n];
    dsu = new Dsu(n * n);
  }

  private int covert(int row, int col) {
    return row * n + col;
  }

  public void open(int row, int col) {
    row--;
    col--;
    if (row < 0 || row >= n || col < 0 || col >= n) {
      throw new IllegalArgumentException();
    }

    if (!arr[row][col]) {
      arr[row][col] = true;
      cnt++;

      if (row == 0) {
        dsu.union(dsu.getTopNode(), covert(row, col));
      }

      for (int i = 0; i < S; ++i) {
        if (row + ii[i] >= 0 && row + ii[i] < n && col + jj[i] >= 0 && col + jj[i] < n) {
          if (arr[row + ii[i]][col + jj[i]]) {
            dsu.union(covert(row, col), covert(row + ii[i], col + jj[i]));
          }
        }
      }

      dsu.open(covert(row, col));
    }
  }

  public boolean isOpen(int row, int col) {
    row--;
    col--;
    if (row < 0 || row >= n || col < 0 || col >= n) {
      throw new IllegalArgumentException();
    }

    return arr[row][col];
  }

  public boolean isFull(int row, int col) {
    row--;
    col--;
    if (row < 0 || row >= n || col < 0 || col >= n) {
      throw new IllegalArgumentException();
    }
    return arr[row][col] && dsu.isLinkedTop(covert(row, col));
  }

  public int numberOfOpenSites() {
    return cnt;
  }

  public boolean percolates() {
    return dsu.isPercolates();
  }

  public static void main(String[] args) {
    Percolation percolation = new Percolation(3);
    percolation.open(1, 1);
    percolation.open(2, 1);
    percolation.open(2, 2);

    System.out.println(percolation.percolates());
  }
}
