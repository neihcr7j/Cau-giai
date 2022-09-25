import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.Iterator;

public class Solver {
  private TreeBoard target;
  private Stack<Board> stack;

  private boolean ok;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    if (initial == null) {
      throw new IllegalArgumentException();
    }

    MinPQ<TreeBoard> boardMinPQ = new MinPQ<>();

    boardMinPQ.insert(new TreeBoard(initial, null));

    for (int cur = 0; cur < initial.dimension() * initial.dimension() * 40000; ++cur) {
      target = boardMinPQ.delMin();

      if (target != null && target.board.isGoal()) {
        break;
      }

      for (Board neighborBoard : target.board.neighbors()) {
        if (target.prevBoard == null
            || (target.prevBoard != null && !neighborBoard.equals(target.prevBoard.board))) {
          boardMinPQ.insert(new TreeBoard(neighborBoard, target));
        }
      }
    }

    stack = new Stack<>();

    if (target.board.isGoal()) {
      trace(target);
    }

    ok = target.board.isGoal();
  }

  private void trace(TreeBoard node) {
    if (node == null) return;
    stack.push(node.board);
    trace(node.prevBoard);
  }

  // is the initial board solvable? (see below)
  public boolean isSolvable() {
    return ok;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    if (!isSolvable()) {
      return -1;
    } else {
      return target.numberOfMoveStep;
    }
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    if (!isSolvable()) {
      return null;
    } else {
      return new Iterable<Board>() {
        @Override
        public Iterator<Board> iterator() {
          return stack.iterator();
        }
      };
    }
  }

  private class TreeBoard implements Comparable<TreeBoard> {
    Board board;
    TreeBoard prevBoard;

    int heuristics;

    int numberOfMoveStep;

    TreeBoard(Board board, TreeBoard prevBoard) {
      this.board = board;
      this.prevBoard = prevBoard;
      if (prevBoard == null) {
        numberOfMoveStep = 0;
      } else {
        numberOfMoveStep = prevBoard.numberOfMoveStep + 1;
      }
      heuristics = board.manhattan() + numberOfMoveStep;
    }

    @Override
    public int compareTo(TreeBoard o) {
      return heuristics - o.heuristics;
    }
  }

  // test client (see below)
  public static void main(String[] args) {}
}
