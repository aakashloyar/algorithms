package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.*;
public class main1 {
    static ArrayList<Integer> list;
    static BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    static void main() throws IOException {
        list=new ArrayList<>();
        StringTokenizer s2
                = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(s2.nextToken());
        for(int j=0;j<n;j++) {
            int[] arr=new int[n+1];
            StringTokenizer s3
                    = new StringTokenizer(br.readLine());
            for(int i=1;i<52;i++) arr[i]=Integer.parseInt(s3.nextToken());
            solve(arr);
        }
    }
    static void solve(int[] arr) {
        int c=0;
        for(int i=1;i<arr.length;i++) {
            if(arr[i]-arr[i-1]==1) c++;
        }
        System.out.println(c);
    }
}
