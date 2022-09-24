import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Dsu {
  private final WeightedQuickUnionUF weightedQuickUnionUF;
  private final boolean[] mask;

  private int cnt;

  private final int topNode;

  private boolean percolates;

  Dsu(int size) {
    weightedQuickUnionUF = new WeightedQuickUnionUF(size + 1);
    percolates = false;
    mask = new boolean[size + 1];
    topNode = size;
    cnt = size;

    for (int i = 0; i < Math.sqrt(size); ++i) {
      mask[size - i - 1] = true;
    }
  }

  public int getTopNode() {
    return topNode;
  }

  public boolean isLinkedTop(int u) {
    return weightedQuickUnionUF.find(u) == weightedQuickUnionUF.find(topNode);
  }

  public boolean isPercolates() {
    return percolates;
  }

  public boolean getMask(int u) {
    return mask[weightedQuickUnionUF.find(u)];
  }

  public void union(int u, int v) {
    if (weightedQuickUnionUF.find(u) != weightedQuickUnionUF.find(v)) {
      boolean check = mask[weightedQuickUnionUF.find(u)] | mask[weightedQuickUnionUF.find(v)];
      cnt--;
      weightedQuickUnionUF.union(u, v);
      mask[weightedQuickUnionUF.find(u)] = check;
    }
  }

  public void open(int u) {
    if (getMask(u) && isLinkedTop(u)) {
      percolates = true;
    }
  }

  public int count() {
    return cnt;
  }
}
