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
ll A[15][3], dp[1<<15][16], cost[1<<15];
int n, k;

ll solve(int mask, int idx){
    if(mask == 0){
        dp[mask][idx] = 0;
        return 0;
    }   

    if(dp[mask][idx] != -1){
        return dp[mask][idx];
    }
    dp[mask][idx] = 1e18;
    if(idx == 1){
        dp[mask][idx] = min(dp[mask][idx], cost[mask]);
        return dp[mask][idx];
    }

    for(int i = mask; i > 0; i = (i-1)&mask){
        dp[mask][idx] = min(dp[mask][idx], cost[i] + solve(i^mask, idx-1));
    }
    return dp[mask][idx];
}

int main(){
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> n >> k;
    memset(dp, -1, sizeof(dp));

    for(int i = 0; i < n; ++i){
        cin >> A[i][0] >> A[i][1] >> A[i][2];
    }

    for(int i = 0; i < 1<<n; ++i){
        ll maxH = 0, maxW = 0, c = 0;
        for(int j = 0; j < n; ++j){
            if((i>>j)&1){
                maxH = max(maxH, A[j][0]);
                maxW = max(maxW, A[j][1]);
            }
        }

        for(int j = 0; j < n; ++j){
            if((i>>j)&1){
                c += (maxH*maxW - A[j][0] * A[j][1]) * A[j][2];
            }
        }
        cost[i] = c;
    }

    solve((1<<n) - 1, k);
    cout << dp[(1<<n)-1][k] << endl;
    return 0;
}
