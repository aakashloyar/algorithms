package com.range.problems;
import java.io.*;
import java.util.*;


public class P15 {
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
        int[][] que=new int[q][2];
        for(int i=0;i<q;i++) {
            StringTokenizer s4
                    = new StringTokenizer(br.readLine());
            que[i][0] = Integer.parseInt(s4.nextToken());
            que[i][1] = Integer.parseInt(s4.nextToken());
        }
        solve(arr,que,n,q);
    }
    static void solve(long[] arr, int[][] que, int n, int q) {
        ArrayList<Long> res= new ArrayList<>();
        ST seg= new ST(arr, n);
        seg.build(0,n-1,0);
        for (int i = 0; i < q; i++) {
            int ind = que[i][0] - 1;
            int val = que[i][1];
            seg.update(ind, val, 0,n-1, 0);
            long sum = seg.query( 0, n-1,0,n-1,0).max;
            res.add(sum);
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
            seg[ind]=new Node(arr[low],Math.max(arr[low],0),Math.max(arr[low],0));
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
    void update(int p, int val, int low, int high, int ind) {
        if (high < p || p < low) return;
        if(low==high) {
            seg[ind]=new Node(val,Math.max(val,0),Math.max(val,0));
            return;
        }
        int m = low + (high - low) / 2;
        update(p, val, low,m,ind*2+1);
        update(p,val,m+1,high,ind*2+2);
        seg[ind]=push(seg[ind*2+1], seg[ind*2+2]);
    }
    Node push(Node left, Node right) {
        if(left==null) return right;
        if(right==null) return left;
        long suf=Math.max(right.suf, right.sum+left.suf);
        long max=Math.max(Math.max(left.max,right.max),right.max+left.suf);
        return new Node(left.sum+ right.sum, suf, max);
    }
}
class Node {
    long sum,suf,max;
    Node(long sum, long suf, long max) {
        this.sum=sum;
        this.suf=suf;
        this.max=max;
    }
}
