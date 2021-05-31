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

using namespace std;
typedef long long ll;
typedef unsigned long long ull;
typedef vector<int> vi;
typedef vector<vector<int>> vii;
typedef pair<int, int> pi;
typedef pair<long, long> pl;
typedef map<int, int> mapi;
typedef map<long, long> mapl;
typedef vector<long> vl;
typedef vector<vector<long>> vll;

#define endl "\n"
#define FOR(i, a) for(int i = 0; i < a; ++i)
#define FORR(i, a) for(int i = a; ~i; --i)

int main(){
    ios::sync_with_stdio(0);
    cin.tie(0);

    int a, b, D;
    scanf("%d", &D);
    vector<vector<ll>> V;
    V.push_back({0, 0});
    while(scanf("%d%d", &a, &b) == 2){
        V.push_back({a, b});
    }
    V.push_back({D, (ll) 1e12});

    ll dp[V.size()][201];
    memset(dp, 0x3f3f3f3f3f, sizeof(dp));
    dp[0][100] = 0; 
    for(int i = 1; i < V.size(); ++i){
        for(int j = 0; j < i; ++j){
            int dist = V[i][0] - V[j][0];
            for(int k = 0; k+dist <= 200; ++k){
                dp[i][k] = min(dp[i][k], dp[j][k+dist]);
            }
        }
        for(int j = 0; j <= 200; ++j){
            for(int k = j; k <= 200; ++k){
                dp[i][k] = min(dp[i][k], dp[i][j] + V[i][1] * (k-j));
            }
        }
    }

    ll res = 1e12;
    for(int i = 100; i <= 200; ++i){
        res = min(res, dp[V.size()-1][i]);
    }

    if(res == 1e12){
        cout << "IMPOSSIBLE" << endl;
    }
    else{
        cout << res << endl;
    }
    return 0;
}
