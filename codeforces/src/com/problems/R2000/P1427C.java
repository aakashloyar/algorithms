package com.problems.R2000;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class P1427C {
    static BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {

        StringTokenizer s2
                = new StringTokenizer(br.readLine());
        int r =  Integer.parseInt(s2.nextToken());
        int n =  Integer.parseInt(s2.nextToken());
        int[][] arr=new int[n+1][3];
        arr[0][1]=1;
        arr[0][2]=1;
        for(int i=1;i<=n;i++) {
            StringTokenizer s3
                    = new StringTokenizer(br.readLine());
            arr[i][0]=Integer.parseInt(s3.nextToken());
            arr[i][1]=Integer.parseInt(s3.nextToken());
            arr[i][2]=Integer.parseInt(s3.nextToken());
        }
        solve(arr,n,r);
    }
    static void solve(int[][] arr, int n, int r) {
        int[] dp=new int[n+1];
        Arrays.fill(dp,-1);
        dp[0]=0;
        int k=0;
        int max=-1;
        for(int i=1;i<=n;i++) {
            if(i-k>2*r) max=Math.max(max,dp[k++]);
            for(int j=i-1;j>=k;j--) {
                if(dp[j]==-1 || !is(arr[j],arr[i])) continue;
                dp[i]=Math.max(dp[i],dp[j]+1);
            }
        }
        max=0;
        for(int i=1;i<=n;i++) max=Math.max(max,dp[i]);
        System.out.println(max);
    }
    static boolean is(int[] arr1,int[] arr2) {
        return arr2[0]-arr1[0]>=Math.abs(arr2[1]-arr1[1])+Math.abs(arr2[2]-arr1[2]);
    }
}