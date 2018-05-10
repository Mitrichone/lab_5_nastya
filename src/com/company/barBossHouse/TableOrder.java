package com.company.barBossHouse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static java.lang.System.*;

public class TableOrder implements Order{
    private static final int DEFAULT_LENGTH =0;
    private MenuItem[] items;
    private int size;
    private Customer customer;
    private LocalDateTime dateTime;
    //region Конструкторы
    public TableOrder(){
        items = new MenuItem[DEFAULT_LENGTH];
        size=0;
        dateTime = LocalDateTime.now();
    }
    public TableOrder(int num, Customer customer){//throws NegativeSizeException {
        //if(num<0)
         //   throw new NegativeSizeException("Размер массива не может быть отрицательным");
        items = new MenuItem[num];
        size = 0;
        this.customer = customer;
        dateTime = LocalDateTime.now();
    }
    public TableOrder(MenuItem[] menuItems, Customer customer){

        for(int i = 0; i< items.length; i++){
            if(menuItems[i]instanceof Drink) {
                Drink drink = (Drink) menuItems[i];

            }
            items[i]=menuItems[i];
        }
        this.customer = customer;
        dateTime = LocalDateTime.now();
    }
    //endregion

    // region Все get и set
    public MenuItem get(int i){
        if(i<0 || i==size())throw new IndexOutOfBoundsException();
        return items[i];
    }
    public MenuItem set(int index, MenuItem element) {
        /**
         * @throws NullPointerException if the specified element is null and
         *         this list does not permit null elements
         * @throws IndexOutOfBoundsException if the index is out of range
         *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
         *         **/
        if(element == null)throw new NullPointerException();
        if(index<0 || index ==size())throw new IndexOutOfBoundsException();
        MenuItem previous = items[index];
        items[index]=element;
        return previous;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    //endregion

    //region все аdd
    public void add(int index, MenuItem element) {
        if(element == null)throw new NullPointerException();
        if(index < 0 || index >= size - 1)
            throw new IndexOutOfBoundsException();

        expand(0);
        if(index < size - 1)
            System.arraycopy(items, index, items, index + 1, size - (index + 1));

        items[index] = element;
        if(size < items.length)
            size++;
    }
    private void expand(int addSize){
        if (size + addSize > items.length){
            MenuItem[] newItems = new MenuItem[this.size * 2 + addSize];
            System.arraycopy(items, 0, newItems, 0, this.size);
            items = newItems;
        }
    }
    public boolean add(MenuItem newItem){
        if(hasItem(newItem))return false;

        expand(0);

        items[size] = newItem;
        size++;
        return true;
    }
    public boolean hasItem(MenuItem item){
        for(int i = 0; i < size; i++) {
            if (items[i].equals(item))return true;
        }
        return false;
    }

    public boolean addAll(Collection<? extends MenuItem> c) {
        boolean addAll = false;

        expand(c.size());

        for (MenuItem item: c) {
            addAll |= add(item);
        }
        return addAll;
    }

    public boolean addAll(int index, Collection<? extends MenuItem> c) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        if(size + c.size() > items.length){
            MenuItem[] newItems = new MenuItem[size+c.size()];
            System.arraycopy(items, 0, newItems, 0, items.length);
            items = newItems;
        }

        if (index < size)
            System.arraycopy(this.items, index, this.items, index + c.size(),
                    items.length - (index + c.size()));

        if(c.size() == 0)
            return false;

        for (MenuItem o : c) {
            if(!contains(o)) {
                items[index++] = o;
                size++;
            }
        }
        size += c.size();
        return true;
    }


    //endregion

