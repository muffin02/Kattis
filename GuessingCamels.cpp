#include <iostream>
#include <bits/stdc++.h>

using namespace std;

#define ll long long

int BIT[200001];
void increment(int idx){
    for(++idx; idx < 200001; idx += (idx&-idx)){
        BIT[idx]++;
    }
}

ll sum(int idx){
    ll res = 0;
    for(++idx; idx > 0; idx -= (idx&-idx)){
        res += BIT[idx];
    }
    return res;
}

ll rangeSum(int lb, int hb){
    return sum(hb) - sum(lb-1);
}

ll inv(vector<int> a, vector<int> b){
    memset(BIT, 0, sizeof(BIT));
    int n = a.size();
    vector<int> c(n), d(n);
    ll res = 0;
    for(int i = 0; i < n; ++i){
        c[a[i]] = i;
    }
    for(int i = 0; i < n; ++i){
        d[i] = c[b[i]];
    }
    
    for(int i = 0; i < n; ++i){
        res += rangeSum(0, d[i]);
        increment(d[i]);
    }
    
    return n * (long) (n-1)/2 - res;
}
int main() {
    int n;
    cin >> n;
    vector<int> nums1(n), nums2(n), nums3(n);
    for(int i = 0; i < n; ++i){
        cin >> nums1[i];
        --nums1[i];
    }
    
    for(int i = 0; i < n; ++i){
        cin >> nums2[i];
        --nums2[i];
    }
    
    for(int i = 0; i < n; ++i){
        cin >> nums3[i];
        --nums3[i];
    }
    
    ll ans = inv(nums1, nums2) + inv(nums1, nums3) + inv(nums2, nums3);
    cout << (n*(ll) (n-1) - ans)/2 << endl;
    return 0;
}
