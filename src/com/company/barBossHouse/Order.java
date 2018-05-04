package com.company.barBossHouse;

import java.time.LocalDateTime;
import java.util.*;

public interface Order extends List<MenuItem>{


    boolean equals(Object obj);
    int hashCode();

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