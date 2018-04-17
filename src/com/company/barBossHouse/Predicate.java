package com.company.barBossHouse;


    public interface Predicate<Order> {
        boolean isFree(Order order);
        default int[]filter(com.company.barBossHouse.Predicate<Order> orderPredicate, Order[] orders){
            int size=0;
            int index=0;
            for(Order order: orders){
                if(orderPredicate.isFree(order)){
                    size++;
                }
            }
            int[]filter=new int[size];
            for(int i=0;i<orders.length; i++){
                if(orderPredicate.isFree(orders[i])){
                    filter[index]=i;
                    index++;
                }
            }
            return filter;
        }
    }

