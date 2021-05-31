#include <iostream>
#include <iomanip>
#include <cstdio>
#include <cstring>
#include <vector>
#include <algorithm>
#include <iterator>
#include <set>
#include <map>
#include <unordered_map>
#include <unordered_set>
#include <queue>
#include <stack>
#include <deque>
#include <tgmath.h>
#define ll long long
#define ull unsigned long long
#define f first
#define s second
#define endl "\n"

using namespace std;
struct Edge{
    int to, t;
    double p;
    Edge(int to, double p, int t): to(to), p(p/100), t(t) {}
};

vector<Edge> adj[1001];
vector<double> q(1001);

void dfs(int u, int par, double flow){
    q[u] = flow;
    for(Edge e : adj[u]){
        if(e.to != par){
            if(e.t && flow*e.p >= 1){
                dfs(e.to, u, flow*flow*e.p*e.p);
            }
            else{
                dfs(e.to, u, flow*e.p);
            }
        }
    }
}

int main(){
    ios::sync_with_stdio(0);
    cin.tie(0);
    fixed(cout);

    int n; cin >> n;
    for(int i = 0; i < n-1; ++i){
        int a, b, x, t; cin >> a >> b >> x >> t;
        --a; --b;
        adj[a].push_back(Edge(b, x, t));
        adj[b].push_back(Edge(a, x, t));
    }

    vector<double> a(n);
    for(int i = 0; i < n; ++i){
        cin >> a[i];
    }

    double lb = 0, hb = 2e9;
    while(lb+1e-6 < hb){
        double mb = (lb+hb)/2;
        bool ok = 1;

        dfs(0, -1, mb);

        for(int i = 0; i < n; ++i){
            if(a[i] != -1){
                ok &= (q[i]>=a[i]);
            }
        }
        if(ok){
            hb = mb;
        }
        else{
            lb = mb;
        }
    }

    cout << setprecision(4) << lb << endl;

    return 0;
}