    //region remove
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(items[i], o)) {
                arraycopy(items, i+1, items, i, size-i-1);
                size--;
                return true;
            }
        }
        return false;
    }
    public boolean remove(MenuItem item) {
        for (int i = 0; i < size; i++) {
            if (items[i].equals(item)) {
                arraycopy(items, i+1, items, i, size-i-1);
                size--;
                return true;
            }
        }
        return false;
    }
    public MenuItem remove(int index) {
        if(index<0 || index>=size){
            throw new IndexOutOfBoundsException();
        }
        MenuItem previous = items[index];
        arraycopy(items, index+1, items, index, size-index-1);
        size--;
        return previous;
    }
    public boolean removeAll(Collection<?> c){
        boolean changed = false;
        for (Object o: c) {
            if(contains(o)){
                remove(o);
                changed = true;
            }
        }
        return changed;
    }
    //endregion

    //region contain
    @Override
    public boolean contains(Object o) {
        for (MenuItem item: items) {
            if (item.equals(o))
                return true;
        }
        return false;
    }
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o))
                return false;
        }
        return true;
    }
    //endregion

    public String getType(int index){
        if(items[index]instanceof Drink)return "drink";
        if(items[index]instanceof Dish)return "dish";
        throw new NoSuchElementException();
    }
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        for (MenuItem o: items) {
            if(!c.contains(o)){
                remove(o);
                changed = true;
            }
        }
        return changed;
    }

    public int indexOf(Object o) {
        for(int i = 0; i < size; i++){
            if(items[i].equals(o))
                return i;
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        for(int i = size - 1; i > -1; i--){
            if(items[i].equals(o))
                return i;
        }
        return -1;
    }

    public ListIterator<MenuItem> listIterator() {
        return listIterator(0);
    }

    public ListIterator<MenuItem> listIterator(int index) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        TableOrder tableOrder = this;
        return new ListIterator<MenuItem>() {
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

            public MenuItem next() {
                switch (lastOperation) {
                    case ADD:
                        lastOperation = ListIteratorOperation.NEXT;
                        return items[pos];
                    default:
                        lastOperation = ListIteratorOperation.NEXT;
                        return items[(pos + 1 > size - 1)?(size - 1):pos++];
                }
            }

            public boolean hasPrevious() {
                return pos > 0;
            }

            public MenuItem previous() {
                switch (lastOperation) {
                    case ADD:
                        lastOperation = ListIteratorOperation.PREVIOUS;
                        pos = elementPos;
                        return items[pos];
                    default:
                        lastOperation = ListIteratorOperation.PREVIOUS;
                        return items[(pos - 1 < 0)?0:pos--];
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
                        tableOrder.remove(--pos);
                        break;
                    case PREVIOUS:
                        tableOrder.remove(pos + 1);
                        break;
                    default:
                        illegalState();
                }
                lastOperation = ListIteratorOperation.REMOVE;
            }

            public void set(MenuItem employee) {
                switch (lastOperation) {
                    case NEXT:
                        tableOrder.set(pos - 1, employee);
                        break;
                    case PREVIOUS:
                        tableOrder.set(pos + 1, employee);
                        break;
                    default:
                        illegalState();
                }
                lastOperation = ListIteratorOperation.SET;
            }

            public void add(MenuItem item) {
                if(size == 0) {
                    tableOrder.add(item);
                }
                else{
                    switch (lastOperation){
                        case NONE:
                            tableOrder.add(0, item);
                            pos++;
                            break;
                        case NEXT:
                            elementPos = pos - 1;
                            if(pos - 1 < 0)
                                tableOrder.add(0, item);
                            else
                                tableOrder.add(elementPos, item);
                            pos++;
                            break;
                        case PREVIOUS:
                            elementPos = pos + 2;
                            if(pos + 2 > size - 1)
                                tableOrder.add(item);
                            else
                                tableOrder.add(elementPos, item);
                            break;
                    }
                }
                lastOperation = ListIteratorOperation.ADD;
            }
        };
    }

    public List<MenuItem> subList(int fromIndex, int toIndex) {
        if(fromIndex < 0 || toIndex > size - 1 || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();
        if(fromIndex == toIndex)
            return new TableOrder();

        TableOrder subList = new TableOrder();

        subList.customer = customer;
        subList.dateTime = dateTime;
        subList.items = new MenuItem[toIndex - fromIndex];

        for (int i = fromIndex, j = 0; i < toIndex; i++, j++)
            subList.items[j] = items[i];

        subList.size = toIndex - fromIndex;

        return subList;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            items[i] = null;
        }
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Iterator<MenuItem> iterator() {
        return new Iterator<MenuItem>() {
            int pos = 0;

            public boolean hasNext() {
                return size > pos;
            }

            public MenuItem next() {
                return items[pos++];
            }
        };
    }

    public Object[] toArray() {
        return items();
    }

    public  MenuItem[] items(){
        MenuItem[] answer = new MenuItem[size];
        arraycopy(items, 0, answer, 0, size);
        return answer;
    }

    public <T> T[] toArray (T[] a) {
        return (T[]) Arrays.copyOf(items(), size(), a.getClass());
    }

    public  int TotalCost(){
        int total=0;
        for(int i=0;i<size;i++){
            total+= items[i].getCost();
        }
        return total;
    }

    public int numOfItems(String item){
        int count=0;
        for(int i=0;i<size;i++){
            if(items[i].getName().equals(item))count++;
        }
        return count;
    }
    public int numOfItems(MenuItem item){
        int count=0;
        for(int i=0;i<size;i++){
            if(items[i].equals(item))count++;
        }
        return count;
    }

    public String[] itemsNames() {
        int itemNamesSize = 0;
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) { //если элемент после i не равен ему, считаем сколько таких
                if (!items[i].getName().equals(items[j].getName())) {
                    count++;
                }
            }
            if (count == size - i)
                itemNamesSize++; //если это количество = общему числу до конца массива, то i не повторяется
        }
        String[] itemNames = new String[itemNamesSize];
        count = 0;
        int index = 0;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (!items[i].getName().equals(items[j].getName())){
                    count++;
                }
            }
            if (count == size - i) { //если элемент не повторяется
                itemNames[index] = items[i].getName();
                index++;
            }
        }
        return itemNames;
    }

    public MenuItem[] sort(){
        MenuItem[]items = items();
        Arrays.sort(items,MenuItem::compareTo);
        return items;
    }

    public boolean hasAlcoholicDrink(){
        for(int i=0;i<size;i++){
            if(items[i] instanceof  Drink){
                Drink drink = (Drink)items[i];
                if(drink.isItAlcoholic()){
                    return true;
                }
            }
        }
        return false;
    }

    public String toString() {

        StringBuilder s = new StringBuilder("");
        s.append("TableOrder: ");
        s.append(size);
        s.append('\n');
        for(int i=0;i<size;i++)
            s.append(items[i].toString()).append(" ");
        s.append('\n');
        return String.valueOf(s);
    }
    public boolean equals(Object obj) {
        int count = 0;
        if(obj.getClass().equals(this.getClass())) {
            TableOrder tableOrder = (TableOrder) obj;
            if (tableOrder.getCustomer().equals(customer) && tableOrder.size == size && tableOrder.dateTime.isEqual(getDateTime())) {
                TableOrder order = (TableOrder) obj;
                return (customer.equals(order.getCustomer()) &&  this.hashCode() == obj.hashCode());
            }
        }
        return false;
    }
    public int hashCode(){
        int hash=size^customer.hashCode();
        for(int i=0;i<size;i++){
            hash^=items[i].hashCode();
        }
        return hash;
    }

}

