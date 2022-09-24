import javax.swing.text.Segment;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    try {
      Scanner in = new Scanner(System.in);
      int n = in.nextInt();
      Point[] a = new Point[n];

      for (int i = 0; i < n; ++i) {
        int x = in.nextInt();
        int y = in.nextInt();

        a[i] = new Point(x, y);
      }

      FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(a);
      LineSegment[] lineSegments = fastCollinearPoints.segments();

      for (LineSegment s : lineSegments) System.out.println(s);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
