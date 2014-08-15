package cn.mob.poplar.util;

import java.util.*;

public class Lists {

    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();
    }

    public static <E> ArrayList<E> newArrayList(E[] elements) {
        int capacity = (elements.length);
        ArrayList<E> list = new ArrayList<E>(capacity);
        Collections.addAll(list, elements);
        return list;
    }

    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
        return (elements instanceof Collection<?>) ? new ArrayList<E>(cast(elements)) : newArrayList(elements.iterator());
    }

    static <T> Collection<T> cast(Iterable<T> iterable) {
        return (Collection<T>) iterable;
    }

    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
        ArrayList<E> list = newArrayList();
        while (elements.hasNext()) {
            list.add(elements.next());
        }
        return list;
    }

    public static <E> ArrayList<E> newArrayList(int initialArraySize) {
        return new ArrayList<E>(initialArraySize);
    }

    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList<E>();
    }

    public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> elements) {
        LinkedList<E> list = newLinkedList();
        for (Iterator<? extends E> it = elements.iterator(); it.hasNext(); ) {
            E element = it.next();
            list.add(element);
        }
        return list;
    }

    public static <T extends Comparable<? super T>> void sort(List<T> source) {
        Collections.sort(source);
    }

    public static <T> void sort(List<T> source, Comparator<? super T> comparator) {
        Collections.sort(source, comparator);
    }

    public static <T> T[] toArray(Collection<? extends T> elements, Class<T> type) {
        T[] array = ArrayUtils.newArray(type, elements.size());
        int i = 0;
        for (T t : elements) {
            array[i++] = t;
        }
        return array;
    }

}
