import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdIn;

import java.awt.Color;

public class SeamCarver {
  private Picture picture;
//  private double[][] arr;
  // create a seam carver object based on the given picture
  public SeamCarver(Picture picture) {
    if (picture == null) {
      throw new IllegalArgumentException();
    }
    this.picture = new Picture(picture);
  }

  // current picture
  public Picture picture() {
    return new Picture(picture);
  }

  // width of current picture
  public int width() {
    return picture.width();
  }

  // height of current picture
  public int height() {
    return picture.height();
  }

  private int sqr(int a) {
    return a * a;
  }

  private int deltaEnergy(Color a, Color b) {
    return sqr(a.getRed() - b.getRed())
        + sqr(a.getBlue() - b.getBlue())
        + sqr(a.getGreen() - b.getGreen());
  }

  // energy of pixel at column x and row y
  public double energy(int x, int y) {
    if (x < 0 || y < 0 || x >= width() || y >= height()) {
      throw new IllegalArgumentException();
    }

    if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
      return 1000;
    }

//    return arr[x][y];

    return Math.sqrt(
        deltaEnergy(picture.get(x, y - 1), picture.get(x, y + 1))
            + deltaEnergy(picture.get(x - 1, y), picture.get(x + 1, y)));
  }

  // sequence of indices for horizontal seam
  public int[] findVerticalSeam() {
    double[][] d = new double[width()][height()];

    for (int i = 0; i < width(); ++i) {
      d[i][0] = energy(i, 0);
    }

    for (int j = 1; j < height(); ++j) {
      for (int i = 0; i < width(); ++i) {
        double thisEnergy = energy(i, j);

        d[i][j] = d[i][j - 1] + thisEnergy;

        if (i > 0) {
          d[i][j] = Math.min(d[i][j], d[i - 1][j - 1] + thisEnergy);
        }

        if (i + 1 < width()) {
          d[i][j] = Math.min(d[i][j], d[i + 1][j - 1] + thisEnergy);
        }
      }
    }

    int best = 0;
    for (int i = 0; i < width(); ++i)
      if (d[i][height() - 1] < d[best][height() - 1]) {
        best = i;
      }

    int[] result = new int[height()];

    for (int j = height() - 1; j >= 0; --j) {
      result[j] = best;
      double thisEnergy = energy(best, j);

      if (j == 0) {
        break;
      }

      if (d[best][j] == d[best][j - 1] + thisEnergy) {
        continue;
      }

      if (best > 0 && d[best][j] == d[best - 1][j - 1] + thisEnergy) {
        best--;
      } else {
        best++;
      }
    }

    return result;
  }

  public int[] findHorizontalSeam() {
    double[][] d = new double[width()][height()];

    for (int j = 0; j < height(); ++j) {
      d[0][j] = energy(0, j);
    }

    for (int i = 1; i < width(); ++i) {
      for (int j = 0; j < height(); ++j) {
        double thisEnergy = energy(i, j);

        d[i][j] = d[i - 1][j] + thisEnergy;

        if (j > 0) {
          d[i][j] = Math.min(d[i][j], d[i - 1][j - 1] + thisEnergy);
        }

        if (j + 1 < height()) {
          d[i][j] = Math.min(d[i][j], d[i - 1][j + 1] + thisEnergy);
        }
      }
    }

    int best = 0;
    for (int j = 0; j < height(); ++j) {
      if (d[width() - 1][j] < d[width() - 1][best]) {
        best = j;
      }
    }

    int[] result = new int[width()];

    for (int i = width() - 1; i >= 0; --i) {
      result[i] = best;
      double thisEnergy = energy(i, best);

      if (i == 0) {
        break;
      }

      if (d[i][best] == d[i - 1][best] + thisEnergy) {
        continue;
      }

      if (best > 0 && d[i][best] == d[i - 1][best - 1] + thisEnergy) {
        best--;
      } else {
        best++;
      }
    }

    return result;
  }

  // remove horizontal seam from current picture
  public void removeHorizontalSeam(int[] seam) {
    if (seam == null || seam.length != width()) {
      throw new IllegalArgumentException();
    }

    for (int j = 0; j < width(); ++j) {
      if (seam[j] < 0 || seam[j] >= height()) {
        throw new IllegalArgumentException();
      }
    }

    for (int j = 1; j < width(); ++j) {
      if (Math.abs(seam[j] - seam[j - 1]) > 1) {
        throw new IllegalArgumentException();
      }
    }

    Picture other = new Picture(width(), height() - 1);

    for (int i = 0; i < width(); ++i) {
      for (int j = 0; j < height(); ++j) {
        if (j < seam[i]) {
          other.set(i, j, picture.get(i, j));
        } else if (j > seam[i]) {
          other.set(i, j - 1, picture.get(i, j));
        }
      }
    }

    picture = other;
  }

  // remove vertical seam from current picture
  public void removeVerticalSeam(int[] seam) {
    if (seam == null || seam.length != height()) {
      throw new IllegalArgumentException();
    }

    for (int i = 0; i < height(); ++i) {
      if (seam[i] < 0 || seam[i] >= width()) {
        throw new IllegalArgumentException();
      }
    }

    for (int i = 1; i < height(); ++i) {
      if (Math.abs(seam[i] - seam[i - 1]) > 1) {
        throw new IllegalArgumentException();
      }
    }

    Picture other = new Picture(width() - 1, height());

    for (int i = 0; i < width(); ++i) {
      for (int j = 0; j < height(); ++j) {
        if (i < seam[j]) {
          other.set(i, j, picture.get(i, j));
        } else if (i > seam[j]) {
          other.set(i - 1, j, picture.get(i, j));
        }
      }
    }

    picture = other;
  }

  //  unit testing (optional)
  public static void main(String[] args) {
    Picture picture = new Picture("image.png");
    SeamCarver seamCarver = new SeamCarver(picture);
    int[] a = { 3, 4, 3, 2, 1, 1 };

    seamCarver.removeHorizontalSeam(a);

    seamCarver.picture().show();
  }
}
