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
    public TableOrder(int num, Customer customer)throws NegativeSizeException {
        if(num<0)
            throw new NegativeSizeException("Размер массива не может быть отрицательным");
        items = new MenuItem[num];
        size = 0;
        this.customer = customer;
        dateTime = LocalDateTime.now();
    }
    public TableOrder(MenuItem[] menuItems, Customer customer)throws UnlawfulActionException{

        for(int i = 0; i< items.length; i++){
            if(menuItems[i]instanceof Drink) {
                Drink drink = (Drink) menuItems[i];
                if (drink.isItAlcoholic() & customer.getAge() < 18) {
                    {
                        throw new UnlawfulActionException("Продажа алкоголя несовершеннолетним запрещена по закону!");
                    }
                }
                if (getDateTime().isAfter((LocalDateTime.of(getDateTime().toLocalDate(), LocalTime.of(22, 0))))) {
                        throw new UnlawfulActionException("Продажа алкоголя после 22:00 запрещена по закону!");
                }

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
     if(index<0 || index>=size())throw new IndexOutOfBoundsException();
     if(element==null)throw  new NullPointerException();
     items[index]=element;
    }

    public boolean add(MenuItem newItem){
        if(newItem==null)throw new NullPointerException();
        if(items.length==0){
            MenuItem[]newItems = new MenuItem[1];
            newItems[0]=newItem;
            items=newItems;
            return true;
        } else if(items.length>size)
        {
            items[size] = newItem;
            size++;
            return true;
        } else
        {
            MenuItem[] newItems = new MenuItem[items.length * 2];
            arraycopy(items, 0, newItems, 0, items.length);
            newItems[items.length] = newItem;
            items = newItems;
            return true;
        }
    }

    public boolean addAll(Collection<? extends MenuItem> c) {
        boolean addAll = true;

        if(size + c.size() > items.length){
            MenuItem[] newEmployees = new MenuItem[size+c.size()];
            System.arraycopy(this.items, 0, newEmployees, 0, this.size);
            this.items = newEmployees;
        }
        for (MenuItem item: c) {
            addAll &= add(item);
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

        for (Object o : c) {
            items[index++] = (MenuItem) o;
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
    public boolean remove(String name) {
        for (int i = 0; i < size; i++) {
            if (items[i].getName().equals(name)) {
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
    }  //new
    public MenuItem remove(int index) {
       if(index<0 || index>=size){
           throw new IndexOutOfBoundsException();
       }
       MenuItem previous = items[index];
        arraycopy(items, index+1, items, index, size-index-1);
        size--;
        return previous;
    }
    public boolean removeAll(Collection<?> c) {
        Iterator<MenuItem> iterator = iterator();
        boolean changed = false;
        while(iterator.hasNext()) {
            MenuItem item = iterator.next();

            for (int i = 0; i < size; i++) {
                if (item.equals(items[i])) {
                    remove(i);
                    changed = true;
                    break;
                }
            }
        }
        return changed;
    }
    public int removeAll(MenuItem item) {
        int count=0;
        for(int i=0;i<size;i++){
            if(items[i].equals(item))
            {
                arraycopy(items, i+1, items, i, size-i-1);
                size--;
                count++;
                i--;
            }
        }
        return count;
    }
    public int removeAll(String i_name) {
        int count=0;
        for(int i=0;i<size;i++){
            if(items[i].getName().equals(i_name))
            {
                arraycopy(items, i+1, items, i, size-i-1);
                size--;
                count++;
                i--;
            }
        }
        return count;
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

    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        for(int i = 0; i < size; i++) {
            Iterator<?> iterator = c.iterator();
            boolean hasItem = false;
            while (iterator.hasNext()) {
                if (items[i].equals(iterator.next())) {
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

            public boolean hasNext() {
                return items.length > pos;
            }

            public MenuItem next() {
                return items[pos++];
            }

            public boolean hasPrevious() {
                return pos > 0;
            }

            public MenuItem previous() {
                return items[pos--];
            }

            public int nextIndex() {
                return pos + 1;
            }

            public int previousIndex() {
                return pos - 1;
            }

            public void remove() {
                tableOrder.remove(pos);
            }

            public void set(MenuItem employee) {
                tableOrder.set(pos, employee);
            }

            public void add(MenuItem employee) {
                tableOrder.add(pos, employee);
            }
        };
    }

    public List<MenuItem> subList(int fromIndex, int toIndex) {
        if(fromIndex < 0 || toIndex > size || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();
        if(fromIndex == toIndex)
            return null;

        List<MenuItem> subList = new ArrayList<>();
        ListIterator<MenuItem> iterator = listIterator(fromIndex);

        while(iterator.previousIndex() < toIndex)
            subList.add(iterator.next());

        return subList;
    }

   //region unlawfulEx
/*
    public boolean add(MenuItem newItem)throws UnlawfulActionException {
        if(newItem instanceof Drink){
            Drink drink = (Drink)newItem;
            if (drink.isItAlcoholic() & customer.getAge() < 18) {
                {
                    throw new UnlawfulActionException("Продажа алкоголя несовершеннолетним запрещена по закону!");
                }
            }
            if (getDateTime().isAfter((LocalDateTime.of(getDateTime().toLocalDate(), LocalTime.of(22, 0))))) {
                throw new UnlawfulActionException("Продажа алкоголя после 22:00 запрещена по закону!");
            }
        }
*/
   //endregion

    public void clear() {
        items = new MenuItem[items.length];
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

    public MenuItem[] sortedByCost(){
        MenuItem[]items = items();
        for (int i = this.items.length-1; i>=0; i--) //sorry but bubble sort
        {
            for (int j=0; j<i; j++) {
                if (items[i] != null) {
                    if (items[j] != null) {
                        if (items[j].getCost() < items[j + 1].getCost()) {
                            MenuItem tmp = items[j];
                            items[j] = items[j + 1];
                            items[j + 1] = tmp;
                        }
                    }
                }
            }
        }
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

