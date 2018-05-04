package com.company.barBossHouse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static java.lang.System.arraycopy;

public class TableOrdersManager implements List<Order>,OrdersManager {
    private Order[]tables;
    private int size;
    private static final int DEFAULT_SIZE =0;

    public TableOrdersManager(int length)throws NegativeSizeException{
        if(length<0)
            throw new NegativeSizeException("Размер массива не может быть отрицательным");
        tables = new TableOrder[length];
        size= DEFAULT_SIZE;
    }
    //todo код этого метода должен быть на строчке 203
    public void add(int num, TableOrder NewOrder)throws AlreadyAddedException {
        if(!isFreeTable(num)){
            throw new AlreadyAddedException("Столик не свободен");
        }
        tables[num] = NewOrder;
        size++;
    }
    public Order ReturnOrder(int num){
        return tables[num];
    }

    //todo откуда берутся эти num?
    public void AddItem(int num, MenuItem item) throws UnlawfulActionException{
        if(item instanceof Drink){
            Drink drink = (Drink)item;
            if(drink.isItAlcoholic()
                    & tables[num].getCustomer().getAge()<18
                    & tables[num].getDateTime().isAfter((LocalDateTime.of(tables[num].getDateTime().toLocalDate(), LocalTime.of(22, 0))))) {
                throw new UnlawfulActionException();
            }
        }
         tables[num].add(item);
    }

    public boolean isFreeTable(int num){
        if(tables[num]==null)return true;
        else return false;
    }
    @Override
    public int size(){ //new
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Order order: tables) {
            if (order.equals(o))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<Order> iterator() {
        return new Iterator<Order>() {
            int pos = 0;

            public boolean hasNext() {
                return size > pos;
            }

            public Order next() {
                return tables[pos++];
            }
            //todo NoSuchElementException (когда доходим до последнего элемента)

        };
    }

    @Override
    public Object[] toArray() {
        return orders();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) Arrays.copyOf(orders(), size(), a.getClass());
    }

