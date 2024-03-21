#include <iostream>
#include <iomanip>
#include <cstdio>
#include <cstring>
#include <vector>
#include <algorithm>
#include <iterator>
#include <set>
#include <map>
#include <tuple>
#include <unordered_map>
#include <unordered_set>
#include <queue>
#include <stack>
#include <deque>
#include <tgmath.h>
#include <cassert>
#include <time.h>
#include <random>
#include <array>
    
using namespace std;
typedef long long ll;
#define endl "\n"
#define vi vector<int>
#define vl vector<long>
#define vb vector<bool>
#define vii vector<vector<int>>
#define mi map<int, int>
#define pi pair<int, int>
#define F0R(i,n) for(int i = 0; i < n; ++i)
#define FOR(i,a,b) for(int i = a; i <= b; ++i)
#define F0Rd(i,n) for(int i = n-1; ~i; --i)
#define FORd(i,a,b) for(int i = b; i >= a; --i)
#define trav(a, x) for(auto &a : x)
#define f first
#define s second
#define pb push_back
#define sz(v) (int)((v).size())
#define all(v) v.begin(), v.end()

template<class T> bool chkmin(T& a, const T& b) { return b < a ? a = b, 1 : 0; }
template<class T> bool chkmax(T& a, const T& b) { return a < b ? a = b, 1 : 0; }

ll sq(ll a) { 
    return a*a; 
}

ll norm(const pair<ll,ll>& p) { 
    return sq(p.f)+sq(p.s); 
}

pair<ll,ll> operator-(const pair<ll,ll>& l, const pair<ll,ll>& r) {
    return pair<ll,ll>(l.f-r.f,l.s-r.s);
}

ll cross(const pair<ll,ll>& a, const pair<ll,ll>& b) { 
    return a.f*b.s-a.s*b.f; 
}
ll cross(const pair<ll,ll>& p, const pair<ll,ll>& a, const pair<ll,ll>& b) {
    return cross(a-p,b-p);
}

vector<int> hullInd(const vector<pair<int,int>>& v) {
    int ind = int(min_element(all(v))-begin(v));
    vector<int> cand, hull{ind};
    for(int i = 0; i < sz(v); ++i){
        if(v[i]!=v[ind]){
            cand.pb(i);
        }
    }

    sort(all(cand),[&](int a, int b) { // sort by angle, tiebreak by distance
        pair<ll, ll> x = v[a]-v[ind], y = v[b]-v[ind];
        ll t = cross(x,y);
        return t != 0 ? t > 0 : norm(x) < norm(y);
    });

    for(auto& c : cand){
        while (sz(hull) > 1 && cross(v[end(hull)[-2]],v[hull.back()],v[c]) <= 0) {
            hull.pop_back(); // pop until counterclockwise and size > 1
        }
        hull.pb(c);
    }

    return hull;
}


int main(){
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);

    int n;
    while(scanf("%d", &n) && n){
        vector<pair<int,int>> pts(n);
        for(int i = 0; i < n; ++i){
            scanf("%d%d", &pts[i].f, &pts[i].s);
        }

        vector<int> ret = hullInd(pts);
        cout << ret.size() << endl;
        for(auto& a : ret){
            cout << pts[a].f << " " << pts[a].s << endl;
        }
