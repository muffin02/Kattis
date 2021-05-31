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
int f(ll s, ll x, ll y){
    if(s <= 2){
        return 0;
    }
    //region 1:
    if(x >= s/2 && y >= s/2){
        return f(s/2, x-s/2, y-s/2);
    }
    //region 2:
    if((x < s/2 && y >= 3*s/4) || (x < s/4 && y >= s/2)){
        return ((f(s/2, s-1-y, x)+1)%4+4)%4;
    }
    //region 3:
    if((x >= s/4 && x < s/2 && y >= s/4 && y < 3*s/4) || (x >= s/4 && x < 3*s/4 && y >= s/4 && y < s/2)){
        return f(s/2, x-s/4, y-s/4);
    }
    //region 4:
    if((x >= s/2 && y < s/4) || (x >= 3*s/4 && y < s/2)){
        return ((f(s/2, y, s-1-x)-1)%4+4)%4;
    }
    //region 5
    return f(s/2, x, y);
}
void solve(){
    ll x, y; cin >> x >> y;
    ll pow2 = 1;
    while(pow2 <= x || pow2 <= y){
        pow2 *= 2;
    }
    cout << f(pow2, x, y) << endl;
}
int main(){
    ios::sync_with_stdio(0);
    cin.tie(0);

    int t; cin >> t;
    for(int i = 0; i < t; ++i){
        solve();
    }


    return 0;
}