//package com.constructive.problems;

import java.io.*;
import java.util.*;
public class P3 {
    static BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        StringTokenizer s1
                = new StringTokenizer(br.readLine());
        int t = Integer.parseInt(s1.nextToken());
        while(t-->0) {
            StringTokenizer s2
                    = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(s2.nextToken());
            int[][] arr=new int[n][2];
            StringTokenizer s3 = new StringTokenizer(br.readLine());
            StringTokenizer s4 = new StringTokenizer(br.readLine());
            for(int i=0;i<n;i++) {
                arr[i][0]=Integer.parseInt(s3.nextToken());
                arr[i][1]=Integer.parseInt(s4.nextToken());
            }
            solve(arr,n);
        }
    }
    static void solve(int[][] arr,int n) {
        if(n==2) {
            System.out.println("Impossible");
            return;
        }
        int[] res=new int[n];
        int[] v=new int[n+1];
        if(n==3) {
            func1(arr,res,v,0,n);
            print(res);
            return;
        }
        func1(arr,res,v,0,n-4);
        //now check if there is no condition something like
        // 1 2
        // 2 1
        // 1 2 3 4
        // 2 1 4 3
        int[] temp=new int[4];
        int k=0;
        for(int i=0;i<n;i++) if(v[i]!=1) temp[k++]=i;
        
    }
    static void func1(int[][] arr,int[] res,int[] v,int s,int n) {
        int curr=1;
        for(int i=s;i<n;i++) {
            while(v[curr]==1)  curr++;
            int ind=curr;
            while(v[ind]==1 ||ind==arr[i][0] ||ind==arr[i][1]) ind++;
            v[ind]=1;
            res[i]=ind;
        }
    }
    static void print(int[] arr) {
        PrintWriter out=new PrintWriter(System.out);
        for (int i = 0; i < arr.length; i++)
            out.print(arr[i] + " ");
        out.print("\n");
        out.flush();
    }
}

