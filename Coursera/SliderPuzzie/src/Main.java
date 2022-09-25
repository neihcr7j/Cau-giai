import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Main {
  public static void main(String[] args) {

    In in = new In("in.txt");
    int n = in.readInt();
    int[][] tiles = new int[n][n];
    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) tiles[i][j] = in.readInt();
    Board initial = new Board(tiles);
//    Board board = new Board(tiles);

//    System.out.println(initial.twin());
//    System.out.println(initial.twin());
//    System.out.println(initial.twin());
//
//    System.out.println(board.equals(initial));

    Solver solver = new Solver(initial);

    StdOut.println("Minimum number of moves = " + solver.moves());
    System.out.println(solver.solution());
    System.out.println();
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }
}
