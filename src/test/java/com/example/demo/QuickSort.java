package com.example.demo;

import java.util.Arrays;
 
public class QuickSort {
 
    public static void main(String[] args) {
        int[] num = { 1, 3, 4, 8, 5, 10, 22, 15, 16 };
        QuickSort.quickSort(num, 0, num.length - 1);
        System.out.println(Arrays.toString(num));
    }
 
    public static void quickSort(int[] a, int start, int end) {
        // 该值定义了从哪个位置开始分割数组
        int ref;
        if (start < end) {
            // 调用partition方法对数组进行排序
            ref = partition(a, start, end);
            // 对分割之后的两个数组继续进行排序
            quickSort(a, start, ref - 1);
            quickSort(a, ref + 1, end);
        }
    }
 
    /**
     * 选定参考值对给定数组进行一趟快速排序
     * 
     * @param a  数组
     * @param start （切分）每个数组的第一个的元素的位置
     * @param end （切分）每个数组的最后一个的元素位置
     * @return 下一次要切割数组的位置
     */
 
    public static int partition(int[] a, int start, int end) {
        // 取数组的第一个值作为参考值（关键数据）
        int refvalue = a[start];
        // 从数组的右边开始往左遍历，直到找到小于参考值的元素
        while (start < end) {
            while (end > start && a[end] >= refvalue) {
                end--;
            }
            // 将元素直接赋予给左边第一个元素，即pivotkey所在的位置
            a[start] = a[end];
            // 从序列的左边边开始往右遍历，直到找到大于基准值的元素
            while (end > start && a[start] <= refvalue) {
                start++;
            }
            a[end] = a[start];
            return end;
        }
        // 最后的start是基准值所在的位置
        a[start] = refvalue;
        return start;
    }


}