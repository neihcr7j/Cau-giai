import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BruteCollinearPoints {
  private Point[] points;
  private List<LineSegment> list;

  private void process(Point[] points) {
    Arrays.sort(points, new Comparator<Point>() {
      @Override
      public int compare(Point o1, Point o2) {
        return o1.compareTo(o2);
      }
    });

    for (int i = 0; i < points.length; ++i) {
      for (int j = i + 1; j < points.length; ++j) {
        for (int k = j + 1; k < points.length; ++k) {
          if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k])) {
            continue;
          }

          for (int t = k + 1; t < points.length; ++t) {
            if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[t])) {
              list.add(new LineSegment(points[i], points[t]));
            }
          }
        }
      }
    }
  }

  public BruteCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }

    this.points = new Point[points.length];

    for (int i = 0; i < points.length; ++i) {
      this.points[i] = points[i];
    }

    list = new ArrayList<LineSegment>();

    for (int i = 0; i < this.points.length; ++i) {
      if (this.points[i] == null) {
        throw new IllegalArgumentException();
      }
    }

    for (int i = 0; i < this.points.length; ++i) {
      for (int j = i + 1; j < this.points.length; ++j) {
        if (points[i].compareTo(this.points[j]) == 0) {
          throw new IllegalArgumentException();
        }
      }
    }

    process(this.points);
  }

  public int numberOfSegments() {
    return list.size();
  }

  public LineSegment[] segments() {
    return list.toArray(new LineSegment[list.size()]);
  }

  public static void main(String[] args) {
    Point[] a = new Point[7];
    a[0] = new Point(10, 0);
    a[1] = new Point(0, 10);
    a[2] = new Point(3, 7);
    a[3] = new Point(7, 3);
    a[4] = new Point(20, 21);
    a[5] = new Point(3, 4);
    a[6] = new Point(14, 15);



    BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(a);
    LineSegment[] lineSegments = bruteCollinearPoints.segments();

    for (LineSegment s : lineSegments)
      System.out.println(s);
  }
}
