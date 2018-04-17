package com.company.barBossHouse;

public class NegativeSizeException extends NegativeArraySizeException {
    String string;
 NegativeSizeException(){

 }
 NegativeSizeException(String s){
     string = s;
 }

    @Override
    public String toString() {
        return string;
    }
}
