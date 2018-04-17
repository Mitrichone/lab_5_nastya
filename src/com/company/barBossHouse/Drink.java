package com.company.barBossHouse;

public class Drink extends MenuItem implements Alcoholable{
    private static final int DEFAULT_INT = 0;
    private static final String DEFAULT_STRING = "";
    private int AlcoholVol;
    private DrinkTypeEnum drinkType;

    public Drink(String name, DrinkTypeEnum e_DrinkType){
        this(DEFAULT_INT,name,e_DrinkType,DEFAULT_STRING, DEFAULT_INT);
    }
    public Drink(int cost, String name, DrinkTypeEnum DrinkType, String description){
        this(cost,name,DrinkType,description,DEFAULT_INT);
    }
    public Drink(int cost, String name, DrinkTypeEnum DrinkType, String description, int alcoholVol){
        super(cost, name, description);
        if(alcoholVol<0 || alcoholVol>100)
            throw  new IllegalArgumentException("Значение доли алкоголя лежит вне пределов [0,100]");
        drinkType = DrinkType;
        AlcoholVol = alcoholVol;
    }

   public boolean isItAlcoholic(){
       return  (this.AlcoholVol>0);

   }
   public int getAlcoholVol(){
       return this.AlcoholVol;
   }
    public DrinkTypeEnum getDrinkType(){
        return drinkType;
    }

    public String toString() {
        //Возвращаемая строка составляется в формате:“Drink: <type> <name>, <cost>р.
        //Процент алкоголя включается если напиток алкогольный.


        return String.format("%1$s %2$s %3$d %4$s","Drink:",getDrinkType(),getAlcoholVol()>0 ? getAlcoholVol():"", super.toString());
    }
    public boolean equals(Object obj) {
        //если у них одинаковый тип, стоимость, имена и процент спирта совпадают
                //todo вызывать из суперкласса in dish
        if(super.equals(obj)) {
            Drink drink = (Drink) obj;
            return drink.drinkType == drinkType &&
                    drink.AlcoholVol == AlcoholVol;
        }
        return  false;
    }
    public int hashCode(){
        return super.hashCode()^
                getDrinkType().hashCode()^
                AlcoholVol;
    }

}
