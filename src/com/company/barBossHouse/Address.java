package com.company.barBossHouse;

import java.util.Queue;

public final class Address {
    public static final Address EMPTY_ADDRESS = new Address();
    private static final char DEFAULT_CHAR =' ';
    private static final String DEFAULT_STRING =" ";
    private static final int DEFAULT_INT = 0;
    private static final String DEFAULT_CITY = "Самара";

    private final String city;
    private final String street;
    private final int zipCode;
    private final int buildingNumber;
    private final char buildingLetter;
    private final int apartmentNumber;
    //region
    public Address() {
        this(DEFAULT_CITY, DEFAULT_STRING, DEFAULT_INT, DEFAULT_INT, DEFAULT_CHAR, DEFAULT_INT);
    }

    public Address(String street, int houseNumber, char houseLetter, int officeNumber) {

        this(DEFAULT_CITY, street, DEFAULT_INT, houseNumber, houseLetter, officeNumber);

    }

    public Address(String city, String street, int zipCode, int houseNumber, char buildingLetter, int apartmentNumber){
        if(buildingLetter!=DEFAULT_CHAR &&!Character.isLetter(buildingLetter)) {
            throw new IllegalArgumentException("Литера не является буквой");
        }
        if(houseNumber<0){
            throw new IllegalArgumentException("Номер дома не может быть отрицательным");
        }
        if(zipCode<0){
            throw new IllegalArgumentException("Индекс дома не может быть отрицательным");
        }
        if(apartmentNumber<0){
            throw new IllegalArgumentException("Номер квартиры не может быть отрицательным");
        }
            this.city = city;
            this.street = street;
            this.zipCode = zipCode;
            this.buildingNumber = houseNumber;
            this.buildingLetter = buildingLetter;
            this.apartmentNumber = apartmentNumber;

    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getZipCode() {
        return zipCode;
    }

    public int getHouse_number() {
        return buildingNumber;
    }

    public char getLiteral() {
        return buildingLetter;
    }

    public int getOffice_number() {
        return apartmentNumber;
    }
    //endregion

    public String toString() { //todo через операторы
        StringBuilder toString = new StringBuilder("Address: ");
        if (!city.equals(DEFAULT_STRING)) {
            toString.append(city);
        }
        if (zipCode !=DEFAULT_INT)
        {
            toString.append(zipCode).append(", ");
        }
        if (!street.equals(DEFAULT_STRING)){
            toString.append(street).append(" ");
        }
        if (buildingNumber!=DEFAULT_INT) {
            toString.append(buildingNumber);
        }
        if (!(buildingLetter == DEFAULT_CHAR)){
            toString.append(buildingLetter);
        }
        if (apartmentNumber!=DEFAULT_INT) {
            toString.append("-").append(apartmentNumber);
        }
        return toString.toString() ;
    }

    public boolean equals(Object obj) {
        if(obj!=null) {
            if (obj.getClass() == this.getClass()) {
                Address address = (Address) obj;
                return (address.city.equals(city) &&
                        address.street.equals(street) &&
                        address.zipCode == zipCode &&
                        address.buildingNumber == buildingNumber &&
                        address.buildingLetter == buildingLetter &&
                        address.apartmentNumber == apartmentNumber);
            }
        }
        return false;
    }
    public int hashCode() {
        // Вычисляется как последовательное “исключающее или” хэш-кодов всех полей.
       return  city.hashCode()^
               street.hashCode()^
               zipCode ^
               buildingNumber^
               String.valueOf(buildingLetter).hashCode()^
               apartmentNumber;
    }


}