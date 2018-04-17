package com.company.barBossHouse;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.UnaryOperator;

public interface Order extends List<MenuItem>{

    int size();
    boolean isEmpty();
    boolean contains(Object o);
    Iterator<MenuItem> iterator();
    MenuItem[] items();

    boolean add(MenuItem newItem);// throws UnlawfulActionException;
    void add(int index, MenuItem element);
    MenuItem remove(int index);
    boolean remove(String name);
    boolean remove(MenuItem item);
    boolean containsAll(Collection<?> c);
    int removeAll(String i_name);
    int removeAll(MenuItem item);

    default void replaceAll(UnaryOperator<MenuItem> operator) {
        Objects.requireNonNull(operator);
        final ListIterator<MenuItem> li = this.listIterator();
        while (li.hasNext()) {
            li.set(operator.apply(li.next()));
        }
    }
    default void sort( MenuItem c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);
        ListIterator<MenuItem> i = this.listIterator();
        for (Object e : a) {
            i.next();
            i.set((MenuItem) e);
        }
    }
    void clear();
    boolean equals(Object obj);
    int hashCode();
    MenuItem get(int index);
    MenuItem set(int index,MenuItem element);
    int indexOf(Object o);
    int lastIndexOf(Object o);
    ListIterator<MenuItem> listIterator();
    ListIterator<MenuItem> listIterator(int index);
    List<MenuItem> subList(int fromIndex, int toIndex);
    default Spliterator<MenuItem> spliterator() {
        return Spliterators.spliterator(this, Spliterator.ORDERED);
    }
    int TotalCost();
    int numOfItems(String item);
    int numOfItems(MenuItem item);
    String[] itemsNames();
    MenuItem[] sortedByCost();
    String toString();
    boolean hasAlcoholicDrink();
    Customer getCustomer();
    void setCustomer(Customer customer);
    LocalDateTime getDateTime();

}