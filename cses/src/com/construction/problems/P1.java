package com.construction.problems;

import java.io.*;
import java.util.*;
public class P1 {
    static BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        StringTokenizer s2
                = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(s2.nextToken());
        long k=Long.parseLong(s2.nextToken());
        solve(n,k);
    }
    static void solve(int n,long k ) {
        int[] res=new int[n];
        int s=1,e=n;
        for(int i=0;i<n;i++) {
            int p=n-i-1;
            if(k>=p) {
                res[i]=e--;
                k-=p;
            } else res[i]=s++;
        }
        print(res);

    }
    static void print(int[] arr) {
        PrintWriter out=new PrintWriter(System.out);
        for (int i = 0; i < arr.length; i++)
            out.print(arr[i] + " ");
        out.print("\n");
        out.flush();
    }
}

