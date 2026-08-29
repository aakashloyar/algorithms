package com.range.algorithms;
import java.util.*;
public class A2 {
    static void main() {

        int[] arr = {5, 2, 8, 1, 9, 3, 7, 4};

        LP st = new LP(arr, arr.length);

        // Build segment tree
        st.build(0, arr.length - 1, 0);

        System.out.println("Initial array: "
                + Arrays.toString(arr));

        // --------------------------------------------------
        // 1. Initial query
        // --------------------------------------------------

        System.out.println("\n1. Query [0, 7]");
        System.out.println("Expected: 1");
        System.out.println("Actual  : "
                + st.query(0, 7, 0, arr.length - 1, 0));


        // --------------------------------------------------
        // 2. Range update [2, 5] = 10
        // --------------------------------------------------

        System.out.println("\n2. Update [2, 5] = 10");

        st.update(0, arr.length - 1, 0, 2, 5, 10);

        System.out.println("Array should be: "
                + "[5, 2, 10, 10, 10, 10, 7, 4]");

        System.out.println("Actual array  : "
                + Arrays.toString(arr));


        // --------------------------------------------------
        // 3. Queries after first update
        // --------------------------------------------------

        System.out.println("\n3. Query [0, 7]");
        System.out.println("Expected: 2");
        System.out.println("Actual  : "
                + st.query(0, 7, 0, arr.length - 1, 0));

        System.out.println("\nQuery [2, 5]");
        System.out.println("Expected: 10");
        System.out.println("Actual  : "
                + st.query(2, 5, 0, arr.length - 1, 0));


        // --------------------------------------------------
        // 4. Overlapping update [1, 4] = 1
        // --------------------------------------------------

        System.out.println("\n4. Update [1, 4] = 1");

        st.update(0, arr.length - 1, 0, 1, 4, 1);

        System.out.println("Array should be: "
                + "[5, 1, 1, 1, 1, 10, 7, 4]");

        System.out.println("Actual array  : "
                + Arrays.toString(arr));

        System.out.println("Query [0, 7]");
        System.out.println("Expected: 1");
        System.out.println("Actual  : "
                + st.query(0, 7, 0, arr.length - 1, 0));


        // --------------------------------------------------
        // 5. Update entire array [0, 7] = 6
        // --------------------------------------------------

        System.out.println("\n5. Update [0, 7] = 6");

        st.update(0, arr.length - 1, 0, 0, 7, 6);

        System.out.println("Array should be: "
                + "[6, 6, 6, 6, 6, 6, 6, 6]");

        System.out.println("Actual array  : "
                + Arrays.toString(arr));

        System.out.println("Query [0, 7]");
        System.out.println("Expected: 6");
        System.out.println("Actual  : "
                + st.query(0, 7, 0, arr.length - 1, 0));


        // --------------------------------------------------
        // 6. Partial update after lazy update
        // --------------------------------------------------

        System.out.println("\n6. Update [3, 6] = 2");

        st.update(0, arr.length - 1, 0, 3, 6, 2);

        System.out.println("Array should be: "
                + "[6, 6, 6, 2, 2, 2, 2, 6]");

        System.out.println("Actual array  : "
                + Arrays.toString(arr));


        // --------------------------------------------------
        // 7. Final queries
        // --------------------------------------------------

        System.out.println("\n7. Final Queries");

        System.out.println("Query [0, 7]");
        System.out.println("Expected: 2");
        System.out.println("Actual  : "
                + st.query(0, 7, 0, arr.length - 1, 0));

        System.out.println("\nQuery [0, 2]");
        System.out.println("Expected: 6");
        System.out.println("Actual  : "
                + st.query(0, 2, 0, arr.length - 1, 0));

        System.out.println("\nQuery [3, 6]");
        System.out.println("Expected: 2");
        System.out.println("Actual  : "
                + st.query(3, 6, 0, arr.length - 1, 0));

        System.out.println("\nQuery [7, 7]");
        System.out.println("Expected: 6");
        System.out.println("Actual  : "
                + st.query(7, 7, 0, arr.length - 1, 0));
    }
}
class LP {
    int n;
    int[] seg;
    int[] lazy;
    int[] arr;
    LP(int[] arr,int n) {
        this.n=n;
        this.seg=new int[4*n];
        this.lazy=new int[4*n];
        Arrays.fill(lazy,Integer.MAX_VALUE);
        this.arr=arr;
    }
    int build(int low,int high,int ind) {
        if(low>high) return Integer.MAX_VALUE;
        if(low==high) return seg[ind]=arr[low];
        int m= low+(high-low)/2;
        int left=build(low,m,ind*2+1);
        int right=build(m+1,high,ind*2+2);
        return seg[ind]=push(left,right);
    }
    int query(int s,int e,int low,int high,int ind) {
        if (high < s || e < low) return Integer.MAX_VALUE;
        if(s<=low && high<=e) {
            return seg[ind];
        }
        int m = low + (high - low) / 2;
        propogate(ind);
        int left = query(s, e, low, m, ind * 2 + 1);
        int right = query(s, e, m + 1, high, ind * 2 + 2);
        return push(left, right);
    }
    int update(int low, int high, int ind, int s, int e, int val) {
        if (high < s || e < low) return seg[ind];
        if(s<=low && high<=e) {
            lazy[ind]=val;
            return seg[ind]=lazy[ind];
        }
        int m=low+(high-low)/2;
        propogate(ind);
        int left= update(low, m,ind*2+1, s, e, val);
        int right= update(m+1, high,ind*2+2, s, e, val);
        return seg[ind]=push(left, right);
    }
    void propogate(int ind) {
        if(lazy[ind]==Integer.MAX_VALUE) return;
        lazy[ind*2+1]=lazy[ind];
        lazy[ind*2+2]=lazy[ind];
        seg[ind*2+1]=lazy[ind];
        seg[ind*2+2]=lazy[ind];
        lazy[ind]=Integer.MAX_VALUE;
    }
    int push(int left, int right) {
        return Math.min(left,right);
    }
}
