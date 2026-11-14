package com.problems.R2000;
import java.util.*;
import java.io.*;

public class P1416C {
    static BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
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
    static void solve(int[] arr,int n) {
        int bit=1<<30;
        long t=0,x=0;
        int[] dp=new int[n];
        for(int b=30;b>=0;b--) {
            long cnt0=func1(arr,dp,n,bit,0);
            long cnt1=func1(arr,dp,n,bit,1);
            if(cnt0<=cnt1) func2(arr, dp, n, bit, 0);
            else {
                func2(arr,dp,n,bit,1);
                x+=bit;
            }
            t+=Math.min(cnt0,cnt1);
            //System.out.println(cnt0+" "+ cnt1);
            //System.out.println(x);
            bit>>=1;
        }
        System.out.println(t+" "+x);
    }
    static long func1(int[] arr,int[] dp, int n, int bit, int type) {
        HashMap<Integer,Integer> map=new HashMap<>();
        long cnt=0;
        for(int i=0;i<n;i++) {
            int b=arr[i]&bit;
            if(b!=0) b=1;
            b^=type;
            int p=dp[i]<<1;
            if(b==0 && map.containsKey(p+1)) cnt+=map.get(p+1);
            put(map,p+b);
        }
        return cnt;
    }
    static void func2(int[] arr, int[] dp, int n, int b, int type) {
        for(int i=0;i<n;i++) {
            int curr=b&arr[i];
            if(curr!=0) curr=1;
            curr^=type;
            dp[i]<<=1;
            dp[i]+=curr;
        }
    }
    static void put(HashMap<Integer,Integer> map,int p) {
        if(!map.containsKey(p)) map.put(p,0);
        map.put(p,map.get(p)+1);
    }
}