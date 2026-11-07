package datastructure;

public class AuditStack<T> {

    private Object[] data;
    private int size;
    private int capacity;

    public AuditStack() {
        this.capacity = 10;
        this.data = new Object[capacity];
        this.size = 0;
    }

    public void push(T element) {
        if (size == capacity) {
            resize();
        }

        data[size] = element;
        size++;
    }

    private void resize() {
        capacity = capacity * 2;

        Object[] newData = new Object[capacity];

        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }

        data = newData;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack kosong");
        }

        T removedElement = (T) data[size - 1];

        data[size - 1] = null;
        size--;

        return removedElement;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack kosong");
        }

        return (T) data[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }
}