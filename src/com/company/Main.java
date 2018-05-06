package com.company;

import com.company.barBossHouse.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class Main {

    public static void main(String[] args) throws UnlawfulActionException {

        Customer Jane = new Customer();
        TableOrder order = new TableOrder(5,Jane);
        MenuItem item0 = new Dish(1000, "барбариска", "к чаю");
        MenuItem item1 = new Dish(999,"лимончик","к чаю");
        MenuItem item2 = new Dish(997,"карамель","к чаю");
        MenuItem item3 = new Dish(998,"рачок","к чаю");
        order.add(item3);
        order.add(item3);
        order.add(item3);

        ArrayList<MenuItem> arrayList = new ArrayList<>();
        arrayList.add(item0);
        arrayList.add(item0);
        arrayList.add(item0);
        arrayList.add(item0);
      //  order.addAll(1,arrayList);
        System.out.println(order.size());
        MenuItem[]items = order.items();
        Order order1 = new InternetOrder();
        order1.add(item1);
        order1.add(item1);

    }
    public static void print(MenuItem[] items){
        for(int i=0;i<items.length;i++){
          if(items[i]!=null){
            System.out.println(items[i].toString());
        }else System.out.println("null");
        }
    }
}