    @Override
    public boolean add(Order order) {
            throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(tables[i], o)) {
                tables[i] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Order> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends Order> c) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        for (Object o : c) {
            tables[index++] = (Order) o;
            //todo AlreadyAddedException
            //todo идешь либо до первого эксепшена, либо до конца c, либо до конца this.orders
        }
        //size += c.size();
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Iterator<Order> iterator = iterator();
        boolean changed = false;
        //todo отрефакторить аналогично order
        while(iterator.hasNext()) {
            Order order = iterator.next();
            for (int i = 0; i < size; i++) {
                if (order.equals(tables[i])) {
                    remove(i);
                    changed = true;
                    break;
                }
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        //todo отрефакторить аналогично order
        boolean changed = false;
        for(int i = 0; i < size; i++) {
            Iterator<?> iterator = c.iterator();
            boolean hasItem = false;
            while (iterator.hasNext()) {
                if (tables[i].equals(iterator.next())) {
                    hasItem = true;
                    break;
                }
            }
            if (!hasItem) {
                remove(i);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        tables = new Order[tables.length];
        //todo ручками по массиву и null
        size = 0;
    }

    @Override
    public Order get(int index) {
        if(index<0 || index>=size)throw new IndexOutOfBoundsException();
        return tables[index];
    }

    @Override
    public Order set(int index, Order element) {
        if(element == null)throw new NullPointerException();
        if(index<0 || index >=size)throw new IndexOutOfBoundsException();
        Order old = tables[index];
        tables[index]=element;
        return old;
    }

    @Override
    public void add(int index, Order element) {
        if(index<0 || index>=size())throw new IndexOutOfBoundsException();
        if(element==null)throw  new NullPointerException();
        tables[index]=element;
    }

    @Override
    public Order remove(int index) {
        if(index<0 || index>=size){
            throw new IndexOutOfBoundsException();
        }
        Order previous =tables[index];
        tables[index] = null;
        return previous;
    }

    @Override
    public int indexOf(Object o) {
        for(int i = 0; i < size; i++){
            if(tables[i].equals(o))
                return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for(int i = size - 1; i > -1; i--){
            if(tables[i].equals(o))
                return i;
        }
        return -1;
    }

    @Override
    public ListIterator<Order> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<Order> listIterator(int index) {
        //todo аналогично итератору из TableOrder
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        TableOrdersManager tableOrdersManager = this;
        return new ListIterator<Order>() {
            int i = index;

            public boolean hasNext() {
                return tables.length > i;
            }

            public Order next() {
                return tables[i++];
            }

            public boolean hasPrevious() {
                return i > 0;
            }

            public Order previous() {
                return tables[i--];
            }

            public int nextIndex() {
                return i + 1;
            }

            public int previousIndex() {
                return i - 1;
            }

            public void remove() {
                tableOrdersManager.remove(i);
            }

            public void set(Order order) {
                tableOrdersManager.set(i, order);
            }

            public void add(Order order) {
                tableOrdersManager.add(i, order);
            }
        };
    }

    @Override
    public List<Order> subList(int fromIndex, int toIndex) {
        if(fromIndex < 0 || toIndex > size || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();
        if(fromIndex == toIndex)
            return null;

        List<Order> subList = new ArrayList<>(); //todo тип TableOrderManager
        ListIterator<Order> iterator = listIterator(fromIndex);

        while(iterator.previousIndex() < toIndex)
            subList.add(iterator.next());

        return subList;
    }

    public int numOfItems(MenuItem item){
        int count =0;
        for(int i=0;i<size;i++){
            count+=tables[i].numOfItems(item);
        }
            return count;
    }
    //todo это есть метод remove на строчке 102 - удали нафиг
    public int removeOrder(Order table){
        for(int i=0;i<size;i++) {
            if(tables[i].equals(table)) {
                System.arraycopy(tables,i+1,tables,i,size-i-1 );
                size--;
                return i;
            }
        }
        return -1;
    }
    public int removeAllOrders(Order table){
        int count=0;
        for(int i=0;i<size;i++) {
            if(tables[i].equals(table)) {
                System.arraycopy(tables,i+1,tables,i,size-i-1 );
                size--;
                i--;
                count++;
            }
        }
        return count;
    }

    public int firstFreeTable()throws NoFreeTableException{

        for (int i = 0; i < tables.length; i++){
            if (tables[i] == null){
                return i+1;
            }
        }
        throw new NoFreeTableException("Все столики заняты!");
    }
@Override
    public int customersAge(){
        int customerAge=0;
        int count=0;
       for(int i =0;i<size;i++){
            if(tables[i].hasAlcoholicDrink()){
                customerAge += tables[i].getCustomer().getAge();
                count++;
            }
        }
        return customerAge/count;

    }
    public int[] FreeTables(){
        Predicate<Order> freeTables = order -> (order!=null);
        return freeTables.filter(freeTables,tables);
    }

    public int[]OccupiedTables(){
        Predicate<Order> occupiedTables = order -> (order == null);
        return  occupiedTables.filter(occupiedTables,tables);
    }

    public int TotalCost(){
        int sum=0;
        for(int i=0;i<size;i++){
            {
                sum += tables[i].TotalCost();
            }
        }
        return sum;
    }

    public int numOfItems(String itemName){
        int sum=0;
        for(int i=0;i<size;i++){
            sum += tables[i].numOfItems(itemName);
        }
        return sum;
    }

    public Order[]  orders() {
        Order[] orders = new Order[size];
        for(int i=0;i<size;i++){
            orders[i]=tables[i];
        }
        return orders;
    }
  @Override
  public int ordersByDateCount(LocalDate date){
        int ordersByDateCount=0;
        for(int i=0;i<size;i++){
            if(tables[i].getDateTime().toLocalDate().isEqual(date))ordersByDateCount++;
        }
        return ordersByDateCount;
  }
    public List<Order> ordersByDate(LocalDate date){
        ArrayList<Order>ordersByDate = new ArrayList<>();
        for(int i=0;i<size;i++){
            if(tables[i].getDateTime().toLocalDate().isEqual(date)){
                ordersByDate.add(tables[i]);
            }
        }
        return ordersByDate;
    }
    public List<Order> ordersByCustomer(Customer customer) {
        ArrayList<Order>ordersByCustomer = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (tables[i].getCustomer().equals(customer)) {
                ordersByCustomer.add(tables[i]);
            }
        }
        return ordersByCustomer;
    }
}
