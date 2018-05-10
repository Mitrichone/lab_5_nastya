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

    public TableOrdersManager(){
    }
    public TableOrdersManager(int length){
        tables = new TableOrder[length];
        size= DEFAULT_SIZE;
    }
    public Order ReturnOrder(int num){
        return tables[num];
    }

    public void addItem(int num, MenuItem item) throws UnlawfulActionException{
        if(item instanceof Drink){
            Drink drink = (Drink)item;
            if(drink.isItAlcoholic()
                    & tables[num].getCustomer().getAge()<18
                    & tables[num].getDateTime().getHour()>22 || tables[num].getDateTime().getHour()<6) {
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
                if(pos==size-1)throw new NoSuchElementException();
                return tables[pos++];
            }

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
    public boolean remove(Object o)  {
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
            if(contains(o))throw new AlreadyAddedException();
            tables[index++]=(Order)o;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {

        boolean changed = false;
        for (Object o: c) {
            if(contains(o)){
                remove(o);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {

        boolean changed = false;
        for (Order o: tables) {
            if(!c.contains(o)){
                remove(o);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        for(int i=0;i<tables.length;i++){
            tables[i]=null;
        }
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
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        TableOrdersManager tableOrdersManager = this;
        return new ListIterator<Order>() {
            int pos = index;
            int elementPos = 0;
            ListIteratorOperation lastOperation = ListIteratorOperation.NONE;
            private void illegalState(){
                switch (lastOperation){
                    case NONE:
                        throw new IllegalStateException("Не были вызваны методы \"next()\" или \"previous()\"");
                    case ADD:
                        throw new IllegalStateException("Последний вызов: \"add()\"");
                    case REMOVE:
                        throw new IllegalStateException("Последний вызов: \"remove()\"");
                }
            }

            public boolean hasNext() {
                return size-1 > pos;
            }

            public Order next() {
                switch (lastOperation) {
                    case ADD:
                        lastOperation = ListIteratorOperation.NEXT;
                        return tables[pos];
                    default:
                        lastOperation = ListIteratorOperation.NEXT;
                        return tables[(pos + 1 > size - 1)?(size - 1):pos++];
                }
            }

            public boolean hasPrevious() {
                return pos > 0;
            }

            public Order previous() {
                switch (lastOperation) {
                    case ADD:
                        lastOperation = ListIteratorOperation.PREVIOUS;
                        pos = elementPos;
                        return tables[pos];
                    default:
                        lastOperation = ListIteratorOperation.PREVIOUS;
                        return tables[(pos - 1 < 0)?0:pos--];
                }
            }

            public int nextIndex() {
                return pos + 1;
            }

            public int previousIndex() {
                return pos - 1;
            }

            public void remove() {
                switch (lastOperation) {
                    case NEXT:
                        tableOrdersManager.remove(--pos);
                        break;
                    case PREVIOUS:
                        tableOrdersManager.remove(pos + 1);
                        break;
                    default:
                        illegalState();
                }
                lastOperation = ListIteratorOperation.REMOVE;
            }

            @Override
            public void set(Order menuItems) {

            }

            @Override
            public void add(Order menuItems) {

            }
        };
    }

    @Override
    public List<Order> subList(int fromIndex, int toIndex) {
        if(fromIndex < 0 || toIndex > size || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();
        if(fromIndex == toIndex)
            return null;

        TableOrdersManager subList = new TableOrdersManager(toIndex - fromIndex);
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
