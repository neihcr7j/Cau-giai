import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class PointSET {
  private SET<Point2D> set;

  public PointSET() {
    set = new SET<>();
  }

  public boolean isEmpty() {
    return set.isEmpty();
  }

  public int size() {
    return set.size();
  }

  public void insert(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    set.add(p);
  }

  public boolean contains(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    return set.contains(p);
  }

  public void draw() {
    return;
  }

  private List<Point2D> getList(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException();
    }

    List<Point2D> iter = new ArrayList<>();

    for (Point2D point : set) {
      if (rect.contains(point)) {
        iter.add(point);
      }
    }

    return iter;
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException();
    }

    return new Iterable<Point2D>() {
      @Override
      public Iterator<Point2D> iterator() {
        return getList(rect).iterator();
      }
    };
  }

  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }

    Point2D ansPoint = null;

    for (Point2D point : set) {
      if (ansPoint == null || (ansPoint != null && p.distanceTo(ansPoint) > p.distanceTo(point))) {
        ansPoint = point;
      }
    }

    return ansPoint;
  }

  public static void main(String[] args) {
    PointSET pointSET = new PointSET();

    pointSET.insert(new Point2D(0, 0));
    pointSET.insert(new Point2D(0, 1));
    pointSET.insert(new Point2D(0, 2));
    pointSET.insert(new Point2D(0, 3));
    pointSET.insert(new Point2D(0, 4));
    pointSET.insert(new Point2D(0, 5));

    System.out.println("Point Set contain : " + pointSET.contains(new Point2D(0, 3)));

    for (Point2D point2D : pointSET.range(new RectHV(0, 0, 3, 3))) {
      System.out.println(point2D);
    }

    System.out.println();

    System.out.println(pointSET.nearest(new Point2D(3, 3)));
  }
}
