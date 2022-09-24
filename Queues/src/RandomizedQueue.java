import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] arr;
  private int sz;

  // construct an empty randomized queue
  public RandomizedQueue() {
    arr = (Item[]) new Object[1];
    sz = 0;
  }

  private void incSize() {
    if (sz + 1 > arr.length) {
      Item[] temp = (Item[]) new Object[arr.length * 2];

      for (int i = 0; i < sz; ++i) {
        temp[i] = arr[i];
      }

      arr = temp;
    }
  }

  private void decSize() {
    if (sz < arr.length / 4) {
      Item[] temp = (Item[]) new Object[arr.length / 2];
      for (int i = 0; i < sz; ++i) {
        temp[i] = arr[i];
      }
      arr = temp;
    }
  }

  // is the randomized queue empty?
  public boolean isEmpty() {
    return sz == 0;
  }

  // return the number of items on the randomized queue
  public int size() {
    return sz;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    incSize();

    if (sz == 0) {
      arr[sz++] = item;
    } else {
      int k = StdRandom.uniformInt(sz);
      arr[sz++] = arr[k];
      arr[k] = item;
    }
  }

  // remove and return a random item
  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    int k = StdRandom.uniformInt(sz);
    Item item = arr[k];

    if (k == sz - 1) {
      sz--;
    } else {
      arr[k] = arr[--sz];
    }

    decSize();

    return item;
  }

  // return a random item (but do not remove it)
  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    return arr[StdRandom.uniformInt(sz)];
  }

  // return an independent iterator over items in random order
  @Override
  public Iterator<Item> iterator() {
    return new IteratorRQ();
  }

  private class IteratorRQ implements Iterator<Item> {
    int it;
    Item[] a;

    IteratorRQ() {
      it = 0;
      a = (Item[]) new Object[sz];

      for (int i = 0; i < sz; ++i) {
        a[i] = arr[i];
      }

      StdRandom.shuffle(a);
    }

    @Override
    public boolean hasNext() {
      return it < sz;
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      return a[it++];
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  // unit testing (required)
  public static void main(String[] args) {
    RandomizedQueue<Integer> queue = new RandomizedQueue<>();
    queue.enqueue(4);
    System.out.println(queue.dequeue());
    System.out.println(queue.size());
    queue.enqueue(3);
    System.out.println(queue.size());
    System.out.println(queue.dequeue());
  }
}
