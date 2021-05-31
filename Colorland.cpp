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

    unordered_map<string, int> mp;
    mp.insert({"Blue", 0});
    mp.insert({"Orange", 1});
    mp.insert({"Pink", 2});
    mp.insert({"Green", 3});
    mp.insert({"Red", 4});
    mp.insert({"Yellow", 5});

    int n; cin >> n;
    vector<int> nums(n);
    for(int i = 0; i < n; ++i){
        string s; cin >> s;
        nums[i] = mp[s];
    }

    vector<vector<int>> next(n+1, vector<int>(6, -1));
    for(int i = n-1; i >= 0; --i){
        for(int j = 0; j <= 5; ++j){
            if(j == nums[i]){
                next[i][j] = i+1;
            }
            else{
                next[i][j] = next[i+1][j];
            }
        }
    }
    int count = 0, idx = 0;
    while(idx < n){
        int nxt = idx;
        for(int j = 0; j <= 5; ++j){
            nxt = max(nxt, next[idx][j]);
        }
        idx = nxt;
        ++count;
    }
    cout << count << endl;

    return 0;
}
