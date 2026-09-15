//package com.problems.R2000;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class P1427D {
    static BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        StringTokenizer s2
                = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(s2.nextToken());
        int[] arr=new int[n+1];
        StringTokenizer s3
                = new StringTokenizer(br.readLine());
        for(int i=1;i<=n;i++) arr[i]=Integer.parseInt(s3.nextToken());
        solve(arr,n);
    }
    static void solve(int[] arr,int n) {
        ArrayList<ArrayList<Integer>> res=new ArrayList<>();
        while(!isAscending(arr,n)) {
            int ind=1;
            while(arr[ind]!=1) ind++;
            //now we have got the index where we have 1
            //this is our main chain we need to merge other chains in it
            int[] range= range(arr,n,ind);
            int nextchainindex= search(arr,n,arr[range[1]]+1);

            int[][] rangeArr;
            if(nextchainindex<range[1]) {
                //coming to our merging logic in main chain
                rangeArr=new int[2][2];
                int[] range1= range(arr,n,nextchainindex);
                range1[1]=range[0]-1;
                rangeArr[0]=range;
                rangeArr[1]=range1;
            } else {
                //coming in our fallback
                //now here find 2 index in such a way
                // i,j i<j  arr[i]+1=arr[j] arr[j]
                int p=range[1]+1;
                while(p<=n &&search(arr,n,arr[p]+1)>p) p++;
                int pNextConsIndex= search(arr,n,arr[p]+1);
                int[] range1=range(arr, n, p);
                int[] range2=range(arr,n,pNextConsIndex);
                range2[1]=range1[0]-1;
                //now we have 3 range so we can just arrange them in ascending order
                //then we can just add them to list and add any missing range
                rangeArr=new int[2][2];
                rangeArr[0]=range1;
                rangeArr[1]=range2;
            }
            ArrayList<Integer> revarr=filter(n,rangeArr);
            arr=rev(arr,n,revarr);
            res.add(revarr);
        }
        print(res, n);
    }
    static ArrayList<Integer> filter(int n, int[][] range) {
        sort2darray(range);
        ArrayList<Integer> list= new ArrayList<>();
        if(range[0][0]!=1) list.add(1);
        for(int i=0;i<range.length-1;i++) {
            int[] curr=range[i];
            list.add(curr[0]);
            if(range[i+1][0]!=range[i][1]+1) list.add(range[i][1]+1);
        }
        int[] curr= range[range.length-1];
        list.add(curr[0]);
        if(curr[1]!=n) list.add(curr[1]+1);
        return list;
    }
    static void sort2darray(int[][] arr) {
        Arrays.sort(arr,(int[] a, int[] b)->{
            if(a[0]!=b[0])
                return a[0]-b[0];
            else return b[1]-a[1];

        });
    }
    static int search(int[] arr, int n, int p) {
        for(int i=1;i<=n;i++) {
            if(arr[i]==p) return i;
        }
        return n+1;
    }
    static int[] range(int[] arr, int n, int ind) {
        int l=ind, r=ind;
        while(r<=n && arr[r]-arr[ind]==r-ind) r++;
        while(l>=1 && arr[ind]-arr[l]==ind-l) l--;
        return new int[]{l+1, r-1};
    }
    static int[] rev(int[] arr,int n, ArrayList<Integer> revarr) {
        int last =n+1;
        int[] temp =new int[n+1];
        int k=1;
        for(int i=revarr.size()-1;i>=0;i--) {
            int s=revarr.get(i);
            while(s<last) {
                temp[k++]=arr[s++];
            }
            last=revarr.get(i);
        }
        return temp;
    }
    static boolean isAscending(int[] arr,int n) {
        for(int i=1;i<=n;i++) if(arr[i]!=i) return false;
        return true;
    }
    static void print(ArrayList<ArrayList<Integer>> res, int n) {
        PrintWriter out=new PrintWriter(System.out);
        for(int i=0;i<res.size();i++) res.get(i).add(n+1);
        out.print(res.size()+"\n");
        for (ArrayList<Integer> arr: res) {
            out.print(arr.size()-1+" ");
            for(int i=1;i<arr.size();i++) out.print((arr.get(i)-arr.get(i-1))+" ");
            out.print("\n");
        }
        out.flush();
    }
}