package com.maths.problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class IX {
    static BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    static int MAX=(int)1e6;
    public static void main(String[] args) throws IOException {
        StringTokenizer s2
                = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(s2.nextToken());
        int[] arr=new int[n];
        StringTokenizer s3
                = new StringTokenizer(br.readLine());
        for(int i=0;i<n;i++) arr[i]=Integer.parseInt(s3.nextToken());
        solve(arr,n);
    }
    static int[] dp;
    static int[] temp;
    static void solve(int[] arr, int n) {
        fill(arr, n);
        long total =((long)n*(n-1))/2;
        long sum=0;
        for(int i=0;i<n;i++) {
            int[] x=helper(arr[i]);
            long y=Noncoprimes(x,x.length,arr[i]);
            sum+=y;
        }
        sum/=2;
        System.out.println(total-sum);
    }
    static long Noncoprimes(int[] arr, int k, int n) {
        long res=0;
        int K= 1<<k;
        for(int i=1;i<K;i++) res+=func1(arr,n,i);
        return res;
    }
    static long func1(int[] arr,int n, int x) {
        int i=0,c=0;
        long mul=1;
        while(x!=0) {
            int b= x&1;
            c+=b;
            if(b==1) {
                mul*=arr[i];
                if(mul>n) return 0;
            }
            i++;
            x= x>>1;
        }
        long res=dp[(int)mul]-1;
        if((c&1)==0) res=-res;
        return res;
    }
    static int[] helper(int n) {
        ArrayList<Integer> list=new ArrayList<>();
        int x= 2;
        while(x*x<=n) {
            if(n%x==0) list.add(x);
            while(n%x==0) n/=x;
            x++;
        }
        if(n!=1) list.add(n);
        int[] arr=new int[list.size()];
        for(int i=0;i<arr.length;i++) arr[i]=list.get(i);
        return arr;
    }
    static void fill(int[] arr, int n) {
        dp=new int[MAX+1];
        temp=new int[MAX+1];
        for(int i=0;i<n;i++) temp[arr[i]]++;
        for(int i=2;i<=MAX;i++) {
            for(int j=i;j<=MAX;j+=i) dp[i]+=temp[j];
        }
    }
}