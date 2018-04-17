package com.company.barBossHouse;

import com.sun.org.apache.xpath.internal.operations.Or;

import java.time.LocalDate;
import java.util.*;

public class InternetOrdersManager implements Deque<Order>,OrdersManager{

    private ListNode head;
    private ListNode tail;
    private int size;

    public InternetOrdersManager() {
        head = null;
        tail = null;
        size = 0;
    }

    public InternetOrdersManager(InternetOrder[] orders) throws AlreadyAddedException {
        for (int i = 0; orders[i] != null; i++) {
            add(orders[i]);
        }
    }

    @Override

    public int customersAge(){
        int customerAgeSum=0;
        ListNode node = head;
        int count=0;
        while(node!=null) {
                   if(node.order.hasAlcoholicDrink()){
                       customerAgeSum += node.order.getCustomer().getAge();
                       count++;
                   }
           node = node.next;
       }
        return customerAgeSum/count;
    }
    @Override
    public int ordersByDateCount(LocalDate date) {
        ListNode node = head;
        int ordersByDateCount=0;
        while(node!=null) {
            if(node.order.getDateTime().toLocalDate().isEqual(date))ordersByDateCount++;
        }
        return ordersByDateCount;
    }
    @Override
    public List<Order> ordersByDate(LocalDate date) {
        ListNode node = head;
        ArrayList<Order> ordersByDate = new ArrayList<>();
        while (node != null) {
            if (node.order.getDateTime().toLocalDate().isEqual(date)) {
                ordersByDate.add(node.order);
            }
            node = node.next;
        }
        return ordersByDate;
    }
    @Override
    public List<Order> ordersByCustomer(Customer customer) {
        ListNode node = head;
        ArrayList<Order>ordersByCustomer = new ArrayList<>();
        while(node!=null) {
            if (node.order.getCustomer().equals(customer)) {
                ordersByCustomer.add(node.order);
            }
            node = node.next;
        }
        return ordersByCustomer;
    }

    public boolean add(InternetOrder order) throws AlreadyAddedException {
        ListNode node = new ListNode();
        node = head;
        ListNode newNode = new ListNode(order,null);
        while(node!=null) {
            if (head == null) {
                head = newNode;
                size++;
            } else {
                if (node.order.getCustomer().equals(newNode.order.getCustomer()) & node.order.getDateTime().isEqual(newNode.order.getDateTime())) {
                    throw new AlreadyAddedException("&#x41d;&#x435;&#x432;&#x43e;&#x437;&#x43c;&#x43e;&#x436;&#x43d;&#x43e; &#x43f;&#x43e;&#x432;&#x442;&#x43e;&#x440;&#x43d;&#x43e; &#x434;&#x43e;&#x431;&#x430;&#x432;&#x438;&#x442;&#x44c; &#x437;&#x430;&#x43a;&#x430;&#x437;");
                }


                tail = node;
                tail.next = tail;
                size++;
            }
            node = node.next;
        }
        return true;
    }

    public Order getFirstOrder() {
        return head.order;
    }
    public Order getAndRemoveFirstOrder() {
        Order answer = head.order;
        ListNode node = head;
        while (node.next != null) {
            if (node == head) {
                if (tail == node.next) {
                    tail = node;
                }
                node.next = node.next.next;
                size--;
            }
            node = node.next;
        }
        return answer;
    }

    @Override
    public void addFirst(Order order) {
        if(order==null)throw new NullPointerException();
        ListNode oldHead = head;
        head=new ListNode();
        head.order=order;
        head.next=null;
        if(isEmpty()){
            tail=head;
        }else{
            head.next=oldHead;
        }
        size++;
    }

    @Override
    public void addLast(Order order) {
        if(order==null)throw new NullPointerException();
        ListNode oldTail =tail;
        tail=new ListNode();
        tail.order=order;
        tail.next=null;
        if(isEmpty()){
            head=tail;
        }else{
            oldTail.next=tail;
        }
        size++;
    }

    @Override
    public boolean offerFirst(Order order) {
        if(order==null)
        {
            return false;
        }
        else
            {
            ListNode oldHead = head;
            head = new ListNode();
            head.order = order;
            head.next = null;
            if (isEmpty()) {
                tail = head;
            } else {
                head.next = oldHead;
            }
            size++;
            return true;
        }
    }

    @Override
    public boolean offerLast(Order order) {
        if(order==null){
            return false;
        }else {
            ListNode oldTail = tail;
            tail = new ListNode();
            tail.order = order;
            tail.next = null;
            if (isEmpty()) {
                head = tail;
            } else {
                oldTail.next = tail;
            }
            size++;
            return true;
        }
    }

    @Override
    public Order removeFirst() {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        else {
            ListNode node = head;
            node.prev.next = node.next;
            node.next.prev = node.prev;
            return node.order;
        }
    }

    @Override
    public Order removeLast() {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        else {
            ListNode node = tail;
            node.prev.next = node.next;
            node.next.prev = node.prev;
            return node.order;
        }
    }

    @Override
    public Order pollFirst() {
        if(isEmpty()){
            return null;
        }
        else {
            ListNode node = head;
            node.prev.next = node.next;
            node.next.prev = node.prev;
            return node.order;
        }
    }

