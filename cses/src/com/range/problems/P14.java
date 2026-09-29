package com.range.problems;

import java.io.*;
import java.util.*;


public class P14 {
    static BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        StringTokenizer s2
                = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(s2.nextToken());
        int q = Integer.parseInt(s2.nextToken());
        int[] arr=new int[n];
        StringTokenizer s3
                = new StringTokenizer(br.readLine());
        for(int i=0;i<n;i++) arr[i]=Integer.parseInt(s3.nextToken());
        int[][] que=new int[q][4];
        for(int i=0;i<q;i++) {
            StringTokenizer s4
                    = new StringTokenizer(br.readLine());
            que[i][0] = Integer.parseInt(s4.nextToken())-1;
            que[i][1] = Integer.parseInt(s4.nextToken())-1;
            que[i][2] = Integer.parseInt(s4.nextToken());
            que[i][3] = Integer.parseInt(s4.nextToken());
        }
        solve(arr,que,n,q);
    }
    static void solve(int[] arr, int[][] que, int n, int q) {
        STMS seg=new STMS(n,arr);
        seg.build(0,n-1,0);
        int[] res=new int[q];
        for(int i=0;i<q;i++) res[i]=seg.query(que[i][0],que[i][1],que[i][2],que[i][3],0,n-1,0);
        print(res);
    }
    static void print(int[] res) {
        PrintWriter out=new PrintWriter(System.out);
        for (int i = 0; i < res.length; i++) out.print(res[i]+"\n");
        out.flush();
    }
}

class STMS {
    ArrayList<Integer>[] list;
    int[] arr;
    STMS(int n,int[] arr) {
        this.list=new ArrayList[4*n];
        this.arr=arr;
        for(int i=0;i<4*n;i++) list[i]=new ArrayList<>();
    }
    void build(int low,int high, int ind) {
        if(low==high) {
            list[ind].add(arr[low]);
            return;
        }
        int m=low+(high-low)/2;
        build(low,m,ind*2+1);
        build(m+1,high,ind*2+2);
        mergesort(list[ind*2+1], list[ind*2+2],ind);
    }
    void mergesort(ArrayList<Integer> left,ArrayList<Integer> right,int ind) {
        int l=0,r=0;
        while(l<left.size() && r<right.size()) {
            if(left.get(l)<right.get(r)) list[ind].add(left.get(l++));
            else list[ind].add(right.get(r++));
        }
        while(l<left.size()) list[ind].add(left.get(l++));
        while(r<right.size()) list[ind].add(right.get(r++));
    }
    int query(int s,int e,int min,int max,int low,int high,int ind) {
        if(high<s || e<low) return 0;
        if(s<=low && high<=e) return bs(list[ind],max)-bs(list[ind],min-1);
        int m=low+(high-low)/2;
        int left=query(s,e,min,max,low,m,ind*2+1);
        int right=query(s,e,min,max,m+1,high,ind*2+2);
        return left+right;
    }
    int bs(ArrayList<Integer> list,int p) {
        int s=0,e=list.size()-1;
        while(s<=e) {
            int m=s+(e-s)/2;
            if(list.get(m)<=p) s=m+1;
            else e=m-1;
        }
        return s;
    }
}