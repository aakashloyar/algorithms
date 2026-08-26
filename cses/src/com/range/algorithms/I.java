package com.range.algorithms;
import java.util.*;
public class I {
    static void main() {

        int[] arr = {5, 2, 8, 1, 9, 3, 7, 4};

        ST st = new ST(arr, arr.length);

        // Build segment tree
        st.build(0, arr.length - 1, 0);

        System.out.println("Original array:");
        System.out.println(Arrays.toString(arr));

        System.out.println("\n--- Range Minimum Queries ---");

        System.out.println("Minimum [0, 7] = "
                + st.query(0, 7, 0, arr.length - 1, 0));

        System.out.println("Minimum [0, 3] = "
                + st.query(0, 3, 0, arr.length - 1, 0));

        System.out.println("Minimum [2, 5] = "
                + st.query(2, 5, 0, arr.length - 1, 0));

        System.out.println("Minimum [4, 7] = "
                + st.query(4, 7, 0, arr.length - 1, 0));

        System.out.println("Minimum [1, 6] = "
                + st.query(1, 6, 0, arr.length - 1, 0));

        System.out.println("Minimum [3, 3] = "
                + st.query(3, 3, 0, arr.length - 1, 0));


        // --------------------------------------------------
        // Point Update
        // --------------------------------------------------

        System.out.println("\n--- Point Update ---");

        System.out.println("Updating arr[3] from 1 to 10...");

        st.update(0, arr.length - 1, 0, 3, 10);

        System.out.println("Updated array:");
        System.out.println(Arrays.toString(arr));


        // --------------------------------------------------
        // Queries after update
        // --------------------------------------------------

        System.out.println("\n--- Queries After Update ---");

        System.out.println("Minimum [0, 7] = "
                + st.query(0, 7, 0, arr.length - 1, 0));

        System.out.println("Minimum [0, 3] = "
                + st.query(0, 3, 0, arr.length - 1, 0));

        System.out.println("Minimum [2, 5] = "
                + st.query(2, 5, 0, arr.length - 1, 0));

        System.out.println("Minimum [4, 7] = "
                + st.query(4, 7, 0, arr.length - 1, 0));
    }
}
class ST {
    int n;
    int[] seg;
    int[] arr;
    ST(int[] arr, int n) {
        this.n=n;
        this.seg=new int[4*n];
        this.arr=arr;
    }
    int build(int low, int high, int ind) {
        if(low==high) return seg[ind]=arr[low];
        int m= low+(high-low)/2;
        int left=build(low,m,ind*2+1);
        int right=build(m+1,high,ind*2+2);
        return seg[ind]=push(left,right);
    }
    int query(int s, int e, int low, int high, int ind) {
        if (high < s || e < low) return Integer.MAX_VALUE;
        if(s<=low && high<=e) return seg[ind];
        int m = low + (high - low) / 2;
        int left = query(s, e, low, m, ind * 2 + 1);
        int right = query(s, e, m + 1, high, ind * 2 + 2);
        return push(left, right);
    }
    int update(int low, int high, int ind, int p, int val) {
        if(low==high) {
            arr[p]=val;
            return seg[ind]=val;
        }
        if(p<low || high<p) return seg[ind];
        int m=low+(high-low)/2;
        int left= update(low, m,ind*2+1, p, val);
        int right= update(m+1, high,ind*2+2, p, val);
        return seg[ind]=push(left, right);
    }
    int push(int left, int right) {
        return Math.min(left,right);
    }
}