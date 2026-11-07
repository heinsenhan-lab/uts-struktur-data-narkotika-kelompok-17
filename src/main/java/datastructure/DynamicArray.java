package datastructure;

public class DynamicArray<T> {

    private Object[] data;
    private int size;
    private int capacity;

    public DynamicArray() {
        this.capacity = 10;
        this.data = new Object[capacity];
        this.size = 0;
    }
    public void add(T element) {
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
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index tidak valid");
        }

        return (T) data[index];
    }
    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index tidak valid");
        }

        data[index] = element;
    }
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index tidak valid");
        }

        T removedElement = (T) data[index];

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        data[size - 1] = null;
        size--;

        return removedElement;
    }
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
    public int getCapacity() {
        return capacity;
    }
}