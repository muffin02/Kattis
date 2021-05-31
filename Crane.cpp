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
struct Move{
    int a, b;
    Move(int _a, int _b){
        a = _a;
        b = _b;
    }
};

vector<Move> moves;

void recurse(vector<int>& nums, int n){
    if(n == 0){
        return;
    }
    int max = nums[0], idx = 0;
    for(int i=0; i<=n; ++i){
        if(nums[i] > max){
            max = nums[i];
            idx = i;
        }
    }

    if((n-idx)%2 == 1){
        moves.push_back(Move(idx, idx+1));
        int temp = nums[idx];
        nums[idx] = nums[idx+1];
        nums[idx+1] = temp; 
        ++idx;
    }

    if(n != idx){
        moves.push_back(Move(idx, n-1));
        moves.push_back(Move(idx+1, n));
        int temp = nums[idx];
        int mb = (idx+n)/2;
        nums[idx] = nums[mb];
        nums[mb] = nums[n];
        nums[n] = temp;
    }
    recurse(nums, n-1);
}

void solve(){
    int n;
    cin >> n;
    vector<int> nums(n);
    for(int i=0; i<n; ++i){
        cin >> nums[i];
    }
    moves.clear();
    recurse(nums, n-1);
    cout << moves.size() << endl;
    for(auto& move : moves){
        cout << move.a+1 << " " << move.b+1 << endl;
    }
}
int main(){
    ios::sync_with_stdio(0);
    cin.tie(0);

    int t;
    cin >> t;
    while(t--){
        solve();
    }

    return 0;
}
