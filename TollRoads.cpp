#include <bits/stdc++.h>
using namespace std;

#define dbg(x) cerr << #x << ": " << x << '\n'
template<typename A, typename B> ostream& operator<<(ostream &os, const pair<A, B> &p) { return os << '(' << p.first << ", " << p.second << ')'; }
template<typename T_container, typename T = typename enable_if<!is_same<T_container, string>::value, typename T_container::value_type>::type> ostream& operator<<(ostream &os, const T_container &v) { os << '['; string sep; for (const T &x : v) os << sep << x, sep = ", "; return os << ']'; }

const int N = 2e5;
vector<vector<array<int, 2>>> MST;
int up[N][19], upw[N][19], tin[N], tout[N], t = 0;
void dfs(int u, int p, int w){
    tin[u] = ++t, up[u][0] = p, upw[u][0] = w;
    for(int i = 1; i <= 18; ++i){
        up[u][i] = up[up[u][i-1]][i-1];
        upw[u][i] = max(upw[u][i-1], upw[up[u][i-1]][i-1]);
    }
    for(array<int, 2>& e : MST[u]){
        int v = e[0], w = e[1];
        if(v != p){
            dfs(v, u, w);
        }
    }
    tout[u] = ++t;
}

bool is_ancestor(int u, int v){
    return tin[u] <= tin[v] && tout[u] >= tout[v];
}

int lca(int u, int v){
    if(is_ancestor(u, v)) return u;
    if(is_ancestor(v, u)) return v;
    int j = u;
    for(int i = 18; i >= 0; --i){
        if(!is_ancestor(up[j][i], v)){
            j = up[j][i];
        }
    }
    return up[j][0];
}

int max_edge(int u, int v) {
    int LCA = lca(u, v), wu = 0, wv = 0;
    for(int i = 18; i >= 0; --i) {
        if(v != LCA && !is_ancestor(up[v][i], LCA)) {
            wv = max(wv, upw[v][i]);
            v = up[v][i];
        }
        if(u != LCA && !is_ancestor(up[u][i], LCA)) {
            wu = max(wu, upw[u][i]);
            u = up[u][i];
        }
    }
    if(v != LCA) {
        wv = max(wv, upw[v][0]);
    }
    if(u != LCA) {
        wu = max(wu, upw[u][0]);
    }
    return max(wu, wv);
}

struct UnionFind{
    vector<int> p, sz;
    UnionFind(int n){
        p = vector<int>(n), sz = vector<int>(n);
        for(int i = 0; i < n; ++i) {
            p[i] = i, sz[i] = 1;
        }
    }
    bool unite(int u, int v) {
        int U = find(u), V = find(v);
        if(U == V) {
            return 0;
        }
        if(sz[U] < sz[V]) {
            swap(U, V);
        }
        sz[U] += sz[V], p[V] = U;
        return 1;
    }
    int find(int u) {
        if(u == p[u]) {
            return u;
        }
        return p[u] = find(p[u]);
    }
    int getSize(int u) {
        return sz[find(u)];
    }
    void reset() {
        assert(p.size() == sz.size());
        for(int i = 0; i < p.size(); ++i) {
            p[i] = i, sz[i] = 1;
        }
    }
};

int main() {
    ios::sync_with_stdio(0); cin.tie(NULL);

    int n, m, q; cin >> n >> m >> q; MST.resize(n);
    vector<array<int, 3>> edges(m);
    for(int i = 0; i < m; ++i) {
        int u, v, w; cin >> u >> v >> w; --u, --v;
        edges[i] = {u, v, w};
    }

    vector<array<int, 2>> qq(q);
    for(int i = 0; i < q; ++i) {
        cin >> qq[i][0] >> qq[i][1]; --qq[i][0], --qq[i][1];
    }

    vector<array<int, 2>> qans(q);
    sort(edges.begin(), edges.end(), [&](array<int, 3>& e1, array<int, 3>& e2){
        return e1[2] < e2[2];
    });

    UnionFind uf(n);
    for(array<int, 3>& e : edges) {
        int u = e[0], v = e[1], w = e[2];
        if(uf.unite(u, v)) {
            MST[u].push_back({v, w}), MST[v].push_back({u, w});
        }
    }

    dfs(0, 0, 0);
    vector<array<int, 3>> qw(q);
    for(int i = 0; i < q; ++i) {
        int u = qq[i][0], v = qq[i][1];
        qans[i][0] = max_edge(u, v);
        qw[i] = {qans[i][0], u, i};
    }

    sort(qw.begin(), qw.end()), uf.reset();
    for(int i = 0, j = 0; i < edges.size(); ++i) {
        while(i + 1 < edges.size() && (edges[i][2] == edges[i + 1][2])) {
            int u = edges[i][0], v = edges[i][1];
            uf.unite(u, v); ++i;
        }
        uf.unite(edges[i][0], edges[i][1]);
        while(j < q && qw[j][0] <= edges[i][2]) {
            qans[qw[j][2]][1] = uf.getSize(qw[j][1]); ++j;
        }
        if(j == q) {
            break;
        }
    }

    for(int i = 0; i < q; ++i) {
        cout << qans[i][0] << " " << qans[i][1] << '\n';
    }
    return 0;
}
