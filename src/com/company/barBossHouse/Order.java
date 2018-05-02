package com.company.barBossHouse;

import java.time.LocalDateTime;
import java.util.*;

public interface Order extends List<MenuItem>{

    int size();
    boolean isEmpty();
    boolean contains(Object o);
    Iterator<MenuItem> iterator();
    MenuItem[] items();

    boolean add(MenuItem newItem);
    void add(int index, MenuItem element);
    MenuItem remove(int index);
    boolean remove(MenuItem item);
    boolean containsAll(Collection<?> c);
    boolean removeAll(Collection<?> c);
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

    int TotalCost();
    int numOfItems(String item);
    int numOfItems(MenuItem item);
    String[] itemsNames();
    MenuItem[] sort();
    String toString();
    boolean hasAlcoholicDrink();
    Customer getCustomer();
    void setCustomer(Customer customer);
    LocalDateTime getDateTime();

}