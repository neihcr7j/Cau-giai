import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
  private Node front;
  private Node back;

  private int sz;

  private class Node {
    Item item;
    Node prev;
    Node next;

    Node(Item item, Node prev, Node next) {
      this.item = item;
      this.prev = prev;
      this.next = next;
    }
  }

  // construct an empty deque
  public Deque() {
    front = null;
    back = null;
    sz = 0;
  }

  // is the deque empty?
  public boolean isEmpty() {
    return (sz == 0);
  }

  // return the number of items on the deque
  public int size() {
    return sz;
  }

  // add the item to the front
  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (front == null) {
      front = new Node(item, null, null);
      back = front;
      sz = 1;
    } else {
      front.prev = new Node(item, null, front);
      front = front.prev;
      sz++;
    }
  }

  // add the item to the back
  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (front == null) {
      front = new Node(item, null, null);
      back = front;
      sz = 1;
    } else {
      back.next = new Node(item, back, null);
      back = back.next;
      sz++;
    }
  }

  // remove and return the item from the front
  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    Item removeItem = front.item;

    if (sz > 1) {
      front = front.next;
      front.prev = null;
      sz--;
    } else {
      front = null;
      back = null;
      sz = 0;
    }

    return removeItem;
  }

  // remove and return the item from the back
  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    Item removeItem = back.item;

    if (sz > 1) {
      back = back.prev;
      back.next = null;
      sz--;
    } else {
      front = null;
      back = null;
      sz = 0;
    }

    return removeItem;
  }

  // return an iterator over items in order from front to back
  @Override
  public Iterator<Item> iterator() {
    return new Iterator<Item>() {
      private Node current = front;

      @Override
      public boolean hasNext() {
        return current != null;
      }

      @Override
      public Item next() {
        if (current == null) {
          throw new NoSuchElementException();
        }

        Item item = current.item;
        current = current.next;
        return item;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  // unit testing (required)
  public static void main(String[] args) {
    Deque<String> d = new Deque<>();
    d.addFirst("b");
    d.addFirst("a");
    d.addLast("c");

    System.out.println(d.size());

    System.out.println(d.removeFirst());
    System.out.println(d.removeLast());
    System.out.println(d.removeFirst());
    System.out.println(d.removeFirst());
  }
}
