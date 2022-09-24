import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {
  private Point[] points;
  private List<LineSegment> list;

  private void process(Point[] points) {
    Point[] a = new Point[points.length - 1];

    for (Point point : points) {
      int sz = 0;

      for (Point x : points) {
        if (x.compareTo(point) != 0) {
          a[sz++] = x;
        }
      }

      Arrays.sort(a, point.slopeOrder());

      for (int i = 0; i < sz;) {
        int j = i;
        while (j < sz && point.slopeTo(a[i]) == point.slopeTo(a[j])) {
          j++;
        }

        Point maxPoint = a[i];
        boolean check = true;

        for (int k = i; k < j; ++k) {
          if (maxPoint.compareTo(a[k]) < 0) {
            maxPoint = a[k];
          }

          if (point.compareTo(a[k]) >= 0) {
            check = false;
          }
        }

        if (check && j - i > 2) {
          list.add(new LineSegment(point, maxPoint));
        }

        i = j;
      }
    }
  }

  public FastCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }

    this.points = new Point[points.length];

    for (int i = 0; i < points.length; ++i) {
      this.points[i] = points[i];
    }

    list = new ArrayList<LineSegment>();

    for (int i = 0; i < points.length; ++i) {
      if (this.points[i] == null) {
        throw new IllegalArgumentException();
      }
    }

    for (int i = 0; i < points.length; ++i) {
      for (int j = i + 1; j < points.length; ++j) {
        if (this.points[i].compareTo(this.points[j]) == 0) {
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
}
