package datastructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuditStackTest {

    @Test
    void testPushAndSize() {
        AuditStack<String> stack = new AuditStack<>();

        stack.push("Farhan");
        stack.push("Iman");

        assertEquals(2, stack.size());
    }

    @Test
    void testPop() {
        AuditStack<String> stack = new AuditStack<>();

        stack.push("Farhan");
        stack.push("Iman");

        assertEquals("Iman", stack.pop());
        assertEquals(1, stack.size());
    }

    @Test
    void testPeek() {
        AuditStack<String> stack = new AuditStack<>();

        stack.push("Farhan");
        stack.push("Iman");

        assertEquals("Iman", stack.peek());
        assertEquals(2, stack.size());
    }

    @Test
    void testIsEmpty() {
        AuditStack<String> stack = new AuditStack<>();

        assertTrue(stack.isEmpty());

        stack.push("Farhan");

        assertFalse(stack.isEmpty());
    }

    @Test
    void testPopEmptyStack() {
        AuditStack<String> stack = new AuditStack<>();

        assertThrows(IllegalStateException.class, stack::pop);
    }

    @Test
    void testPeekEmptyStack() {
        AuditStack<String> stack = new AuditStack<>();

        assertThrows(IllegalStateException.class, stack::peek);
    }

    @Test
    void testResize() {
        AuditStack<Integer> stack = new AuditStack<>();

        for (int i = 0; i < 11; i++) {
            stack.push(i);
        }

        assertEquals(11, stack.size());
        assertEquals(20, stack.getCapacity());
        assertEquals(10, stack.peek());
    }
}