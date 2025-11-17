package com.ecwid.deepcopy.deep_copy;

import com.ecwid.deepcopy.deep_copy.objects.Family;
import com.ecwid.deepcopy.deep_copy.objects.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DeepCopyTest {

    @Test
    void testImmutableTypes() {
        String original = "Hello";
        String copy = DeepCopy.copy(original);
        assertSame(original, copy);
    }

    @Test
    void testArrayCopy() {
        int[] original = {1, 2, 3};
        int[] copy = DeepCopy.copy(original);

        assertNotSame(original, copy);
        assertArrayEquals(original, copy);
    }

    @Test
    void testNestedArrayCopy() {
        String[][] original = {
                {"A", "B"},
                {"C", "D"}
        };
        String[][] copy = DeepCopy.copy(original);

        assertNotSame(original, copy);
        assertArrayEquals(original[0], copy[0]);
        assertArrayEquals(original[1], copy[1]);
    }

    @Test
    void testImmutableLocalDate() {
        LocalDate original = LocalDate.of(2025, 11, 17);
        LocalDate copy = DeepCopy.copy(original);

        assertSame(original, copy);
    }

    @Test
    void testImmutableLocalDateTime() {
        LocalDateTime original = LocalDateTime.of(2025, 11, 17, 23, 0);
        LocalDateTime copy = DeepCopy.copy(original);

        assertSame(original, copy);
    }

    @Test
    void testOptionalCopy() {
        Optional<String> original = Optional.of("Hello");
        Optional<String> copy = DeepCopy.copy(original);

        assertNotSame(original, copy);
        assertEquals(original.get(), copy.get());
    }

    @Test
    void testOptionalEmptyCopy() {
        Optional<String> original = Optional.empty();
        Optional<String> copy = DeepCopy.copy(original);

        assertTrue(copy.isEmpty());
        assertSame(original, copy);
    }

    @Test
    void testUuidCopy() {
        UUID original = UUID.randomUUID();
        UUID copy = DeepCopy.copy(original);

        assertSame(original, copy);
    }

    @Test
    void testListCopy() {
        List<String> original = new ArrayList<>(List.of("A", "B", "C"));
        List<String> copy = DeepCopy.copy(original);

        assertNotSame(original, copy);
        assertEquals(original, copy);
        assertTrue(copy.contains("A"));
    }

    @Test
    void testSetCopy() {
        Set<String> original = new HashSet<>(List.of("A", "B", "C"));
        Set<String> copy = DeepCopy.copy(original);

        assertNotSame(original, copy);
        assertEquals(original, copy);
        assertTrue(copy.contains("A"));
    }

    @Test
    void testQueueCopy() {
        Queue<Integer> original = new LinkedList<>(List.of(1, 2, 3));
        Queue<Integer> copy = DeepCopy.copy(original);

        assertNotSame(original, copy);
        assertEquals(original.size(), copy.size());
        assertEquals(original.poll(), copy.poll());
    }

    @Test
    void testDequeCopy() {
        Deque<String> original = new ArrayDeque<>(List.of("X", "Y", "Z"));
        Deque<String> copy = DeepCopy.copy(original);

        assertNotSame(original, copy);
        assertEquals(original.size(), copy.size());

        assertEquals(original.peekFirst(), copy.peekFirst());
        assertEquals(original.peekLast(), copy.peekLast());
    }

    @Test
    void testMapCopy() {
        Map<String, Integer> original = new HashMap<>();
        original.put("one", 1);
        original.put("two", 2);

        Map<String, Integer> copy = DeepCopy.copy(original);

        assertNotSame(original, copy);
        assertEquals(original, copy);
        assertEquals(2, copy.size());
    }

    @Test
    void testLinkedHashMapCopy() {
        LinkedHashMap<String, Integer> original = new LinkedHashMap<>();
        original.put("one", 1);
        original.put("two", 2);
        original.put("three", 3);

        LinkedHashMap<String, Integer> copy = DeepCopy.copy(original);

        assertNotSame(original, copy);
        assertEquals(original, copy);
        assertEquals(List.copyOf(original.keySet()), List.copyOf(copy.keySet()));
    }

    @Test
    void testTreeMapCopy() {
        TreeMap<String, Integer> original = new TreeMap<>();
        original.put("b", 2);
        original.put("a", 1);
        original.put("c", 3);

        TreeMap<String, Integer> copy = DeepCopy.copy(original);

        assertNotSame(original, copy);
        assertEquals(original, copy);
        assertEquals(original.firstKey(), copy.firstKey());
        assertEquals(original.lastKey(), copy.lastKey());
    }

    @Test
    void testObjectCopy() {
        Person original = new Person("Alice", 30);
        Person copy = DeepCopy.copy(original);

        assertNotSame(original, copy);
        Assertions.assertEquals(original.getName(), copy.getName());
        Assertions.assertEquals(original.getAge(), copy.getAge());
    }

    @Test
    void testNestedObjectCopyAndNoCyclicDependencies() {
        Person child = new Person("Bob", 5);
        Family original = new Family("Smith", child);

        Family copy = DeepCopy.copy(original);

        assertNotSame(original, copy);
        Assertions.assertEquals(original.getSurname(), copy.getSurname());
        assertNotSame(original.getChild(), copy.getChild());
        Assertions.assertEquals(original.getChild().getName(), copy.getChild().getName());

        IdentityHashMap<Object, Object> context = new IdentityHashMap<>();
        DeepCopy.deepCopyInternal(original, context);
        assertEquals(2, context.size());
    }

    @Test
    void testListWithoutCycles() {
        Person p1 = new Person("Alice", 30);
        Person p2 = new Person("Bob", 25);
        List<Person> original = new ArrayList<>();
        original.add(p1);
        original.add(p2);

        List<Person> copy = DeepCopy.copy(original);

        assertNotSame(original, copy);
        assertEquals(original.size(), copy.size());

        assertNotSame(original.get(0), copy.get(0));
        assertEquals(original.get(0).getName(), copy.get(0).getName());

        assertNotSame(original.get(1), copy.get(1));
        assertEquals(original.get(1).getName(), copy.get(1).getName());

        IdentityHashMap<Object, Object> context = new IdentityHashMap<>();
        DeepCopy.deepCopyInternal(original, context);

        assertEquals(3, context.size());
    }
}
