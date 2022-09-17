public class Dsu {
  private int cnt;

  private final int[] id;
  private final int[] mask;

  private boolean percolates;

  Dsu(int size) {
    percolates = false;
    cnt = size;
    id = new int[size];
    mask = new int[size];

    for (int i = 0; i < size; ++i) {
      id[i] = i;
    }

    for (int i = 0; i < Math.sqrt(size); ++i) {
      mask[i] |= 1;
      mask[size - i - 1] |= 2;
    }
  }

  public boolean isPercolates() {
    return percolates;
  }

  public int find(int u) {
    if (u == id[u]) {
      return u;
    } else {
      id[u] = find(id[u]);
      return id[u];
    }
  }

  public int getMask(int u) {
    return mask[find(u)];
  }

  public boolean checkComponent(int u, int v) {
    return find(u) != find(v);
  }

  public void union(int u, int v) {
    if (checkComponent(u, v)) {
      cnt--;
      u = find(u);
      v = find(v);
      id[u] = v;
      mask[v] |= mask[u];
    }
  }

  public void open(int u) {
    if (mask[find(u)] == 3) {
      percolates = true;
    }
  }

  public int count() {
    return cnt;
  }


}
