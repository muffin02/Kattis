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
const int mxN = 1e5+1, MOD = 1e9+7;
ll stLeft[8*mxN+1], stRight[8*mxN+1], sum[8*mxN+1], lazy[8*mxN+1][4], ps[8*mxN+2][4];

void update(int i){ 
    ll sum0 = 0, sum1 = 0;
    for(int t = 0; t < 4; ++t){
        ll totSum0 = (ps[stRight[2*i]][t] - ps[stLeft[2*i]-1][t]+2*MOD)%MOD;
        ll totSum1 = (ps[stRight[2*i+1]][t] - ps[stLeft[2*i+1]-1][t]+2*MOD)%MOD;
        sum0 = (sum0 + lazy[2*i][t] * totSum0 + 2*MOD)%MOD;
        sum1 = (sum1 + lazy[2*i+1][t] * totSum1 + 2*MOD)%MOD;
    }
    sum[i] = (sum[2*i] + sum[2*i+1] + sum0 + sum1 + 2 * MOD)%MOD;
}

void prop(int i){
    for(int t = 0; t < 4; ++t){
        lazy[2*i][t] = (lazy[2*i][t] + lazy[i][t] + 2*MOD)%MOD; 
        lazy[2*i+1][t] = (lazy[2*i+1][t] + lazy[i][t] + 2*MOD)%MOD;
        lazy[i][t] = 0;
    }
}

ll get(int i, int u, int v){
    if(v < stLeft[i] || stRight[i] < u){
        return 0;
    }
    if(u <= stLeft[i] && stRight[i] <= v){
        ll sum0 = 0;
        for(int t = 0; t < 4; ++t){
            sum0 = (sum0 + (ps[stRight[i]][t]-ps[stLeft[i]-1][t]+2 * MOD)%MOD * lazy[i][t] + 2*MOD)%MOD;
        }
        return (sum[i] + sum0 + 2 * MOD)%MOD;
    }
    prop(i);
    update(i);
    return (get(2*i, u, v) + get(2*i+1, u, v) + 2*MOD)%MOD;
}


void update(int i, int u, int v, int sign){
    if(v < stLeft[i] || stRight[i] < u){
        return;
    }
    if(u <= stLeft[i] && stRight[i] <= v){
        for(int t = 0; t < 4; ++t){
            ll c = 0;
            if(t == 0){
                c += -u * (ll)u * (ll)u + 6 * u * (ll) u - 11 * u + 6;
            }
            else if(t == 1){
                c += 3 * u * (ll) u - 12 * u + 11;
            }
            else if(t == 2){
                c += -3 * u + 6;
            }
            else{
                c += 1;
            }
            lazy[i][t] = (lazy[i][t] + c*sign + 2*MOD)%MOD;
        }
        return;
    }
    prop(i);
    update(2*i, u, v, sign);
    update(2*i+1, u, v, sign);
    update(i);
}

void construct(int i, int l, int r){
    stLeft[i] = l;
    stRight[i] = r;
    if(l == r){
        return;
    }
    int mb = (l+r)/2;
    construct(2*i, l, mb);
    construct(2*i+1, mb+1, r);
}

int main(){
    ios::sync_with_stdio(0);
    cin.tie(0);

    int n, m; cin >> n >> m;
    for(int i = 1; i <= n+3; ++i){
        for(int j = 0; j < 4; ++j){
            if(j == 0){
                ps[i][j] = (ps[i-1][j] + 1)%MOD;
            }
            else if(j == 1){
                ps[i][j] = (ps[i-1][j] + i)%MOD;
            }
            else if(j == 2){
                ps[i][j] = (ps[i-1][j] + i*(ll)i)%MOD;
            }
            else{
                ps[i][j] = (ps[i-1][j] + i*(ll)i*(ll)i)%MOD;
            }
        }
    }

    construct(1, 1, n);
    for(int i = 0; i < m; ++i){
        int t, x, y; cin >> t >> x >> y;
        if(t == 0){
            cout << (get(1,x,y) + 2 * MOD)%MOD << endl;
        }
        else if(t == 1){
            update(1, x, y, 1);
        }
        else{
            update(1, x, y, -1);
        }
    }

    return 0;
}
