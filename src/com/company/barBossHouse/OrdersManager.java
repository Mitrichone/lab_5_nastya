package com.company.barBossHouse;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public interface OrdersManager extends Collection<Order> {
int size();
Order[] orders();
int TotalCost();
int numOfItems(String itemName);
int numOfItems(MenuItem item);
int customersAge();

int ordersByDateCount(LocalDate date);
List<Order> ordersByDate(LocalDate date);
List<Order> ordersByCustomer(Customer customer);

}
