#include <bits/stdc++.h>
using namespace std;
    
#define dbg(x) cerr << #x << ": " << x << '\n'
#define ll long long
template<typename A, typename B> ostream& operator<<(ostream &os, const pair<A, B> &p) { return os << '(' << p.first << ", " << p.second << ')'; }
template<typename T_container, typename T = typename enable_if<!is_same<T_container, string>::value, typename T_container::value_type>::type> ostream& operator<<(ostream &os, const T_container &v) { os << '['; string sep; for (const T &x : v) os << sep << x, sep = ", "; return os << ']'; }

const int M = 998244353;
int main(){
    ios::sync_with_stdio(0); cin.tie(0);

    int n, m; cin >> n >> m;
    long long LEN = 1, ans = 1, tans = 0;
    for(int i = 0; i < m; ++i) {
        LEN = 3 * LEN;
    }

    vector<string> g(n);
    for(auto& s : g) {
        cin >> s;
    }

    for(int i = 0; i < n; ++i) {
        for(int j = 0; j < m; ++j) {
            if(g[i][j] == '?') {
                ans = (ans * 3) % M;
            }
        }
    }

    vector<long long> p3(m + 1, 1);
    for(int i = 1; i <= m; ++i) {
        p3[i] = p3[i - 1] * 3;
    }

    vector<vector<bool>> match(n, vector<bool>(LEN, 1));
    vector<int> get_mask_ic(LEN), get_mask_pc(LEN);
    vector<char> gv = {'I', 'C', 'P'};
    for(int msk = 0; msk < LEN; ++msk) {
        for(int i = 0; i < n; ++i) {
            for(int j = 0, tmsk = msk; j < m; ++j, tmsk /= 3) {
                if(msk / p3[j] % 3 == 0 && msk / p3[j + 1] % 3 == 1) {
                    get_mask_ic[msk] = get_mask_ic[msk] | (1 << j) | 1 << (j + 1);
                }
                if(msk / p3[j] % 3 == 2 && msk / p3[j + 1] % 3 == 1) {
                    get_mask_pc[msk] = get_mask_pc[msk] | (1 << j) | 1 << (j + 1);
                }
                if(g[i][j] != '?' && g[i][j] != gv[tmsk % 3]) {
                    match[i][msk] = 0;
                }
            }
        }
    }

    vector<long long> get_blocks;
    for(int i = 0; i < 1 << m; ++i) {
        int ret = 0, ni = 0;
        while(ni) {
            ni = (ni & ni >> 1), ++ret;
        }
        if(ret % 2 == 0) {
            get_blocks.push_back(i);
        }
    }

    vector<vector<bool>> chk_two(1 << m, vector<bool>(1 << m));
    for(int i = 0; i < 1 << m; ++i) {
        for(int j = 0; j < 1 << m; ++j) {
            int ix = 0, iy = 0, x = i, y = j;
            for(int k = 0; k < m; ++k) {
                if(x >> k & 1) {
                    ix = (ix + 1) % 2;
                }
                if(y >> k & 1) {
                    iy = (iy + 1) % 2;
                }
                if(ix > 0 && ix == iy) {
                    chk_two[i][j] = 1; break;
                }
            }
        }
    }
    
    vector<vector<long long>> dp(n, vector<long long>(LEN));
    vector<vector<long long>> dp2(n, vector<long long>(1 << m));
    for(int i = 0; i < n; ++i) {
        if(!i) {
            for(int msk = 0; msk < LEN; ++msk) {
                if(!match[i][msk]) continue;
                ++dp[i][msk], ++dp2[i][get_mask_ic[msk]];
            }
        } else{
            for(int msk = 0; msk < LEN; ++msk) {
                if(!match[i][msk]) continue;
                for(auto pmsk : get_blocks) {
                    if(!(pmsk & get_mask_pc[msk]) || !chk_two[pmsk][get_mask_pc[msk]]) {
                        // no overlap, good?
                        dp[i][msk] += dp2[i - 1][pmsk]; 
                        dp[i][msk] %= M;
                    }
                }
                dp2[i][get_mask_ic[msk]] += dp[i][msk]; dp2[i][get_mask_ic[msk]] %= M;
            }
        }
    }
    for(int j = 0; j < LEN; ++j) {
        if(match[n - 1][j]){
            tans += dp[n - 1][j]; tans %= M;
        }
    }
    ans -= tans;
    if(ans < 0) {
        ans += M;
    }
    cout << ans << '\n';
    return 0;
}
