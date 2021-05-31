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
#include <tgmath.h>
using namespace std;
#define ll long long
#define f first
#define s second
int main(){
    ios::sync_with_stdio(0);
    cin.tie(0);

    int n; cin >> n;
    vector<double> dp(n+1, 0), ways(n+1, 0);

    dp[0] = dp[1] = 1;
    for(int i = 2; i < n+1; ++i){
        dp[i] = i * dp[i-1];
    }

    if(n <= 3){
        cout << 1.0;
        return 0;
    }
    ways[2] = 1;
    ways[3] = 8;
    for(int i = 4; i <= n; ++i){
        double cur = pow(i-1, i);
        for(int j = 2; j <= i-2; ++j){
            cur -= dp[i-1]/dp[j-1]/dp[i-j] * ways[j] * pow(i-j-1, i-j);
        }
        ways[i] = cur;
    }

    cout << setprecision(12) << ways[n]/pow(n-1,n) << endl;
    return 0;
}
