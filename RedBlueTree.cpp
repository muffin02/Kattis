#include <bits/stdc++.h>
using namespace std;
    
typedef long long ll;
#define nl "\n"
#define sz(v) (int)((v).size())
#define all(v) v.begin(), v.end()
#define bitCount(i) __builtin_popcount(i)
#define FOR(i, a, b) for(int i = a; i < b; ++i)
#define FORd(i, b, a) for(int i = b; i >= a; --i)
#define EACH(x, a) for (auto& x : a)
#define arr array
#define pi pair<int, int>
#define vpi vector<pair<int, int>>
#define vi vector<int>
#define vl vector<ll>
#define pb push_back
#define ff first
#define ss second
    
ll inv(ll a, ll b){
    assert(a != 0); return 1<a ? b - inv(b%a,a)*b/a : 1LL;
}
    
#define dbg(x) cerr << #x << ": " << x << nl
template<typename A, typename B> ostream& operator<<(ostream &os, const pair<A, B> &p) { return os << '(' << p.first << ", " << p.second << ')'; }
template<typename T_container, typename T = typename enable_if<!is_same<T_container, string>::value, typename T_container::value_type>::type> ostream& operator<<(ostream &os, const T_container &v) { os << '['; string sep; for (const T &x : v) os << sep << x, sep = ", "; return os << ']'; }

int p[1005], sz[1005];

int find(int u) {
    if(u == p[u]) {
        return u;
    }
    return p[u] = find(p[u]);
}

int unite(int u, int v) {
    int U = find(u), V = find(v);
    if(U == V) return 0;
    if(sz[U] < sz[V]) {
        swap(U, V);
    }
    sz[U] += sz[V];
    p[V] = U;
    return 1;
}

int main() {
    // okay, so a couple of observations:
    // if there's a leaf node that has a blue edge, that has to be connected
    // we remove all of these leaf nodes. now we only have cycles
    // min and max # of blue edges in our graph?

    int n, m, k; cin >> n >> m >> k;
    vector<tuple<int, int, char>> edges(m);
    for(int i = 0; i < m; ++i) {
        char c; cin >> c;
        int u, v; cin >> u >> v; u -= 1, v -= 1;
        edges[i] = make_tuple(u, v, c);
    }

    for(int i = 0; i < n; ++i) {
        p[i] = i, sz[i] = 1;
    }

    int min_blue = 0, max_blue = 0;
    for(auto [u, v, c] : edges) {
        if(c == 'R') {
            unite(u, v);
        }
    }

    for(auto [u, v, c] : edges) {
        if(c == 'B') {
            if(unite(u, v)) {
                min_blue += 1;
            }
        }
    }

    for(int i = 0; i < n - 1; ++i) {
        if(find(i) != find(i + 1)) {
            cout << 0 << '\n';
        }
    }

    for(int i = 0; i < n; ++i) {
        p[i] = i, sz[i] = 1;
    }

    for(auto [u, v, c] : edges) {
        if(c == 'B') {
            if(unite(u, v)) {
                max_blue += 1;
            }
        }
    }

    for(auto [u, v, c] : edges) {
        if(c == 'R') {
            unite(u, v);
        }
    }

    if(min_blue <= k && k <= max_blue) {
        cout << 1 << '\n';
    } else{
        cout << 0 << '\n';
    }

    return 0;
}
