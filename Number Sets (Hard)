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
int a[1000001], sz[1000001];
int find(int u){
    if(u != a[u]){
        a[u] = find(a[u]);
    }
    return a[u];
}

void unite(int u, int v){
    int U = find(u), V = find(v);
    if(U == V){
        return;
    }
    if(sz[u] < sz[v]){
        sz[V] += sz[U];
        a[U] = a[V];
    }
    else{
        sz[U] += sz[V];
        a[V] = a[U];
    }
}

void solve(){
    ll A, B, P; cin >> A >> B >> P;
    vector<int> primes;
    vector<bool> isPrime(1000001, true);
    isPrime[0] = isPrime[1] = false;
    for(int i = 2; i*i <= 1000001; ++i){
        for(int j = i*i; j <= 1000001; j += i){
            isPrime[j] = false;
        }
    }
    for(int i = 0; i <= 1000000; ++i){
        if(i >= P && isPrime[i]){
            primes.push_back(i);
        }
        a[i] = i;
        sz[i] = 1;
    }

    for(int k : primes){
        for(ll i = (A+k-1)/k * k; i+k <= B; i += k){
            unite(i-A, i+k-A);
        }
    }

    vector<bool> isParent(1000001, false);
    for(int i = 0; i <= B-A; ++i){
        find(i);
        isParent[a[i]] = true;
    }

    int res = 0;
    for(int i = 0; i <= B-A; ++i){
        res += isParent[i];
    }

    cout << res << endl;
}

int main(){
    ios::sync_with_stdio(0);
    cin.tie(0);

    int t, i = 1; cin >> t;
    while(t--){
        cout << "Case #" << i++ << ": ";
        solve();
    }

    return 0;
}
