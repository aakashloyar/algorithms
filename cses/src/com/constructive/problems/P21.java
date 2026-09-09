//package com.constructive.problems;

//7 -> 2 2 2 1
//7 -> 3 3 1
import java.io.*;
import java.util.*;
public class P21 {
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
        int p=1;
        while(p*p<=n) p++;
        p--;
        int c=n/p;// c= total intervals
        if(n%p!=0) c++;
        int a=p+1;
        int b=n/a;
        if(n%a!=0) b++;
        if(Math.max(a,b)<Math.max(p,c)) {
            p=a;
            c=b;
        }
        if(k<c) {
            System.out.println("IMPOSSIBLE");
            return;
        }
        int[] res=new int[n];
        for(int i=0;i<n;i++) res[i]=i+1;
        int ind=0;
        int t=0;
        while(ind<n) {
            int s=ind;
            //curr will be = t+n-s
            while(s<ind+p && s<n && t+n-s>k) s++;
            if(s==ind+p || s==n) s--;
            rev(res,ind,s);
            ind+=p;
            t++;
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

