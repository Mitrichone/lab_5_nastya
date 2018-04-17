package com.company.barBossHouse;

public class UnlawfulActionException extends Exception {
    String string;
    UnlawfulActionException(){

    }
    UnlawfulActionException(String s){
        string = s;
    }

    @Override
    public String toString() {
        return string;
    }
}
