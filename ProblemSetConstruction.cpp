#include <bits/stdc++.h>
using namespace std;

#define dbg(x) cerr << #x << ": " << x << '\n'
#define ll long long
template<typename A, typename B> ostream& operator<<(ostream &os, const pair<A, B> &p) { return os << '(' << p.first << ", " << p.second << ')'; }
template<typename T_container, typename T = typename enable_if<!is_same<T_container, string>::value, typename T_container::value_type>::type> ostream& operator<<(ostream &os, const T_container &v) { os << '['; string sep; for (const T &x : v) os << sep << x, sep = ", "; return os << ']'; }

ll inv(ll a, ll b){
    assert(a != 0); return 1<a ? b - inv(b%a,a)*b/a : 1LL;
}

const int N = 50, T = 2500;
double dp[N + 5][N + 5][T + 5], binom[N + 5][N + 5];
// dp[i][j][k] -> among the i problems, we've selected j of them, and the solved ones total time = k.
int main(){
    ios::sync_with_stdio(0);
    cin.tie(0); fixed(cout);

    int n, k, t; cin >> n >> k >> t;
    for(int i = 0; i <= n; ++i) {
        for(int j = 0; j <= i; ++j) {
            if(!i || i == j) {
                binom[i][j] = 1;
            } else{
                binom[i][j] += binom[i - 1][j] + binom[i - 1][j - 1];
            }
        }
    }
    
    vector<tuple<int, double, int>> A(n);
    for(int i = 0; i < n; ++i) {
        double p; int t, id; cin >> p >> t; id = i;
        A[i] = make_tuple(t, p, id);
    }

    sort(A.begin(), A.end());
    dp[0][0][0] = 1.0;
    for(int i = 0; i < n; ++i) {
        int ct = get<0>(A[i]); double p = get<1>(A[i]);
        dp[i + 1][0][0] = 1.0;
        for(int j = 0; j < k; ++j) {
            for(int tt = 0; tt <= t; ++tt) {
                // case 1: we solve it
                dp[i + 1][j + 1][tt] += dp[i][j + 1][tt];
                if(tt + ct <= t) {
                    dp[i + 1][j + 1][tt + ct] += p * dp[i][j][tt];
                }
                // case 2: we don't solve it
                dp[i + 1][j + 1][tt] += (1.0 - p) * dp[i][j][tt];
            }
        }
    }

    vector<double> ans(n);
    for(int i = 0; i < n; ++i) {
        int id = get<2>(A[i]), ct = get<0>(A[i]);
        double p = get<1>(A[i]), num = 0, den = binom[n - 1][k - 1];
        for(int j = 0; j <= k - 1; ++j) {
            for(int tt = 0; tt <= t - ct; ++tt) {
                num += dp[i][j][tt] * binom[n - i - 1][k - j - 1];
            }
        }
        ans[id] = p * num / den;
    }

    for(auto& x : ans) {
        cout << setprecision(12) << x << '\n';
    }
    return 0;
}
