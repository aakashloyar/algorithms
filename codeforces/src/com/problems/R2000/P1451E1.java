package com.problems.R2000;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class P1451E1 {
    static BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {

        StringTokenizer s2
                = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(s2.nextToken());
        System.out.println(n);
        //solve(n);
    }
    static void solve(int n) throws IOException {
        int[] res= new int[n+1];
        int AandB= IR(18,2,"and");
        int AorB= IR(1,2,"xor");
        int AandC= IR(1,3,"and");
        int BandC= IR(2,3,"and");
        int AxorC= IR(1,3,"xor");
        //now getting value for index 1
        //taking the set index in 1 and 2
        int a=AandB;
        //taking the set index in 1 and 3
        a|=AandC;
        //now let us evaluate the index which are like 0,1 for a,b and 0,1 or 0,0 for a,c
        res[0]=func1(a, AorB, BandC, AxorC);

        //now getting value of second and third index
        int AxorB=AorB-AandB;
        res[2]=AxorB^res[1];
        res[3]=AxorC^res[1];
        //now getting value for index greater than 3
        solveAfterSecondIndex(res,n);
        print(res);
    }
    static int func1(int a, int AorB, int BandC,int AxorC) {
        int res=a;
        int add=1;
        for(int i=0;i<31;i++) {
            if((AorB&1)==1 && (a&1)==0 && (BandC&1)==0 && (AxorC&1)==1) res+=add;
            a>>=1;
            AorB>>=1;
            BandC>>=1;
            AxorC>>=1;
            add<<=1;
        }
        return res;
    }
    static void solveAfterSecondIndex(int[] res,int n) throws IOException {
        for(int i=4;i<=n;i++) res[i]=IR(1,i,"xor")^res[1];
    }
    static void print(int[] res) {
        PrintWriter out=new PrintWriter(System.out);
        out.print("! ");
        for (int i = 1; i < res.length; i++)
            out.print(res[i] + " ");
        out.print("\n");
        out.flush();
    }
    static int IR(int a,int b, String op) throws IOException {
        System.out.println(op+" "+a+" "+b);
        System.out.flush();
        StringTokenizer s2
                = new StringTokenizer(br.readLine());
        return Integer.parseInt(s2.nextToken());
    }
}

