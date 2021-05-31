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
const int mxN = 200002, MAX = 2e9;
int occ[mxN], tourHeight[2*mxN+1], tourNode[2*mxN+1], height[mxN+1];
int stLeft[8*mxN+1], stRight[8*mxN+1], minVal[8*mxN+1];
int eulerIdx = 0;
vector<int> adj[mxN];

void dfs(int u, int par, int h){
    occ[u] = eulerIdx;
    tourHeight[eulerIdx] = h;
    tourNode[eulerIdx++] = u;
    height[u] = h;
    for(int v : adj[u]){
        if(v != par){
            dfs(v, u, h+1);
            occ[u] = eulerIdx;
            tourHeight[eulerIdx] = h;
            tourNode[eulerIdx++] = u;
        }
    }
}

void construct(int i, int l, int r){
    stLeft[i] = l;
    stRight[i] = r;
    if(l == r){
        minVal[i] = tourHeight[l];
        return;
    }
    int mb = (l+r)/2;
    construct(2*i, l, mb);
    construct(2*i+1, mb+1, r);
    minVal[i] = min(minVal[2*i], minVal[2*i+1]);
}

int rangeMin(int i, int l, int r){
    if(stLeft[i] > r || stRight[i] < l){
        return MAX;
    }
    if(l <= stLeft[i] && stRight[i] <= r){
        return minVal[i];
    }
    return min(rangeMin(2*i, l, r), rangeMin(2*i+1, l, r));
}

int dist(int u, int v){
    int minVal = rangeMin(1, min(occ[u], occ[v]), max(occ[u], occ[v]));
    return height[u] + height[v] - 2 * minVal;
}

int main(){
    ios::sync_with_stdio(0);
    cin.tie(0);

    int n; cin >> n;
    for(int i = 0; i < n-1; ++i){
        int n1, n2; cin >> n1 >> n2;
        adj[n1].push_back(n2);
        adj[n2].push_back(n1);
    }
    dfs(1, -1, 0);
    construct(1, 0, 2*n-2);

    ll res = 0;
    for(int i = 1; i <= n/2; ++i){
        for(int j = 2*i; j <= n; j += i){
            res += dist(i, j) + 1;
        }
    }

    cout << res << endl;

    return 0;
}