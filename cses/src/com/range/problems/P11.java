
import java.io.*;
import java.util.*;


public class P11 {
    static BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    void main(String[] args) throws IOException {
        StringTokenizer s2
                = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(s2.nextToken());
        int q = Integer.parseInt(s2.nextToken());
        int[] arr=new int[n];
        StringTokenizer s3
                = new StringTokenizer(br.readLine());
        for(int i=0;i<n;i++) arr[i]=Integer.parseInt(s3.nextToken());
        int[][] que=new int[q][3];
        for(int i=0;i<q;i++) {
            StringTokenizer s4
                    = new StringTokenizer(br.readLine());
            que[i][0] = Integer.parseInt(s4.nextToken());
            que[i][1] = Integer.parseInt(s4.nextToken());
            que[i][2] = Integer.parseInt(s4.nextToken());
        }
        solve(arr,que,n,q);
    }
    void solve(int[] arr, int[][] que, int n, int q) {
        ArrayList<Long> res= new ArrayList<>();
        long[] dp=new long[n];
        dp[0]= arr[0];
        for(int i=1;i<n;i++) dp[i]= dp[i-1]+arr[i];
        LP seg= new LP(dp,n);
        seg.build(0,0,n-1);
        for (int i = 0; i < q; i++) {
            int type = que[i][0];

            if (type == 1) {
                int ind = que[i][1] - 1;
                int val = que[i][2];

                int diff = val - arr[ind];
                arr[ind] = val;

                seg.update(0, 0, n - 1, ind, n - 1, diff);
            } else {
                int s = que[i][1] - 1;
                int e = que[i][2] - 1;

                long sum = seg.query(0, 0, n - 1, s, e)
                        - seg.query(0, 0, n - 1, s, s)
                        + arr[s];

                res.add(Math.max(sum, 0));
            }
        }

        for (Long x : res) System.out.print(x+" ");
        System.out.println();
    }
}

class LP {
    int n;
    int[] seg;
    int[] lazy;
    int[] arr;
    LP(int[] arr,int n) {
        this.n=n;
        this.seg=new int[4*n];
        this.lazy=new int[4*n];
        Arrays.fill(lazy,Integer.MAX_VALUE);
        this.arr=arr;
    }
    int build(int low,int high,int ind) {
        if(low>high) return Integer.MAX_VALUE;
        if(low==high) return seg[ind]=arr[low];
        int m= low+(high-low)/2;
        int left=build(low,m,ind*2+1);
        int right=build(m+1,high,ind*2+2);
        return seg[ind]=push(left,right);
    }
    int query(int s,int e,int low,int high,int ind) {
        if (high < s || e < low) return Integer.MAX_VALUE;
        if(s<=low && high<=e) {
            return seg[ind];
        }
        int m = low + (high - low) / 2;
        propogate(ind);
        int left = query(s, e, low, m, ind * 2 + 1);
        int right = query(s, e, m + 1, high, ind * 2 + 2);
        return push(left, right);
    }
    int update(int low, int high, int ind, int s, int e, int val) {
        if (high < s || e < low) return seg[ind];
        if(s<=low && high<=e) {
            lazy[ind]=val;
            return seg[ind]=lazy[ind];
        }
        int m=low+(high-low)/2;
        propogate(ind);
        int left= update(low, m,ind*2+1, s, e, val);
        int right= update(m+1, high,ind*2+2, s, e, val);
        return seg[ind]=push(left, right);
    }
    void propogate(int ind) {
        if(lazy[ind]==Integer.MAX_VALUE) return;
        lazy[ind*2+1]=lazy[ind];
        lazy[ind*2+2]=lazy[ind];
        seg[ind*2+1]=lazy[ind];
        seg[ind*2+2]=lazy[ind];
        lazy[ind]=Integer.MAX_VALUE;
    }
    int push(int left, int right) {
        return Math.min(left,right);
    }
}

void main() {
}
