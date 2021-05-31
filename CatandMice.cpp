#include <iostream>
#include <iomanip>
#include <vector>
#include <algorithm>
#include <iterator>
#include <set>
#include <map>
#include <unordered_map>
#include <unordered_set>
#include <cstring>
#include <queue>
#include <bits/stdc++.h>

using namespace std;
#define ll long long
#define f first
#define s second
#define bitCount(i) __builtin_popcount(i);


int main(){
    ios::sync_with_stdio(0);
    cin.tie(0);
    fixed(cout);
    int n;
    cin >> n;
    double mice[n][3], dist[n][n], m;
    for(int i = 0; i < n; ++i){
        cin >> mice[i][0] >> mice[i][1] >> mice[i][2];
    }
    cin >> m;

    for(int i = 0; i < n; ++i){
        for(int j = i+1; j < n; ++j){
            double d = sqrt(pow(mice[i][1]-mice[j][1],2) + pow(mice[i][0]-mice[j][0], 2));
            dist[i][j] = d;
            dist[j][i] = d;
        }
    }

    double lb = 0, hb = 1e9;
    while(lb+1e-6<hb){
        vector<vector<double>> dp(1<<n, vector<double>(n, 2e9));

        double v = (lb+hb)/2;
        for(int mask = 1; mask < (1<<n); ++mask){
            int count = bitCount(mask);
            if(count == 1){
                int idx = 0;
                while(((mask>>idx)&1) == 0){
                    ++idx;
                }
                double time = sqrt(mice[idx][1]*mice[idx][1] + mice[idx][0]*mice[idx][0])/v;
                if(time <= mice[idx][2]){
                    dp[mask][idx] = min(dp[mask][idx], time);
                }
                continue;
            }
            
            for(int i = 0; i < n; ++i){
                for(int j = 0; j < n; ++j){
                    if(i == j){
                        continue;
                    }
                    //goes from mouse i to mouse j
                    if((mask>>i)&1 && (mask>>j)&1){
                        double added = dist[i][j] / (v * pow(m, count-1));
                        if(dp[mask^(1<<j)][i] + added > mice[j][2]){
                            continue;
                        }
                        dp[mask][j] = min(dp[mask][j], dp[mask^(1<<j)][i] + added);
                    }
                }
            }
        }

        bool ok = 0;
        for(int i = 0; i < n; ++i){
            ok |= (dp[(1<<n)-1][i] <= 100000);
        }

        if(ok){
            hb = v;
        }
        else{
            lb = v;
        }
    }

    cout << setprecision(6) << lb << endl;
    return 0;
}
