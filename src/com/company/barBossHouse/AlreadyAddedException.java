package com.company.barBossHouse;

public class AlreadyAddedException  extends RuntimeException {
    String string;
    AlreadyAddedException (){

    }
    AlreadyAddedException(String s){
        string = s;
    }

    @Override
    public String toString() {
        return string;
    }

}
