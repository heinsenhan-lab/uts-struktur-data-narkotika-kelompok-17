package datastructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {

    @Test
    void testAddAndGet() {
        DynamicArray<String> array = new DynamicArray<>();

        array.add("Farhan");
        array.add("Iman");

        assertEquals(2, array.size());
        assertEquals("Farhan", array.get(0));
        assertEquals("Iman", array.get(1));
    }

    @Test
    void testSet() {
        DynamicArray<String> array = new DynamicArray<>();

        array.add("Farhan");
        array.set(0, "Andi");

        assertEquals("Andi", array.get(0));
    }

    @Test
    void testRemove() {
        DynamicArray<String> array = new DynamicArray<>();

        array.add("Farhan");
        array.add("Iman");
        array.add("Andi");

        String removed = array.remove(1);

        assertEquals("Iman", removed);
        assertEquals(2, array.size());
        assertEquals("Andi", array.get(1));
    }

    @Test
    void testResize() {
        DynamicArray<Integer> array = new DynamicArray<>();

        for (int i = 0; i < 11; i++) {
            array.add(i);
        }

        assertEquals(11, array.size());
        assertEquals(20, array.getCapacity());
        assertEquals(10, array.get(10));
    }

    @Test
    void testIsEmpty() {
        DynamicArray<String> array = new DynamicArray<>();

        assertTrue(array.isEmpty());

        array.add("Farhan");

        assertFalse(array.isEmpty());
    }

    @Test
    void testInvalidIndex() {
        DynamicArray<String> array = new DynamicArray<>();

        assertThrows(IndexOutOfBoundsException.class,
                () -> array.get(0));

        array.add("Farhan");

        assertThrows(IndexOutOfBoundsException.class,
                () -> array.get(1));

        assertThrows(IndexOutOfBoundsException.class,
                () -> array.remove(-1));
    }
}