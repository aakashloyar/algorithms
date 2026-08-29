package com.range.problems;

import java.io.*;
import java.util.*;


public class P11 {
    static BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    void main(String[] args) throws IOException {
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
    void solve(long[] arr, int[][] que, int n, int q) {
        ArrayList<Long> res= new ArrayList<>();
        long[] dp=new long[n];
        dp[0]= arr[0];
        for(int i=1;i<n;i++) dp[i]= dp[i-1]+arr[i];
        LP seg= new LP(dp, n);
        seg.build(0,0,n-1);
        for (int i = 0; i < q; i++) {
            int type = que[i][0];

            if (type == 1) {
                int ind = que[i][1] - 1;
                int val = que[i][2];

                long diff = val - arr[ind];
                arr[ind] = val;

                seg.update(0, 0, n-1, ind, n - 1, diff);
            } else {
                int s = que[i][1] - 1;
                int e = que[i][2] - 1;
                long sum = seg.query( 0, 0,n-1,s,e)
                        - seg.query(0, 0, n-1, s,s)
                        + arr[s];

                res.add(Math.max(sum, 0));
            }
        }

        for (Long x : res) System.out.println(x);
    }
}

class LP {
    int n;
    long[] seg;
    long[] lazy;
    long[] arr;
    LP(long[] arr,int n) {
        this.n=n;
        this.seg=new long[4*n];
        this.lazy=new long[4*n];
        Arrays.fill(lazy,0);
        this.arr=arr;
    }
    long build(int ind, int low, int high) {
        if(low>high) return Long.MIN_VALUE;
        if(low==high) return seg[ind]=arr[low];
        int m= low+(high-low)/2;
        long left=build(ind*2+1, low,m);
        long right=build(ind*2+2,m+1,high);
        return seg[ind]=push(left,right);
    }
    long query(int ind, int low,int high, int s, int e) {
        if (high < s || e < low) return Long.MIN_VALUE;
        propogate(ind, low, high);
        if(s<=low && high<=e) {
            return seg[ind];
        }
        int m = low + (high - low) / 2;
        long left = query(ind*2+1, low, m, s, e);
        long right = query( ind*2+2,m + 1, high, s, e);
        return push(left, right);
    }
    long update(int ind, int low, int high, int s, int e, long val) {
        if (high < s || e < low) return seg[ind];
        propogate(ind,low,high);
        if(s<=low && high<=e) {
            lazy[ind]+=val;
            propogate(ind,low, high);
            return seg[ind];
        }
        int m=low+(high-low)/2;
        propogate(ind,low, high);
        long left= update(ind*2+1,low, m, s, e, val);
        long right= update(ind*2+2,m+1, high, s, e, val);
        return seg[ind]=push(left, right);
    }
    void propogate(int ind, int low, int high) {
        seg[ind]+=lazy[ind];
        if(low!=high) {
            lazy[ind*2+1]+=lazy[ind];
            lazy[ind*2+2]+=lazy[ind];
        }
        lazy[ind]=0;
    }
    long push(long left, long right) {
        return Math.max(left,right);
    }
}
