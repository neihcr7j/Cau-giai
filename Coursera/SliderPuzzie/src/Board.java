import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
  private int[][] tiles;

  private int n;

  private int distManhattan;

  public Board(int[][] tiles) {
    n = tiles.length;
    this.tiles = new int[n][n];
    distManhattan = -1;

    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        this.tiles[i][j] = tiles[i][j];
      }
    }
  }

  // string representation of this board
  @Override
  public String toString() {
    String s = Integer.toString(n);
    for (int i = 0; i < n; ++i) {
      s += "\n";
      for (int j = 0; j < n; ++j) {
        s += tiles[i][j] + " ";
      }
    }
    return s;
  }

  // board dimension n
  public int dimension() {
    return n;
  }

  // number of tiles out of place
  public int hamming() {
    int cur = 0;

    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        if (tiles[i][j] != i * n + j + 1) {
          cur++;
        }
      }
    }

    return cur - 1;
  }

  // sum of Manhattan distances between tiles and goal
  public int manhattan() {
    if (distManhattan != -1) {
      return distManhattan;
    }

    distManhattan = 0;

    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        if (tiles[i][j] != 0) {
          distManhattan += Math.abs(i - (tiles[i][j] - 1) / n) + Math.abs(j - (tiles[i][j] - 1) % n);
        }
      }
    }

    return distManhattan;
  }

  // is this board the goal board?
  public boolean isGoal() {
    return hamming() == 0;
  }

  // does this board equal y?
  @Override
  public boolean equals(Object y) {
    if (y instanceof Board) {
      if (n != ((Board) y).n) {
        return false;
      }

      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
          if (tiles[i][j] != ((Board) y).tiles[i][j]) {
            return false;
          }
        }
      }
      return true;
    } else {
      return false;
    }
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    return new Iterable<Board>() {
      @Override
      public Iterator<Board> iterator() {

        return new IteratorBorad();
      }
    };
  }

  private class IteratorBorad implements Iterator<Board> {
    int x;
    int y;

    int index;

    List<Board> list;

    final int[] ii = {-1, 0, 1, 0};
    final int[] jj = {0, 1, 0, -1};

    IteratorBorad() {
      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
          if (tiles[i][j] == 0) {
            x = i;
            y = j;
          }
        }
      }

      index = 0;
      list = new ArrayList<>();

      for (int i = 0; i < 4; ++i) {
        if (x + ii[i] >= 0 && x + ii[i] < n && y + jj[i] >= 0 && y + jj[i] < n) {
          list.add(swap(x, y, x + ii[i], y + jj[i]));
        }
      }
    }

    @Override
    public boolean hasNext() {
      if (index < list.size()) {
        return true;
      } else {
        return false;
      }
    }

    @Override
    public Board next() {
      return list.get(index++);
    }
  }

  private Board swap(int a, int b, int x, int y) {
    Board newBoard = new Board(tiles);

    newBoard.tiles[x][y] += newBoard.tiles[a][b];
    newBoard.tiles[a][b] = newBoard.tiles[x][y] - newBoard.tiles[a][b];
    newBoard.tiles[x][y] = newBoard.tiles[x][y] - newBoard.tiles[a][b];

    return newBoard;
  }

  // a board that is obtained by exchanging any pair of tiles
  public Board twin() {
    int a = StdRandom.uniformInt(n);
    int b = StdRandom.uniformInt(n);

    int x = a;
    int y = b;

    if (StdRandom.uniformInt(2) == 0) {
      if (a == 0) {
        a++;
      } else if (a == n - 1) {
        a--;
      } else {
        if (StdRandom.uniformInt(2) == 0) {
          a++;
        } else {
          a--;
        }
      }
    } else {
      if (b == 0) {
        b++;
      } else if (b == n - 1) {
        b--;
      } else {
        if (StdRandom.uniformInt(2) == 0) {
          b++;
        } else {
          b--;
        }
      }
    }

    return swap(a, b, x, y);
  }

  // unit testing (not graded)
  public static void main(String[] args) {

  }
}
