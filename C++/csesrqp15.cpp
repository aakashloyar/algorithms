
#include <bits/stdc++.h>
using namespace std;

#define ll long long

struct Node {
    ll sum, pre, suf, mx;

    Node(ll sum = 0, ll pre = 0, ll suf = 0, ll mx = 0)
        : sum(sum), pre(pre), suf(suf), mx(mx) {}
};

class ST {
    int n;
    vector<ll> arr;
    vector<Node> seg;

    Node push(const Node &left, const Node &right) {
        ll sum = left.sum + right.sum;
        ll pre = max(left.pre, left.sum + right.pre);
        ll suf = max(right.suf, right.sum + left.suf);
        ll mx = max({left.mx, right.mx, left.suf + right.pre});

        return Node(sum, pre, suf, mx);
    }

    void build(int low, int high, int ind) {
        if (low == high) {
            ll x = arr[low];
            seg[ind] = Node(x, max(0LL, x),
                            max(0LL, x), max(0LL, x));
            return;
        }

        int mid = low + (high - low) / 2;

        build(low, mid, 2 * ind + 1);
        build(mid + 1, high, 2 * ind + 2);

        seg[ind] = push(seg[2 * ind + 1],
                        seg[2 * ind + 2]);
    }

    void update(int p, ll val, int low, int high, int ind) {
        if (low == high) {
            seg[ind] = Node(val, max(0LL, val),
                            max(0LL, val), max(0LL, val));
            return;
        }

        int mid = low + (high - low) / 2;

        if (p <= mid)
            update(p, val, low, mid, 2 * ind + 1);
        else
            update(p, val, mid + 1, high, 2 * ind + 2);

        seg[ind] = push(seg[2 * ind + 1],
                        seg[2 * ind + 2]);
    }

public:
    ST(vector<ll> &a) {
        arr = a;
        n = a.size();
        seg.resize(4 * n);
    }

    void build() {
        build(0, n - 1, 0);
    }

    void update(int p, ll val) {
        update(p, val, 0, n - 1, 0);
    }

    ll query() {
        return seg[0].mx;
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, q;
    cin >> n >> q;

    vector<ll> arr(n);

    for (int i = 0; i < n; i++)
        cin >> arr[i];

    ST seg(arr);
    seg.build();

    while (q--) {
        int ind;
        ll val;
        cin >> ind >> val;

        --ind;  // Convert to 0-based indexing

        seg.update(ind, val);

        cout << seg.query() << '\n';
    }

    return 0;
}