    @Override
    public Order pollLast() {
        if(isEmpty()){
            return null;
        }
        else {
            ListNode node = head;
            node.prev.next = node.next;
            node.next.prev = node.prev;
            return node.order;
        }
    }

    @Override
    public Order getFirst() {
        if(isEmpty())throw new NoSuchElementException();
        return head.order;
    }

    @Override
    public Order getLast() {
        if(isEmpty())throw new NoSuchElementException();
        return tail.order;
    }

    @Override
    public Order peekFirst() {
        if(isEmpty())return null;
        return head.order;
    }

    @Override
    public Order peekLast() {
        if(isEmpty())return null;
        return tail.order;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        if(o==null)throw new NullPointerException();
        Order object = (Order)o;
        ListNode node = head;
        while(node.next!=null){
            if(node.order.equals(object)){
                node.prev.next = node.next;
                node.next.prev = node.prev;
                return true;
            }
            node=node.next;
        }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        if(o==null)throw new NullPointerException();
        Order object = (Order)o;
        ListNode node = tail;
        while(node.prev!=head){
            if(node.order.equals(object)){
                node.prev.next = node.next;
                node.next.prev = node.prev;
                return true;
            }
            node=node.prev;
        }
        return false;
    }

    @Override
    public boolean add(Order order) {
        ListNode node = new ListNode(order, null);
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
    public boolean offer(Order order) {
        if(order==null)throw new NullPointerException();
        ListNode node = new ListNode(order, null);
        if (head == null) {
            head = node;
            tail = node;
            size++;
            return true;
        }
        else{
            tail.next = node;
            tail = node;
            size++;
            return true;
        }
    }

    @Override
    public Order remove() {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        else {
            ListNode node = head;
            node.prev.next = node.next;
            node.next.prev = node.prev;
            return node.order;
        }
    }

    @Override
    public Order poll() {
       return pollFirst();
    }

    @Override
    public Order element() {
        if(isEmpty())throw new NoSuchElementException();
        return head.order;
    }

    @Override
    public Order peek() {
       return peekFirst();
    }

    @Override
    public void push(Order order) {
       addFirst(order);
    }

    @Override
    public Order pop() {
       return removeFirst();
    }

    @Override
    public boolean remove(Object o) {
        ListNode node = head;
        for(int i=0;i<size;i++){
            if(node.order.equals((Order)o)){
                node.prev.next = node.next;
                node.next.prev = node.prev;
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
    public boolean addAll(Collection<? extends Order> c) {
        boolean addAll = true;
        for (Order order: c) {
            addAll &= add(order);
        }
        return addAll;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        ListNode node = head;
        boolean changed = false;
        for (Object o :c) {

            for (int i = 0; i < size; i++) {
                if(node.order.equals(o)) {
                    remove(i);
                    changed = true;
                    break;
                }
            }
            node=node.next;
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        ListNode node = head;
        for(int i = 0; i < size; i++) {
            Iterator<?> iterator = c.iterator();
            boolean hasItem = false;
            while (iterator.hasNext()) {
                if (node.item.equals(iterator.next())) {
                    hasItem = true;
                    break;
                }
            }
            if (!hasItem) {
                remove(i);
                changed = true;
            }
            node = node.next;
        }
        return changed;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Order tables: this) {
            if (tables.equals(o))
                return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<Order> iterator() {
        return new Iterator<Order>() {
            ListNode node = head;
            int pos = 0;

            public boolean hasNext() {
                return size > pos;
            }

            public Order next() {
                Order order = node.order;
                node = node.next;
                pos++;
                return order;
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
    public Iterator<Order> descendingIterator() {
        return new Iterator<Order>() {
            ListNode node = tail;
            int pos = size - 1;

            public boolean hasNext() {
                return pos>-1;
            }

            public Order next() {
                Order order = node.order;
                node = node.prev;
                pos--;
                return order;
            }
        };
    }

    public Order[]  orders() {
        Order[] orders = new Order[size];
        ListNode node = head;
        for (int i = 0; i < size; i++) {
            orders[i] = node.order;
            node = node.next;
        }
        return orders;
    }

    public int TotalCost() {
        int cost = 0;
        ListNode node = head;
        for (int i = 0; i < size; i++) {
            cost += node.item.getCost();
            node = node.next;
        }
        return cost;
    }

    public int numOfItems(String orderName) {
        int count = 0;
        ListNode node = head;
        for (int i = 0; i < size; i++) {
            if (node.item.getName().equals(orderName)) count++;
            node = node.next;
        }
        return count;
    }

    public int numOfItems(MenuItem item) {
        int count = 0;
        ListNode node = head;
        for (int i = 0; i < size; i++) {
            if (node.item.equals(item)) count++;
            node = node.next;
        }
        return count;
    }

    @Override
    public String toString() {
        ListNode node = head;
        StringBuilder stringBuilder = new StringBuilder("InternetOrderManager: ")
                .append('\n')
                .append(size)
                .append('\n');
        while(node.next!=null) {
            stringBuilder.append(node.order.toString()).append ('\n');
            node = node.next;
        }
        return String.valueOf(stringBuilder);
    }
}

