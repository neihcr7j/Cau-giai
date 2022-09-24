import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.Iterator;

public class Solver {
  private TreeBoard target;
  private Stack<Board> stack;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    MinPQ<TreeBoard> boardMinPQ = new MinPQ<>();

    boardMinPQ.insert(new TreeBoard(initial, null));

    int cur = 0;
    while (true) {
      target = boardMinPQ.delMin();

      cur++;

      if ((target != null && target.board.isGoal()) || cur == 100000) {
        break;
      }

      for (Board neighborBoard : target.board.neighbors()) {
        boardMinPQ.insert(new TreeBoard(neighborBoard, target));
      }
    }

    stack = new Stack<>();

    if (target.board.isGoal()) {
      trace(target);
    }
  }

  private void trace(TreeBoard node) {
    if (node == null) return;
    trace(node.prevBoard);
    stack.push(node.board);
  }

  // is the initial board solvable? (see below)
  public boolean isSolvable() {
    return target.board.isGoal();
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    return target.numberOfMoveStep;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    return new Iterable<Board>() {
      @Override
      public Iterator<Board> iterator() {
        return stack.iterator();
      }
    };
  }

  private class TreeBoard implements Comparable<TreeBoard> {
    Board board;
    TreeBoard prevBoard;

    int numberOfMoveStep;

    TreeBoard(Board board, TreeBoard prevBoard) {
      this.board = board;
      this.prevBoard = prevBoard;
      if (prevBoard == null) {
        numberOfMoveStep = 1;
      } else {
        numberOfMoveStep = prevBoard.numberOfMoveStep + 1;
      }
    }

    @Override
    public int compareTo(TreeBoard o) {
      if (board.manhattan() + numberOfMoveStep == o.board.manhattan() + o.numberOfMoveStep) {
        return Integer.compare(numberOfMoveStep, o.numberOfMoveStep);
      } else {
        return Integer.compare(
            board.manhattan() + numberOfMoveStep, o.board.manhattan() + o.numberOfMoveStep);
      }
    }
  }

  // test client (see below)
  public static void main(String[] args) {}
}
