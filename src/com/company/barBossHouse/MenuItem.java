package com.company.barBossHouse;

public abstract class MenuItem implements Comparable<MenuItem>{
    private static final int DEFAULT_COST =0;
    private static final String EMPTY_LINE ="";
    private int cost;
    private String name;
    private String description;

    protected MenuItem(String name,String description){
        this(DEFAULT_COST,name,description);
    }
    protected MenuItem(int cost, String name, String description){
        if(cost<0)
            throw  new IllegalArgumentException("Значение цены не может быть отрицательным");
        this.cost = cost;
        this.name = name;
        this.description=description;
    }

    public int getCost(){
        return cost;
    }
    public String getName(){
        return name;
    }
    public  String getDescription(){
        return description;
    }

    @Override
    public int compareTo(MenuItem o){
        return getCost() - o.getCost();
    }
    public String toString() {
        return String.format("%1$s%2$s%3$d", !name.isEmpty() ? name : "",",", cost!=DEFAULT_COST ? cost : "").trim();
    }
    public boolean equals(Object obj){
        if(obj!=null) {
            if (obj instanceof MenuItem) {
                MenuItem item = (MenuItem) obj;
                return(item.name.equals(name) &&
                        item.cost == cost);
            }
        }
        return false;
    }
    public int hashCode(){
        return cost^
                name.hashCode()^
                description.hashCode();
    }

}
