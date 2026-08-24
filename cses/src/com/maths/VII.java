package com.maths;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class VII {
    static BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    static int mod= (int)1e9+7;
    public static void main(String[] args) throws IOException {
        StringTokenizer s2
                = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(s2.nextToken());
        int[] x=new int[n];
        int[] f=new int[n];
        for(int i=0;i<n;i++) {
            StringTokenizer s3
                    = new StringTokenizer(br.readLine());
            x[i]=Integer.parseInt(s3.nextToken());
            f[i]=Integer.parseInt(s3.nextToken());
        }
        solve(x,f,n);
    }
    static void solve(int[] x, int[] f, int n) {
        //first calculate the number of divisors
        // N = summation(fi+1)
        int N = totalDivisors(f, n);
        //second calculate the sum of divisors
        // S = pi(Xi)
        // where Xi =(xi^(fi+1)-1)/(xi-1)
        int S = sumofDivisors(x, f, n);
        //product of divisors
        //P = pi(xi^((fi*N)/2))
        int P = productofDivisors(x, f, n);
        System.out.println(N+ " "+ S+ " "+ P);
    }
    static int productofDivisors(int[] x,int[] f, int n) {
        int ind = -1;
        long N = 1;
        for(int i=0;i<n;i++) {
            N*=(f[i]+1);
            if(((f[i]+1)&1)==0 && ind==-1) {
                ind= i;
                N/=2;
            }
            N%=(mod-1);
        }
        long res=1;
        for(int i=0;i<n;i++) {
            res*=func2(N,ind,x[i],f[i]);
            res%=mod;
        }
        return (int)res;
    }
    static int func2(long N, int ind, int x, int f) {
        long pow = N*f;
        if(ind==-1) pow/=2;
        pow%=(mod-1);
        return (int)pow(x,(int)pow, mod);
    }
    static int sumofDivisors(int[] x, int[] f, int n) {
        long res=1;
        for(int i=0;i<n;i++) {
            res*= func1(x[i],f[i]);
            res%=mod;
        }
        return (int)res;
    }
    static int func1(int x, int f) {
        long res = (pow(x, f + 1, mod) - 1 + mod) % mod;
        res *= pow(x - 1, mod - 2, mod);
        res %= mod;
        return (int) res;
    }
    static int totalDivisors(int[] f, int n) {
        long N=1;
        for(int i=0;i<n;i++) {
            N*=(f[i]+1);
            N%=mod;
        }
        return (int)N;
    }
    static long pow(long base,int pow,int mod) {
        long res=base;
        long ans=1;
        while(pow!=0) {
            int bit=pow&1;
            if(bit==1) {
                ans=ans*res;
                ans%=mod;
            }
            res=res*res;
            res%=mod;
            pow=pow>>1;
        }
        return ans;
    }
}