package com.company.barBossHouse;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public  class InternetOrder implements Order{
    private static Customer customer;
    private ListNode head;
    private ListNode tail;
    private int size;
    private LocalDateTime dateTime;
    public InternetOrder(){

        head = null;
        tail = null;
        size=0;
        dateTime = LocalDateTime.now();
    }

    public InternetOrder(MenuItem[]items,Customer Customer)throws UnlawfulActionException{
        this();
        customer = Customer;
        for(int i = 0; items[i]!=null; i++) {
            if (items[i] instanceof Drink) {
                Drink drink = (Drink) items[i];
                if (drink.isItAlcoholic() & customer.getAge() < 18) {
                    {
                        throw new UnlawfulActionException("Продажа алкоголя несовершеннолетним запрещена по закону!");
                    }
                }
                if (getDateTime().isAfter((LocalDateTime.of(getDateTime().toLocalDate(), LocalTime.of(22, 0))))) {
                    throw new UnlawfulActionException("Продажа алкоголя после 22:00 запрещена по закону!");
                }
            }
            add(items[i]);
        }

    }
    @Override
    public boolean add(MenuItem item){/*throws UnlawfulActionException{

        if(item instanceof Drink){
            Drink drink = (Drink)item;
            if (drink.isItAlcoholic() && customer.getAge() < 18) {
                {
                    throw new UnlawfulActionException("Продажа алкоголя несовершеннолетним запрещена по закону!");
                }
            }
            if (drink.isItAlcoholic() && getDateTime().getHour()>22 ||  getDateTime().getHour()<6 ) {
                throw new UnlawfulActionException("Продажа алкоголя после 22:00 запрещена по закону!");
            }
        }*/
        ListNode node = new ListNode(item, null);
        if (head == null) {
            head = node;
            tail = node;
            size++;
            return true;
        }
        else {
            tail.next = node;
            tail = node;
            size++;
            return true;
        }
    }

    @Override
    public boolean remove(Object o) {
        ListIterator<MenuItem> listIterator = listIterator();
        while(listIterator.hasNext()) {
            if (listIterator.next().equals(o)) {
                remove(listIterator.previousIndex());
                return true;
            }
        }
        return false;
    }
    private ListNode getNode(int index){
        ListNode node;
        if(index <= size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }
    @Override
    public void add(int index, MenuItem element) {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        ListIterator<MenuItem> listIterator = listIterator(index);

        ListNode node = getNode(index);
        ListNode newNode = new ListNode(element,null);

        newNode.prev = node.prev;
        node.prev.next = newNode;

        newNode.next = node;
        node.prev = newNode;

        size++;
    }

    @Override
    public MenuItem remove(int index) {
        return null;
    }

    @Override
    public boolean hasAlcoholicDrink(){
        ListNode node = head;
        while(node!=null){
            if(node.item instanceof Drink){
                Drink drink = (Drink)node.item;
                if(drink.isItAlcoholic())return true;
            }
            node=node.next;
        }
        return false;
    }
    public boolean remove(String itemName){
        ListNode node = head;
        while (node.next != null) {
            if (node.next.item.getName().equals(itemName)) {
                if(tail == node.next)
                {
                    tail = node;
                }
                node.next = node.next.next;
                size--;
                return true;
            }
            node = node.next;
        }
        return false;
    }

    public boolean remove (MenuItem item){
        ListNode node = head;
        while (node.next != null) {
            if (node.next.item.equals(item)) {
                if(tail == node.next)
                {
                    tail = node;
                }
                node.next = node.next.next;
                size--;
                return true;
            }
            node = node.next;
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
    public boolean addAll(Collection<? extends MenuItem> c) {
        boolean addAll = true;
        for (MenuItem item: c) {
            addAll &= add(item);
        }
        return addAll;
    }

    @Override
    public boolean addAll(int index, Collection<? extends MenuItem> c) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        if(c.size() == 0)
            return false;

        for (Object o : c) {
            add(index++, (MenuItem) o);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Iterator<MenuItem> iterator = iterator();
        ListNode node = head;
        boolean changed = false;
        while(iterator.hasNext()) {
            MenuItem item = iterator.next();

            for (int i = 0; i < size; i++) {
                if (item.equals(node.item)) {
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
        boolean changed = false;
        ListNode node = head;
        for(int i = 0; i < size; i++) {
            Iterator<?> iterator = c.iterator();
            boolean hasTravel = false;
            while (iterator.hasNext()) {
                if (node.item.equals(iterator.next())) {
                    hasTravel = true;
                    break;
                }
            }
            if (!hasTravel) {
                remove(i);
                changed = true;
            }
            node = node.next;
        }
        return changed;
    }

    public int removeAll(MenuItem item){
        int count=0;
        ListNode node = head;
        while (node.next != null) {
            if (node.next.item.equals(item)) {
                if(tail == node.next)
                {
                    tail = node;
                }
                node.next = node.next.next;
                size--;
               count++;
            }
            node = node.next;
        }

        return count;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public int removeAll(String itemName){
        int count=0;
        ListNode node = head;
        while (node.next != null) {
            if (node.next.item.getName().equals(itemName)) {
                if(tail == node.next)
                {
                    tail = node;
                }
                node.next = node.next.next;
                size--;
                count++;
            }
            node = node.next;
        }

        return count;
    }

   public int size(){
        return size;
   }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (MenuItem items: this) {
            if (items.equals(o))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<MenuItem> iterator() {
        return new Iterator<MenuItem>() {
            ListNode node = head;
            int pos = 0;

            public boolean hasNext() {
                return size > pos;
            }

            public MenuItem next() {
               MenuItem employee = node.item;
                node = node.next;
                pos++;
                return employee;
            }
        };
    }

    @Override
    public Object[] toArray() {
        return items();
    }

    public MenuItem[]items(){
       MenuItem[]menuItems=new MenuItem[size];
       ListNode node = head;
      for(int i=0;i<size;i++){
           menuItems[i]=node.item;
           node = node.next;
       }
       return menuItems;
   }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) Arrays.copyOf(items(), size(), a.getClass());
    }

    public int TotalCost(){
       int cost=0;
       ListNode node = head;
        for(int i=0;i<size;i++){
            cost+=node.item.getCost();
            node = node.next;
        }
       return cost;
   }

   @Override
    public int numOfItems(String itemName){
       int count = 0;
       ListNode node = head;
       for(int i=0;i<size;i++){
           if(node.item.getName().equals(itemName))count++;
           node = node.next;
       }
       return count;
   }
   @Override
   public int numOfItems(MenuItem item){
        int count = 0;
        ListNode node = head;
        for(int i=0;i<size;i++){
            if(node.item.equals(item))count++;
            node = node.next;
        }
        return count;
    }

    @Override
    public String[] itemsNames() {
        return new String[0];
    }

    public String[] itemNames() {
    int dishesNamesSize = 0;
    int count = 0;
    ListNode node1 = head;
    ListNode node2 = head;
    for (int i = 0; i < size; i++) {
        for (int j = i + 1; j < size; j++) { //если элемент после i не равен ему, считаем сколько таких
            if (!node1.item.getName().equals(node2.item.getName())) {
                count++;
            }
            node2=node2.next;
        }
        if (count == size - i)
            dishesNamesSize++; //если это количество = общему числу до конца массива, то i не повторяется
        node1 = node1.next;
    }
    String[] dishesNames = new String[dishesNamesSize];
    count = 0;
    int index = 0;
    node1 = head;
    node2 = head;
    for (int i = 0; i < size; i++) {
        for (int j = i + 1; j < size; j++) {
            if (!node1.item.getName().equals(node2.item.getName())) {
                count++;
            }
            node2=node2.next;
        }
        if (count == size - i) { //если элемент не повторяется
            dishesNames[index] = node1.item.getName();
            index++;
        }
        node1 = node1.next;
    }
    return dishesNames;
}
    public MenuItem[] sortedByCost(){
        MenuItem[]items = items();
        Arrays.sort(items,MenuItem::compareTo);
        return items;
    }

    public String toString() {
        ListNode node = head;
      StringBuilder stringBuilder = new StringBuilder("InternetOrder: ")
              .append('\n')
              .append(size)
              .append('\n');
    while(node.next!=null) {
        stringBuilder.append(node.item.toString()).append ('\n');
        node = node.next;
    }
           return String.valueOf(stringBuilder);
    }

    public boolean equals(Object obj) {
       if(obj.getClass().equals(this.getClass())) {
           InternetOrder internetOrder = (InternetOrder) obj;
           if (internetOrder.getCustomer().equals(customer) && internetOrder.size == size && internetOrder.dateTime.isEqual(getDateTime())) {
               InternetOrder order = (InternetOrder) obj;

               if(this.size != order.size)
                   return false;

               return (customer.equals(order.getCustomer()) &&  this.hashCode() == obj.hashCode());
           }
       }
        return false;
    }

    public int hashCode(){
        ListNode node = head;
        int hash=size^customer.hashCode();
        while(node!=null){
            hash^=node.item.hashCode();
            node = node.next;
        }
        return hash;
    }

    @Override
    public MenuItem get(int index) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        return getNode(index).item;
    }

    @Override
    public MenuItem set(int index, MenuItem element) {
        MenuItem item = getNode(index).item;
        getNode(index).item = element;

        return item;
    }

    @Override
    public int indexOf(Object o) {
        ListNode node = head;
        for(int i = 0; i < size; i++){
            if(node.item.equals(o))
                return i;
            node = node.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        ListNode node = tail;
        for(int i = size - 1; i > -1; i--){
            if(node.item.equals(o))
                return i;
            node = node.prev;
        }
        return -1;
    }

    @Override
    public ListIterator<MenuItem> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<MenuItem> listIterator(int index) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        InternetOrder internetOrder = this;
        return new ListIterator<MenuItem>() {
            ListNode node = getNode(index);
            int pos = index;

            public boolean hasNext() {
                return size > pos;
            }

            public MenuItem next() {
                MenuItem employee = node.item;
                node = node.next;
                pos++;
                return employee;
            }

            public boolean hasPrevious() {
                return pos > 0;
            }

            public MenuItem previous() {
               MenuItem item = node.item;
                node = node.prev;
                pos--;
                return item;
            }

            public int nextIndex() {
                return pos + 1;
            }

            public int previousIndex() {
                return pos - 1;
            }

            public void remove() {
                internetOrder.remove(pos);
            }

            public void set(MenuItem item) {
                internetOrder.set(pos, item);
            }

            public void add(MenuItem item) {internetOrder.add(item);
            }
        };
    }

    @Override
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

    public Customer getCustomer() {
        return customer;
    }  //new
    public void setCustomer(Customer customer) {
        InternetOrder.customer = customer;
    }  //new

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}