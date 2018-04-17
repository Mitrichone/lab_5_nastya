package com.company.barBossHouse;

public class Dish extends MenuItem {
    private static final int DEFAULT_COST =0;
    private static final String EMPTY_LINE ="";
    public Dish(int cost, String name, String description) {
        super(cost, name, description);
    }
    public Dish(String name, String description) {
        this(DEFAULT_COST, name, description);
    }

    public String toString() {
        //Возвращаемая строка составляется в формате:“<name>, <cost>р.”
     return super.toString();
    }

    public boolean equals(Object obj){
       return super.equals(obj);
    }

    public int hashCode(){
        return  super.hashCode();
    }
}
