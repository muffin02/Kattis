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

    int n, c; cin >> n >> c;
    vector<int> v(n);
    FOR(i, n){
        cin >> v[i];
    }
    sort(v.begin(), v.end());

    double binom[65][65];
    for(int i = 0; i < 65; ++i){
        binom[i][0] = 1;
        for(int j = 1; j <= i; ++j){
            binom[i][j] = binom[i-1][j] + binom[i-1][j-1];
        }
    }

    double res = 0, sum = v[n-1];
    for(int i = 0; i < n-1; ++i){
        res += v[i];
        sum += v[i];
    }
    
    if(c == 0){
        cout << sum/n << endl;
        return 0;
    }
    
    res = res * (1 - binom[n-1][c]/binom[n][c]) / (n-1);

    for(int i = 0; i < n-1; ++i){
        sum -= v[i];
        if(i+1 >= c){
            //c numbers can be chosen
            res += binom[i][c-1] / binom[n][c] * sum / (n - i - 1);
        }
    }


    cout << res << endl;

    return 0;
}
