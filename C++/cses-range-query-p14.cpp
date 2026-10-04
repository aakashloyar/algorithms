#include <bits/stdc++.h>
using namespace std;

class STMS {
    vector<vector<int>> tree;
    vector<int> arr;

public:
    STMS(int n, vector<int>& arr) {
        tree.resize(4 * n);
        this->arr = arr;
    }

    void build(int low, int high, int ind) {
        if (low == high) {
            tree[ind].push_back(arr[low]);
            return;
        }

        int m = low + (high - low) / 2;

        build(low, m, ind * 2 + 1);
        build(m + 1, high, ind * 2 + 2);

        merge(
            tree[ind * 2 + 1].begin(),
            tree[ind * 2 + 1].end(),
            tree[ind * 2 + 2].begin(),
            tree[ind * 2 + 2].end(),
            back_inserter(tree[ind])
        );
    }

    // Returns count of elements <= p
    int bs(const vector<int>& v, int p) {
        return upper_bound(v.begin(), v.end(), p) - v.begin();
    }

    int query(int s, int e, int minVal, int maxVal,
              int low, int high, int ind) {

        // No overlap
        if (high < s || e < low)
            return 0;

        // Complete overlap
        if (s <= low && high <= e) {
            return bs(tree[ind], maxVal)
                 - bs(tree[ind], minVal - 1);
        }

        int m = low + (high - low) / 2;

        int left = query(
            s, e, minVal, maxVal,
            low, m, ind * 2 + 1
        );

        int right = query(
            s, e, minVal, maxVal,
            m + 1, high, ind * 2 + 2
        );

        return left + right;
    }
};

void solve(vector<int>& arr, vector<array<int, 4>>& queries,
           int n, int q) {

    STMS seg(n, arr);

    seg.build(0, n - 1, 0);

    vector<int> res(q);

    for (int i = 0; i < q; i++) {
        res[i] = seg.query(
            queries[i][0],
            queries[i][1],
            queries[i][2],
            queries[i][3],
            0,
            n - 1,
            0
        );
    }

    for (int x : res)
        cout << x << '\n';
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, q;
    cin >> n >> q;

    vector<int> arr(n);

    for (int i = 0; i < n; i++)
        cin >> arr[i];

    vector<array<int, 4>> queries(q);

    for (int i = 0; i < q; i++) {
        cin >> queries[i][0]
            >> queries[i][1]
            >> queries[i][2]
            >> queries[i][3];

        // Convert 1-based indices to 0-based
        queries[i][0]--;
        queries[i][1]--;
    }

    solve(arr, queries, n, q);

    return 0;
}
