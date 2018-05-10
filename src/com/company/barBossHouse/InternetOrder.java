package com.company.barBossHouse;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public  class InternetOrder implements Order{
    private Customer customer;
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

    public InternetOrder(MenuItem[]items,Customer customer)throws UnlawfulActionException{
        this();
        this.customer = customer;
        for(int i = 0; items[i]!=null; i++) {
            if (items[i] instanceof Drink) {
                Drink drink = (Drink) items[i];
                if (drink.isItAlcoholic() & customer.getAge() < 18) {
                    {
                        throw new UnlawfulActionException("Продажа алкоголя несовершеннолетним запрещена по закону!");
                    }
                }
                if (getDateTime().getHour() > 22 || getDateTime().getHour() < 6) {
                    throw new UnlawfulActionException("Продажа алкоголя после 22:00 запрещена по закону!");
                }
            }
            add(items[i]);
        }

    }

    public String getType(int index){
        ListNode node = this.getNode(index);
        if(node.item instanceof Drink)return "drink";
        if(node.item instanceof Dish)return "dish";
        throw new NoSuchElementException();
    }
    @Override
    public boolean add(MenuItem item){

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
        ListNode node = head;
        int count=0;
        if(index>=size || index<0){
            throw new IndexOutOfBoundsException();
        }
        while(node.next != null) {
            if(count==index){
                MenuItem item = node.item;
                if(tail == node.next)
                {
                    tail = node;
                }
                node.next = node.next.next;
                size--;
                return item;
            }
            node = node.next;
            count++;
        }
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

        ListNode node = getNode(index);
        for(Object o:c){
            if(node==null)break;
            node.item=(MenuItem)o;
            node=node.next;
        }
        return true;
    }


    @Override
    public boolean removeAll(Collection<?> c) {
        ListNode node = head;
        boolean changed = false;
        while(node!=null){
            if(c.contains(node)){
                remove(node);
                changed=true;
            }
            node=node.next;
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        ListNode node = head;
        boolean changed = false;
        while(node!=null){
            if(!c.contains(node)){
                remove(node);
                changed=true;
            }
            node=node.next;
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
        ListNode node = head, next;

        while(node.next!=null){
            next = node.next;
            node.prev = null;
            node.next = null;
            node.item=null;
            node=next;
        }
        size = 0;
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
                if(node==null)throw new NoSuchElementException();
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

    MenuItem[]items(){
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
    public MenuItem[] sort(){
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
        ListNode node = getNode(index);
        MenuItem old = node.item;
        node.item = element;

        return old;
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
                if(node==tail)throw new NoSuchElementException();
                MenuItem item = node.item;
                node = node.next;
                pos++;
                return item;
            }

            public boolean hasPrevious() {
                return pos > 0;
            }
            public MenuItem previous() {
                if(node==head)throw new NoSuchElementException();
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
                internetOrder.remove(node.prev);
            }

            public void set(MenuItem item) {
                internetOrder.set(pos, item);
            }

            public void add(MenuItem item) {
                internetOrder.add(index,item);
            }
        };
    }

    @Override
    public List<MenuItem> subList(int fromIndex, int toIndex) {
        if(fromIndex < 0 || toIndex > size )
            throw new IndexOutOfBoundsException();
        if(fromIndex > toIndex)
            throw new IllegalArgumentException();

        InternetOrder subList = new InternetOrder();
        ListIterator<MenuItem> iterator = listIterator(fromIndex);

        while(iterator.previousIndex() < toIndex)
            subList.add(iterator.next());

        return subList;

    }

    public Customer getCustomer() {
        return customer;
    }  //new
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}