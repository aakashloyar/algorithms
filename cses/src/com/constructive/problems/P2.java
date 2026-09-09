//package com.constructive.problems;

import java.io.*;
import java.util.*;
public class P2 {
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
            int k = Integer.parseInt(s2.nextToken());
            solve(n,k);
        }
    }
    static void solve(int n,int k ) {
        //6-> 6 5 4 3 //p=3
        //5 -> 5 4 3 //p=3
        //4-> 4 3 2 //p=2
        //3-> 3 2 //2

        int p=(n+1)/2;//minimum possible length of monotonic subsequence
        if(k<p) {
            System.out.println("IMPOSSIBLE");
            return;
        }
        //1 2 3 4 ...n
        //normally we have n monotonic sebseq max length
        //so we need to find how many numbers we need to switch
        //that will be equal to n-k;
        int[] res=new int[n];
        int r=n-k;
        if(r==p) {
            for(int i=0;i<n;i++) res[i]=i+1;
            int a=(n-1)/2;
            int b=a+1;
            if((n&1)==1) {
                res[b]=b;
                b++;
            }
            rev(res,0,a);
            rev(res,b,n-1);
        } else {
            //System.out.println(r);
            int e=n-1-r;
            int f=n;
            for(int i=e;i<n;i++) res[i]=f--;
            //System.out.println(Arrays.toString(res));
            f=1;
            for(int i=0;i<e;i++) res[i]=f++;
        }
        print(res);
    }
    static void rev(int[] arr,int a,int b) {
        if(a>b) return;
        while(a<=b) {
            int temp=arr[a];
            arr[a]=arr[b];
            arr[b]=temp;
            a++;
            b--;
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

