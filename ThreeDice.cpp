#include <bits/stdc++.h>
using namespace std;
    
#define dbg(x) cerr << #x << ": " << x << '\n'
#define ll long long
template<typename A, typename B> ostream& operator<<(ostream &os, const pair<A, B> &p) { return os << '(' << p.first << ", " << p.second << ')'; }
template<typename T_container, typename T = typename enable_if<!is_same<T_container, string>::value, typename T_container::value_type>::type> ostream& operator<<(ostream &os, const T_container &v) { os << '['; string sep; for (const T &x : v) os << sep << x, sep = ", "; return os << ']'; }

array<int, 3> get_mask(string s) {
    array<int, 3> ret;
    for(int i = 0; i < s.size(); ++i) {
        ret[i] = (1 << (s[i] - 'a'));
    }
    return ret;
}    

int main(){
    ios::sync_with_stdio(0); cin.tie(0);

    int n; cin >> n;
    vector<string> w(n);
    for(int i = 0; i < n; ++i) {
        cin >> w[i];
        for(int j = 0; j < 3; ++j) {
            for(int k = j + 1; k < 3; ++k) {
                if(w[i][j] == w[i][k]) {
                    cout << 0 << '\n'; return 0;
                }
            }
        }
    }

    vector<array<int, 3>> t{get_mask(w[0])};
    vector<bool> vis(26);
    for(int i = 0; i < 3; ++i) {
        vis[w[0][i] - 'a'] = 1;
    }
    for(int i = 1; i < n; ++i) {
        vector<array<int, 3>> nt;
        string s = w[i];
        for(array<int, 3>& msk : t) {
            for(int j = 0; j < 6; ++j) {
                array<int, 3> cmsk = get_mask(s);
                bool is_good = 1;
                for(int k = 0; k < 3; ++k) {
                    if(vis[s[k] - 'a'] && !(msk[k] & cmsk[k])) {
                        is_good = 0; break;
                    }
                }
                if(is_good) {
                    for(int k = 0; k < 3; ++k) {
                        cmsk[k] |= msk[k];
                        if(__builtin_popcount(cmsk[k]) > 6) {
                            is_good = 0; break;
                        }
                    }
                    if(is_good) {
                        nt.push_back(cmsk);
                    }
                }
                next_permutation(s.begin(), s.end());
            }
        }
        t = nt;
        if(!t.size()) {
            cout << 0 << '\n'; return 0;
        }
        for(int j = 0; j < 3; ++j) {
            vis[s[j] - 'a'] = 1;
        }
    }

    array<int, 3> sol = t.back();
    vector<string> ret(3);
    for(int i = 0; i < 3; ++i) {
        for(int j = 0; j < 26; ++j) {
            if(sol[i] >> j & 1) {
                ret[i] += (char)('a' + j);
            }
        }
    }
    char c = 'a';
    for(int i = 0; i < 3; ++i) {
        while(ret[i].size() < 6) {
            while(vis[c - 'a']) {
                ++c;
            }
            ret[i] += c; ++c;
        }
    }
    for(auto& x : ret) {
        cout << x << " ";
    }
    return 0;
}
