package com.company.barBossHouse;

public class NoFreeTableException extends Exception {
    String string;
    NoFreeTableException(){

    }
    NoFreeTableException(String s){
        string = s;
    }
    @Override
    public String toString() {
        return string;
    }
}
