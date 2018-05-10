package com.company.barBossHouse;

import sun.invoke.empty.Empty;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public final class Customer {
    public static final Customer MATURE_UNKNOWN_CUSTOMER = new Customer(ageToDate(21));
    public static final Customer NOT_MATURE_UNKNOWN_CUSTOMER= new Customer(ageToDate(14));

    public static final LocalDate DEFAULT_DATE = LocalDate.of(1,1,1);
    private static final String DEFAULT_STRING =" ";
    private static final int DEFAULT_INT = -1;

    private final String name;
    private final String surname;
    private final LocalDate birthDate;
    private final Address address;

    public Customer(){
    this(DEFAULT_STRING, DEFAULT_STRING, DEFAULT_DATE, Address.EMPTY_ADDRESS);
    }
    public Customer(LocalDate birhDate){
        this(DEFAULT_STRING, DEFAULT_STRING, birhDate, Address.EMPTY_ADDRESS);
    }

    public Customer(String name, String surname, LocalDate birthDate, Address address){
        if(birthDate.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Невозможно ввести дату из будущего");
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.address = address;
    }
    public static LocalDate ageToDate(int age){
      return LocalDate.now().minusYears(age);
    }
    public String getName() {
        return name;
    }
    public LocalDate getBirthDate(){
        return birthDate;
    }
    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return (int)(ChronoUnit.YEARS.between(birthDate, LocalDate.now()));
    }

    public Address getAddress() {
        return address;
    }

    public String toString() {
  //Возвращаемая строка составляется в формате:“Customer: <secondName> <firstName>, <age>, <address>”
        StringBuilder stringBuilder = new StringBuilder("Customer: ");
        if (!surname.equals(DEFAULT_STRING)) {
            stringBuilder.append(surname);
        }
        if (!name.equals(DEFAULT_STRING)) {
            stringBuilder.append(" ").append(name);
        }
        if (getAge()!=DEFAULT_INT) {
            stringBuilder.append(", ").append(getAge());
        }
        if (address != Address.EMPTY_ADDRESS) {
            stringBuilder.append(", ").append(address.toString());
        }
        return String.valueOf(stringBuilder);
    }
    public boolean equals(Object obj) {
        if(obj!=null) {
            if (obj.getClass()==this.getClass()){
                Customer customer = (Customer) obj;
                return(customer.name.equals(name) &&
                        customer.surname.equals(surname) &&
                        customer.address.equals(address) &&
                        customer.birthDate.isEqual(birthDate));
            }
        }
        return false;
    }
    public int hashCode() {
        // Вычисляется как последовательное “исключающее или” хэш-кодов всех полей.
        return  name.hashCode()^
                surname.hashCode()^
                birthDate.toString().hashCode()^
                address.hashCode();
    }
}
