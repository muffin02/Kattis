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
#include <deque>
using namespace std;
#define ll long long
#define f first
#define s second

int main(){
    ios::sync_with_stdio(0);
    cin.tie(0);

    int N, T; cin >> N >> T;
    vector<vector<int>> dp(N, vector<int>(T+1, 0)), c(N, vector<int>(2, 0));
    for(int i = 0; i < N; ++i){
        cin >> c[i][1] >> c[i][0];
    }
    sort(c.begin(), c.end());
    int ans = 0;
    for(int j = 1; j <= T; ++j){
        for(int i = 0; i < N; ++i){
            if(c[i][0] < j-1){
                continue;
            }
            dp[i][j] = (i-1>=0?dp[i-1][j]:0);
            dp[i][j] = max(dp[i][j], (i-1>=0?dp[i-1][j-1]:0) + c[i][1]);
            ans = max(ans, dp[i][j]);
        }
    }

    cout << ans << endl;

    return 0;
}
