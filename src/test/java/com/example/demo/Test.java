package com.example.demo;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @author liangxu
 * @version 1.0
 * @since 2022-03-21 16:08:15
 **/
public class Test {
    public static void main(String[] strs) {
//        int count = 0;
//        int[] arrays = new int[] {0, 0, 1, 1, 1, 0, 1, 0, 0, 1};
//        int left = 0;
//        int right = arrays.length - 1;
//        while (true) {
//            while (arrays[left] == 0) {
//                left++;
//            }
//            while (arrays[right] == 1) {
//                right--;
//            }
//            if (left >= right) {
//                break;
//            } else {
//                int temp = arrays[left];
//                arrays[left] = arrays[right];
//                arrays[right] = temp;
//                count++;
//            }
//        }
//       System.out.println(count);
//        for (int array : arrays) {
//            System.out.println(","+array);
//        }
        Deque<String> stack = new ArrayDeque<String>();

        ListNode listNode1 = new ListNode(9);
        listNode1.next=new ListNode(3);
        listNode1.next.next=new ListNode(7);

        ListNode listNode2 = new ListNode(1);
        listNode2.next=new ListNode(7);
        listNode2.next.next=new ListNode(3);

        ListNode listNode = addTwoNumbers(listNode1, listNode2);
        System.out.println();

    }

        public static  ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode pre = new ListNode(0);
            ListNode cur = pre;
            int carry = 0;
            while(l1 != null || l2 != null) {
                int x = l1 == null ? 0 : l1.val;
                int y = l2 == null ? 0 : l2.val;
                int sum = x + y + carry;

                carry = sum / 10;
                sum = sum % 10;
                cur.next = new ListNode(sum);

                cur = cur.next;
                if(l1 != null)
                    l1 = l1.next;
                if(l2 != null)
                    l2 = l2.next;
            }
            if(carry == 1) {
                cur.next = new ListNode(carry);
            }
            return pre.next;
        }


    //????????????
    static void levelShow(TreeNode node){
        //????????????
        LinkedList<TreeNode> queue = new LinkedList<>();
        //????????????????????????
        queue.addLast(node);
        //??????????????????
        TreeNode p;
        while(queue.size()>0){
            //?????????
            p = queue.removeFirst();
            System.out.print(p.value+" ");
            //??????????????????
            if (p.left!=null) queue.addLast(p.left);
            //??????????????????
            if (p.right!=null) queue.addLast(p.right);
        }
    }




    //??????????????????
    public static  int maxSum(int[] num){
        int curSum = 0;
        int curMaxSum = -99999999;
        int start = 0;
        int end = 0;

        for(int i=0;i<num.length;i++){
            if(curSum<=0){
                curSum = num[i];
                start = i;
            }
            else{
                curSum += num[i];
            }
            if(curSum>curMaxSum){
                curMaxSum = curSum;
                end = i;
            }
        }
        for(int i = start;i<=end;i++){
            System.out.println(num[i]);
        }
        return curMaxSum;
    }

    /**
     * ??????????????????????????????, ??????????????????
     * @param a
     * @param n
     */
    public static void bubbleSort1(int[] a, int n){
        int i, j;

        for(i=0; i<n; i++){//??????n??????????????????
            for(j=1; j<n-i; j++){
                if(a[j-1] > a[j]){//?????????????????????????????????????????????
                    //??????a[j-1]???a[j]
                    int temp;
                    temp = a[j-1];
                    a[j-1] = a[j];
                    a[j]=temp;
                }
            }
        }
    }

    public static String reverse1(String s) {
        int length = s.length();
        if (length <= 1)
            return s;
        String left = s.substring(0, length / 2);
        String right = s.substring(length / 2, length);
        return reverse1(right) + reverse1(left);  //????????????
    }

    public static String reverse2(String s) {
        int length = s.length();
        String reverse = "";
        for (int i = 0; i < length; i++)
            reverse = s.charAt(i) + reverse;
        return reverse;
    }

    public static String reverse3(String s) {
        char[] array = s.toCharArray();
        String reverse = "";
        for (int i = array.length - 1; i >= 0; i--)
            reverse += array[i];
        return reverse;
    }

    public static String reverse5(String orig) {
        char[] s = orig.toCharArray();
        int n = s.length - 1;
        int halfLength = n / 2;
        for (int i = 0; i <= halfLength; i++) {
            char temp = s[i];
            s[i] = s[n - i];
            s[n - i] = temp;
        }
        return new String(s);
    }

    public static String reverse7(String s) {
        char[] str = s.toCharArray();
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < str.length; i++)
            stack.push(str[i]);

        String reversed = "";
        for (int i = 0; i < str.length; i++)
            reversed += stack.pop();

        return reversed;
    }



}