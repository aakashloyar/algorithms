package com.maths;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class VIII {
    static BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    static int mod= (int)1e9+7;
    public static void main(String[] args) throws IOException {
        StringTokenizer s2
                = new StringTokenizer(br.readLine());
        long n = Long.parseLong(s2.nextToken());
        int k = Integer.parseInt(s2.nextToken());
        long[] arr=new long[k];
        StringTokenizer s3
                = new StringTokenizer(br.readLine());
        for(int i=0;i<k;i++) arr[i]=Long.parseLong(s3.nextToken());
        solve(arr,k,n);
    }
    static void solve(long[] arr, int k, long n) {
        long res=0;
        int K= 1<<k;
        for(int i=1;i<K;i++) res+=func1(arr,n,i);
        System.out.println(res);
    }
    static long func1(long[] arr,long n, int x) {
        int i=0,c=0;
        long mul=1;
        while(x!=0) {
            int b= x&1;
            c+=b;
            if(b==1) {
                if(helper(n,mul,arr[i])) return 0;
                mul*=arr[i];
            }
            i++;
            x= x>>1;
        }
        long res=n/mul;
        if((c&1)==0) res=-res;
        return res;
    }
    static boolean helper(long n,long m,long curr) {
        int a=count(m);
        int b=count(curr);
        return a + b > 60 || m * curr > n;
    }
    static int count(long n) {
        int c=0;
        while(n!=0) {
            n=n>>1;
            c++;
        }
        return c;
    }
}