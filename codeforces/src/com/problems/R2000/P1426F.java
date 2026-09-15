package com.problems.R2000;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class P1426F {
    static int mod= (int)1e9+7;
    static BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    static long[] pow;
    public static void main(String[] args) throws IOException {

        StringTokenizer s2
                = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(s2.nextToken());

        StringBuilder sb=new StringBuilder(br.readLine());
        pow=new long[n+1];
        fill(n);
        solve(sb,n);
    }
    static void solve(StringBuilder sb,int n) {
        Pair[] arr=new Pair[n];
        for(int i=0;i<n;i++) {
            arr[i]=new Pair();//check here ki if we will point to same will they allocate differernt memeory
            if(i!=0) {
                arr[i].a=arr[i-1].a;
                arr[i].c=arr[i-1].c;
                arr[i].q=arr[i-1].q;
            }
            char ch=sb.charAt(i);
            if(ch=='a') arr[i].a++;
            else if(ch=='c') arr[i].c++;
            else if(ch=='?') arr[i].q++;
        }
//        for(int i=0;i<n;i++) {
//            System.out.println(arr[i].a+" "+arr[i].c+" "+arr[i].q);
//        }
        //System.out.println(Arrays.toString(arr));
        long res=0;
        //now our logic is simple just considering b as pivot
        //adding the total subsequence with that b
        for(int i=1;i<n;i++) {
            if(sb.charAt(i)=='a' || sb.charAt(i)=='c') continue;
            int r=0;
            if(sb.charAt(i)=='?') r=1;
            int ap=arr[i-1].a;
            int cs=arr[n-1].c-arr[i-1].c;
            int qp=arr[i-1].q;
            int qs=arr[n-1].q-arr[i].q;
            int qt=arr[n-1].q;
            //first considering the present a and c
            long x1=(((pow[qt-r]*ap)%mod)*cs)%mod;
            //considering taking a as any question mark and c as any present c
            long x2=0;
            if(qp!=0) x2=(((pow[qt-r-1]*cs)%mod)*qp)%mod;
            //considering taking a as any prsent a and c as any quesiton mark
            long x3=0;
            if(qs!=0) x3=(((pow[qt-r-1]*ap)%mod)*qs)%mod;
            //considering taking a as any question makr and c as any quesiton mark
            long x4=0;
            if(qp!=0 && qs!=0) x4=(((pow[qt-r-2]*qp)%mod)*qs)%mod;
            res+=x1+x2+x3+x4;
            res%=mod;
        }
        System.out.println(res);
    }
    static void fill(int n) {
        pow[0]=1;
        for(int i=1;i<=n;i++) {
            pow[i]=pow[i-1]*3;
            pow[i]%=mod;
        }
    }
}
class Pair {
    int a,c,q;
}