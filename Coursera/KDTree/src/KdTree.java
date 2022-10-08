import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KdTree {
  private Point2D minDist;
  private List<Point2D> temp;

  private class Node {
    Point2D point;

    RectHV rect;
    Node left;
    Node right;
    boolean isRed;

    Node(Point2D point, RectHV rect, boolean isRed) {
      this.point = point;
      this.rect = rect;
      this.isRed = isRed;
      this.left = null;
      this.right = null;
    }

    RectHV getRectLeft() {
      if (isRed) {
        return new RectHV(rect.xmin(), rect.ymin(), point.x(), rect.ymax());
      } else {
        return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), point.y());
      }
    }

    RectHV getRectRight() {
      if (isRed) {
        return new RectHV(point.x(), rect.ymin(), rect.xmax(), rect.ymax());
      } else {
        return new RectHV(rect.xmin(), point.y(), rect.xmax(), rect.ymax());
      }
    }

    void insertLeft(Point2D point) {
      if (left == null) {
        left = new Node(point, getRectLeft(), !isRed);
        sz++;
      } else {
        left.insert(point);
      }
    }

    void insertRight(Point2D point) {
      if (right == null) {
        right = new Node(point, getRectRight(), !isRed);
        sz++;
      } else {
        right.insert(point);
      }
    }

    void insert(Point2D point) {
      if (this.point.equals(point)) {
        return;
      }

      if ((isRed && point.x() <= this.point.x()) || (!isRed && point.y() <= this.point.y())) {
        insertLeft(point);
      } else {
        insertRight(point);
      }
    }

    boolean contains(Point2D point) {
      if (this.point.equals(point)) {
        return true;
      } else {
        if ((isRed && point.x() <= this.point.x()) || (!isRed && point.y() <= this.point.y())) {
          return (left != null && left.contains(point));
        } else {
          return (right != null && right.contains(point));
        }
      }
    }

    void getList(RectHV rect) {
      if (rect.contains(point)) {
        temp.add(point);
      }

      if (left != null && rect.intersects(left.rect)) {
        left.getList(rect);
      }
      if (right != null && rect.intersects(right.rect)) {
        right.getList(rect);
      }
    }

    void findNearest(Point2D p) {
      if (rect.distanceTo(p) >= p.distanceTo(minDist)) {
        return;
      }

      if (p.distanceTo(point) < p.distanceTo(minDist)) {
        minDist = point;
      }

      if (left == null && right == null) {
        return;
      }

      if (left == null) {
        right.findNearest(p);
      } else if (right == null) {
        left.findNearest(p);
      } else {
        if ((isRed && p.x() <= point.x()) || (!isRed && p.y() <= point.y())) {
          left.findNearest(p);
          right.findNearest(p);
        } else {
          right.findNearest(p);
          left.findNearest(p);
        }
      }
    }

    Point2D nearest(Point2D p) {
      minDist = point;
      findNearest(p);
      return minDist;
    }
  }

  private Node root;
  private int sz;

  public KdTree() {
    root = null;
    sz = 0;
  }

  public boolean isEmpty() {
    return sz == 0;
  }

  public int size() {
    return sz;
  }

  public void insert(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    if (root == null) {
      root =
          new Node(
              p,
              new RectHV(
                  Double.NEGATIVE_INFINITY,
                  Double.NEGATIVE_INFINITY,
                  Double.POSITIVE_INFINITY,
                  Double.POSITIVE_INFINITY),
              true);
      sz++;
    } else {
      root.insert(p);
    }
  }

  public boolean contains(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }

    if (root == null) {
      return false;
    }

    return root.contains(p);
  }

  public void draw() {
    return;
  }

  private List<Point2D> getRange(RectHV rect) {
    temp = new ArrayList<>();
    if (root != null) {
      root.getList(rect);
    }
    return temp;
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException();
    }

    return new Iterable<Point2D>() {
      @Override
      public Iterator<Point2D> iterator() {
        return getRange(rect).iterator();
      }
    };
  }

  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }

    if (isEmpty()) {
      return null;
    }

    return root.nearest(p);
  }

  public static void main(String[] args) {
    KdTree kdTree = new KdTree();

    kdTree.insert(new Point2D(0.7, 0.2));
    kdTree.insert(new Point2D(0.5, 0.4));
    kdTree.insert(new Point2D(0.2, 0.3));
    kdTree.insert(new Point2D(0.4, 0.7));
    kdTree.insert(new Point2D(0.9, 0.6));

    System.out.println(kdTree.nearest(new Point2D(0.38, 0.43)));
  }
}
