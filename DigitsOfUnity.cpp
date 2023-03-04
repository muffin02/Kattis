```cpp
#include <bits/stdc++.h>
using namespace std;

const int M = 998244353;
void test_case() {
    int n, k, m; cin >> n >> k >> m;
    vector<long long> b(m + 1);
    function<int(int)> get_len = [&](int x) {
        int res = 0;
        while(x > 0) {
            x >>= 1; ++res;
        }
        return res;
    };

    int z = get_len(m);
    for(int i = 1; i <= m; ++i) {
        vector<long long> ps(z + 1);
        for(int j = 0; j < z; ++j) {
            ps[j + 1] = ps[j] + !(i >> j & 1);
        }
        for(int j = z - 1; j >= 0; --j) {
            if(i >> j & 1) {
                if(!(m >> j & 1)) {
                    break;
                } else if(!j) {
                    ++b[i];
                }
                continue;
            }
            if(m >> j & 1) {
                b[i] += 1 << ps[j];
            }
            if(!j) {
                ++b[i];
            }
        }
    }

    vector<long long> fact(m + 5, 1), ifact(m + 5, 1), get_inv(m + 5, 1);
    for(int i = 2; i <= m; ++i) {
        fact[i] = fact[i - 1] * i % M;
        get_inv[i] = (M - (M / i) * get_inv[M % i] % M) % M;
        ifact[i] = ifact[i - 1] * get_inv[i] % M;
    }

    long long res = 0;
    for(int i = 0; i <= m; ++i) {
        int p = __builtin_popcount(i) - k;
        if(p >= 0 && b[i] - n >= 0) {
            long long nCk = fact[p + k - 1] * ifact[k - 1] % M * ifact[p] % M;
            res = (res + fact[b[i]] * ifact[b[i] - n] % M * nCk % M * (p & 1 ? -1 % M : 1)) % M;
            if(res < 0) {
                res += M;
            }
        }
    }
    cout << res << '\n';
}

int main(){
    ios::sync_with_stdio(0); cin.tie(0);
    test_case();
    return 0;
}
```
