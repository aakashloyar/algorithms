#include <bits/stdc++.h>
using namespace std;

struct Node {
    long long sum;
    long long pre;

    Node() : sum(0), pre(0) {}

    Node(long long sum, long long pre)
        : sum(sum), pre(pre) {}
};

class ST {
private:
    int n;
    vector<long long> arr;
    vector<Node> seg;

public:
    ST(vector<long long>& arr, int n) {
        this->n = n;
        this->arr = arr;
        this->seg.resize(4 * n);
    }

    Node push(const Node& left, const Node& right) {
        long long pre = max(left.pre, right.pre + left.sum);

        return Node(
            left.sum + right.sum,
            max(pre, 0LL)
        );
    }

    void build(int low, int high, int ind) {
        if (low == high) {
            seg[ind] = Node(
                arr[low],
                max(arr[low], 0LL)
            );
            return;
        }

        int m = low + (high - low) / 2;

        build(low, m, ind * 2 + 1);
        build(m + 1, high, ind * 2 + 2);

        seg[ind] = push(
            seg[ind * 2 + 1],
            seg[ind * 2 + 2]
        );
    }

    Node query(int s, int e, int low, int high, int ind) {
        // No overlap
        if (high < s || e < low) {
            return Node(0, 0);
        }

        // Complete overlap
        if (s <= low && high <= e) {
            return seg[ind];
        }

        int m = low + (high - low) / 2;

        Node left = query(
            s, e,
            low, m,
            ind * 2 + 1
        );

        Node right = query(
            s, e,
            m + 1, high,
            ind * 2 + 2
        );

        return push(left, right);
    }

    long long query2(int s, int e, int low, int high, int ind) {
        if (high < s || e < low) {
            return 0;
        }

        if (s <= low && high <= e) {
            return seg[ind].sum;
        }

        int m = low + (high - low) / 2;

        long long left = query2(
            s, e,
            low, m,
            ind * 2 + 1
        );

        long long right = query2(
            s, e,
            m + 1, high,
            ind * 2 + 2
        );

        return left + right;
    }

    void update(int p, long long val, int low, int high, int ind) {
        // Outside the range
        if (high < p || p < low) {
            return;
        }

        // Leaf
        if (low == high) {
            seg[ind] = Node(
                val,
                max(val, 0LL)
            );
            return;
        }

        int m = low + (high - low) / 2;

        update(
            p, val,
            low, m,
            ind * 2 + 1
        );

        update(
            p, val,
            m + 1, high,
            ind * 2 + 2
        );

        seg[ind] = push(
            seg[ind * 2 + 1],
            seg[ind * 2 + 2]
        );
    }
};

void solve(
    vector<long long>& arr,
    vector<array<int, 3>>& queries,
    int n,
    int q
) {
    ST seg(arr, n);

    seg.build(0, n - 1, 0);

    for (int i = 0; i < q; i++) {
        int type = queries[i][0];

        if (type == 1) {
            int ind = queries[i][1] - 1;
            long long val = queries[i][2];

            seg.update(
                ind,
                val,
                0,
                n - 1,
                0
            );
        }
        else {
            int s = queries[i][1] - 1;
            int e = queries[i][2] - 1;

            long long ans = seg.query(
                s,
                e,
                0,
                n - 1,
                0
            ).pre;

            cout << max(ans, 0LL) << '\n';
        }
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, q;
    cin >> n >> q;

    vector<long long> arr(n);

    for (int i = 0; i < n; i++) {
        cin >> arr[i];
    }

    vector<array<int, 3>> queries(q);

    for (int i = 0; i < q; i++) {
        cin >> queries[i][0]
            >> queries[i][1]
            >> queries[i][2];
    }

    solve(arr, queries, n, q);

    return 0;
}
