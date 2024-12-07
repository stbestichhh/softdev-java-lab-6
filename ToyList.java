import java.util.*;

/**
 * A typed collection of Toys implemented by using a doubly linked list
 *
 * @param <T> The type of toy
 */
public class ToyList<T extends Toy> implements List<T> {
  private Node<T> head;
  private Node<T> tail;
  private int size;

  /**
   * Node represents an element in the doubly linked list
   *
   * @param <T> type of the element
   */
  private static class Node<T> {
    T data;
    Node<T> next;
    Node<T> prev;

    Node(T data) {
      this.data = data;
    }
  }

  /**
   * Default constructor for an empty collection
   */
  public ToyList() {
    this.size = 0;
  }

  /**
   * Constructor that initializes the list with one element
   *
   * @param toy The toy to initialize list with
   */
  public ToyList(T toy) {
    this();
    add(toy);
  }

  /**
   * Constructor that initializes the list with toys from other list
   *
   * @param toys A collections of toys to initialize ToyList with
   */
  public ToyList(Collection<? extends T> toys) {
    this();
    for (T toy : toys) {
      add(toy);
    }
  }

  @Override
  public int size() {
    return this.size;
  }

  @Override
  public boolean isEmpty() {
    return this.size == 0;
  }

  @Override
  public boolean contains(Object obj) {
    for (T toy : this) {
      if (Objects.equals(toy, obj)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      private Node<T> current = head;
      
      @Override
      public boolean hasNext() {
        return current != null;
      }

      @Override
      public T next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        T data = current.data;
        current = current.next;
        return data;
      }
    };
  }

  @Override
  public Object[] toArray() {
    Object[] array = new Object[this.size];
    int index = 0;
    for (T toy : this) {
      array[index++] = toy;
    }
    return array;
  }

  @Override
  public boolean add(T toy) {
    Node<T> newNode = new Node(toy);
    if (head == null) {
      head = tail = newNode;
    } else {
      tail.next = newNode;
      newNode.prev = tail;
      tail = newNode;
    }
    size++;
    return true;
  }

  @Override
  public boolean remove(Object o) {
    Node<T> current = head;
    while (current != null) {
      if (Objects.equals(current.data, o)) {
        if (current.prev != null) {
          current.prev.next = current.next;
        } else {
          head = current.next;
        }

        if (current.next != null) {
          current.next.prev = current.prev;
        } else {
          tail = current.prev;
        }
        size--;
        return true;
      }
      current = current.next;
    }
    return false;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    for (Object toy : c) {
      if (!contains(toy)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    for (T toy : c) {
      add(toy);
    }
    return true;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    boolean modified = false;
    for (Object toy : c) {
      while(remove(toy)) {
        modified = true;
      }
    }
    return modified;
  }

  @Override
  public void clear() {
    head = tail = null;
    size = 0;
  }

  @Override
  public T get(int index) {
    return getNode(index).data;
  }

  @Override
  public T set(int index, T element) {
    Node<T> node = getNode(index);
    T oldData = node.data;
    node.data = element;
    return oldData;
  }

  @Override
  public T remove(int index) {
    Node<T> node = getNode(index);
    T data = node.data;
    remove(data);
    return data;
  }

  @Override
  public int indexOf(Object o) {
    int index = 0;
    for (T toy : this) {
      if (Objects.equals(toy, o)) {
        return index;
      }
      index++;
    }
    return -1;
  }

  @Override
  public int lastIndexOf(Object o) {
    int index = size - 1;
    Node<T> current = tail;
    while (current != null) {
      if (Objects.equals(current.data, o)) {
        return index;
      }
      current = current.prev;
      index--;
    }
    return -1;
  }

  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
      throw new IndexOutOfBoundsException();
    }

    ToyList<T> subList = new ToyList<>();
    Node<T> current = getNode(fromIndex);

    for(int i = fromIndex; i < toIndex; i++) {
      subList.add(current.data);
      current = current.next;
    }
    return subList;
  }

  @Override
  public ListIterator<T> listIterator() {
    throw new UnsupportedOperationException("Unsupported operation");
  }

  @Override
  public ListIterator<T> listIterator(int index) {
    throw new UnsupportedOperationException("Unsupported operation");
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException("Unsupported operation");
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> c) {
    throw new UnsupportedOperationException("Unsupported operation");
  }

  @Override
  public <U> U[] toArray(U[] u) {
    throw new UnsupportedOperationException("Unsupported operation");
  }

  @Override
  public void add(int index, T element) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    Node<T> newNode = new Node<>(element);

    if (index == 0) {
      if (head == null) {
        head = newNode;
        tail = newNode;
      } else {
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
      }
    } else if (index == size) {
      tail.next = newNode;
      newNode.prev = tail;
      tail = newNode;
    } else { 
      Node<T> current = getNode(index);
      Node<T> previous = current.prev;

      previous.next = newNode;
      newNode.prev = previous;
      newNode.next = current;
      current.prev = newNode;
    }

    size++;
  }

  private Node<T> getNode(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index: " + index + "\tSize: " + size);
    }
    Node<T> current = head;
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    return current;
  }
}
