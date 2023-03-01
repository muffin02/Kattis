#include <bits/stdc++.h>
using namespace std;

#define dbg(x) cerr << #x << ": " << x << '\n'
template<typename A, typename B> ostream& operator<<(ostream &os, const pair<A, B> &p) { return os << '(' << p.first << ", " << p.second << ')'; }
template<typename T_container, typename T = typename enable_if<!is_same<T_container, string>::value, typename T_container::value_type>::type> ostream& operator<<(ostream &os, const T_container &v) { os << '['; string sep; for (const T &x : v) os << sep << x, sep = ", "; return os << ']'; }

const int64_t N = 5e3, INF = 4e18;
vector<vector<int>> adj;
int64_t dp[N + 5][N + 5], sz[N], sum[N];

int64_t mul(int64_t A, int64_t B) {
    if(!A || !B) return 0;
    if(A >= INF / B) {
        return INF;
    }
    return A * B;
}
void dfs(int u, int p) {
    sz[u] = 1, dp[u][0] = 1LL;
    for(auto v : adj[u]) {
        if(v == p) continue;
        dfs(v, u);
        for(int i = sz[u] - 1; ~i; --i) {
            for(int j = sz[v] - 1; ~j; --j) {
                dp[u][i + j + 1] = min(INF, dp[u][i + j + 1] + mul(dp[u][i], dp[v][j]));
            }
        }
        sz[u] += sz[v];
    }
}

int main() {
    ios::sync_with_stdio(0); cin.tie(NULL);
    int64_t n, k; cin >> n >> k; adj.resize(n);
    for(int i = 0; i < n - 1; ++i) {
        int u, v; cin >> u >> v; --u, --v;
        adj[u].push_back(v); adj[v].push_back(u);
    }
    dfs(0, 0);
    for(int i = 0; i <= n; ++i) {
        for(int j = 0; j <= n; ++j) {
            sum[j] = min(INF, sum[j] + dp[i][j]);
        }
    }

    int i = 0;
    while(k > 0) {
        if(k - sum[i] <= 0) {
            break;
        }
        k -= sum[i], ++i;
        if(i >= n) {
            cout << -1 << '\n'; return 0;
        }
    }
    cout << i + 1 << '\n';
    return 0;
}
