package com.company.barBossHouse;
class ListNode {
    MenuItem item;
    Order order;
    ListNode next;
    ListNode prev;

        public ListNode(){

        }
        public ListNode(MenuItem item, ListNode next){
            this.next=next;
            this.item = item;
            prev=null;
        }
        public ListNode(Order order, ListNode next){
        this.next=next;
        this.order = order;
        prev=null;
       }

    }
