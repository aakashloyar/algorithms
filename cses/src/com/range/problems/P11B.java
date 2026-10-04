//package com.range.problems;

import java.io.*;
import java.util.*;


public class P11B {
    static BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        StringTokenizer s2
                = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(s2.nextToken());
        int q = Integer.parseInt(s2.nextToken());
        long[] arr=new long[n];
        StringTokenizer s3
                = new StringTokenizer(br.readLine());
        for(int i=0;i<n;i++) arr[i]=Integer.parseInt(s3.nextToken());
        int[][] que=new int[q][3];
        for(int i=0;i<q;i++) {
            StringTokenizer s4
                    = new StringTokenizer(br.readLine());
            que[i][0] = Integer.parseInt(s4.nextToken());
            que[i][1] = Integer.parseInt(s4.nextToken());
            que[i][2] = Integer.parseInt(s4.nextToken());
        }
        solve(arr,que,n,q);
    }
    static void solve(long[] arr, int[][] que, int n, int q) {
        ArrayList<Long> res= new ArrayList<>();
        ST seg= new ST(arr, n);
        seg.build(0,n-1,0);
        for (int i = 0; i < q; i++) {
            int type = que[i][0];
            if (type == 1) {
                int ind = que[i][1] - 1;
                int val = que[i][2];
                seg.update(ind, val, 0,n-1, 0);
            } else {
                int s = que[i][1] - 1;
                int e = que[i][2] - 1;
                long sum = seg.query( s, e,0,n-1,0).pre;
                //+ seg.query2(0, e-1,0,n-1,0);
                res.add(Math.max(sum, 0));
            }
        }
        for (Long x : res) System.out.println(x);
    }
}

class ST {
    int n;
    long[] arr;
    Node[] seg;
    ST(long[] arr,int n) {
        this.n=n;
        this.seg=new Node[4*n];
        this.arr=arr;
    }
    void build(int low, int high, int ind) {
        if(low==high) {
            seg[ind]=new Node(arr[low],Math.max(arr[low],0));
            return;
        }
        int m= low+(high-low)/2;
        build(low,m,ind*2+1);
        build(m+1,high,ind*2+2);
        seg[ind]=push(seg[ind*2+1],seg[ind*2+2]);
    }
    Node query(int s, int e, int low, int high, int ind) {
        if (high < s || e < low) return null;
        if(s<=low && high<=e) return seg[ind];
        int m = low + (high - low) / 2;
        Node left = query(s, e, low, m, ind * 2 + 1);
        Node right = query(s, e, m + 1, high, ind * 2 + 2);
        return push(left,right);
    }
    long query2(int s, int e, int low, int high, int ind) {
        if (high < s || e < low) return 0;
        if(s<=low && high<=e) return seg[ind].sum;
        int m = low + (high - low) / 2;
        long left = query2(s, e, low, m, ind * 2 + 1);
        long right = query2(s, e, m + 1, high, ind * 2 + 2);
        return left+right;
    }
    void update(int p, int val, int low, int high, int ind) {
        if (high < p || p < low) return;
        if(low==high) {
            seg[ind]=new Node(val,Math.max(val,0));
            return;
        }
        int m = low + (high - low) / 2;
        update(p, val, low,m,ind*2+1);
        update(p,val,m+1,high,ind*2+2);
        seg[ind]=push(seg[ind*2+1], seg[ind*2+2]);
    }
//    Node update(int p, int val, int low, int high, int ind) {
//        if (high < p || p < low) return null;
//        if (low == high) return seg[ind] = new Node(val, Math.max(val, 0));
//        int m = low + (high - low) / 2;
//        Node left = update(p, val, low, m, ind * 2 + 1);
//        Node right = update(p, val, m + 1, high, ind * 2 + 2);
//        return seg[ind] = push(left, right);
//    }
    //donot do this it will make you leave the returns which are not in the segment
    //you will update their result to null check line number 98 and 263
        Node push(Node left, Node right) {
        if(left==null) return right;
        if(right==null) return left;
        long pre=Math.max(left.pre, right.pre+left.sum);
        return new Node(left.sum+right.sum,Math.max(pre,0));
    }
}
class Node {
    long sum,pre;
    Node(long sum, long pre) {
        this.sum=sum;
        this.pre=pre;
    }
}
