#include <bits/stdc++.h>
using namespace std;


#define dbg(x) cerr << #x << ": " << x << '\n'
#define ll long long
template<typename A, typename B> ostream& operator<<(ostream &os, const pair<A, B> &p) { return os << '(' << p.first << ", " << p.second << ')'; }
template<typename T_container, typename T = typename enable_if<!is_same<T_container, string>::value, typename T_container::value_type>::type> ostream& operator<<(ostream &os, const T_container &v) { os << '['; string sep; for (const T &x : v) os << sep << x, sep = ", "; return os << ']'; }

ll inv(ll a, ll b){
    assert(a != 0); return 1<a ? b - inv(b%a,a)*b/a : 1LL;
}

using cd = complex<long double>;
using pi = pair<int64_t, int64_t>;
pi fcos(int64_t n);
pi fsin(int64_t n);
unordered_map<int64_t, pi> get_cos, get_sin;
int64_t M = 1e9 + 7;

pi mul(pi A, pi B) {
    return pi(A.first * B.first % M, A.second * B.second % M);
}
pi sub(pi A, pi B) {
    int64_t num = (A.first * B.second) % M - (A.second * B.first) % M;
    num %= M;
    if(num < 0) {
        num += M;
    }
    int64_t den = (A.second * B.second) % M;
    int64_t GCD = gcd(num, den);
    return pi(num / GCD, den / GCD);
}
pi add(pi A, pi B) {
    int64_t num = (A.first * B.second) % M + (A.second * B.first) % M;
    num %= M;
    if(num < 0) {
        num += M;
    }
    int64_t den = (A.second * B.second) % M;
    int64_t GCD = gcd(num, den);
    return pi(num / GCD, den / GCD);
}
pi fcos(int64_t n) {
    if(get_cos.find(n) != get_cos.end()) {
        return get_cos[n];
    }
    if(n & 1) {
        pi BCOS = fcos(n - 1), SCOS = fcos(1);
        pi BSIN = fsin(n - 1), SSIN = fsin(1);
        pi prod_cos = mul(BCOS, SCOS);
        pi prod_sin = mul(BSIN, SSIN);
        get_cos[n] = sub(prod_cos, prod_sin);
        int64_t GCD = gcd(get_cos[n].first, get_cos[n].second);
        get_cos[n].first /= GCD, get_cos[n].second /= GCD;
    } else{
        pi COS = fcos(n >> 1);
        COS = mul(COS, COS);
        COS.first = (COS.first * 2) % M;
        COS.first = (COS.first - COS.second) % M;
        if(COS.first < 0) {
            COS.first += M;
        }
        int64_t GCD = gcd(COS.first, COS.second);
        COS.first /= GCD, COS.second /= GCD;
        get_cos[n] = COS;
    }
    return get_cos[n];
}

pi fsin(int64_t n) {
    if(get_sin.find(n) != get_sin.end()) {
        return get_sin[n];
    }
    if(n & 1) {
        pi SINL = fsin(n - 1), COSL = fcos(1);
        pi COSR = fcos(n - 1), SINR = fsin(1);
        pi prod_left = mul(SINL, COSL);
        pi prod_right = mul(COSR, SINR);
        get_sin[n] = add(prod_left, prod_right);
        int64_t GCD = gcd(get_sin[n].first, get_sin[n].second);
        get_sin[n].first /= GCD, get_sin[n].second /= GCD;
    } else {
        pi SIN = fsin(n >> 1), COS = fcos(n >> 1);
        SIN = mul(SIN, COS);
        SIN.first = (SIN.first * 2) % M;
        int64_t GCD = gcd(SIN.first, SIN.second);
        SIN.first /= GCD, SIN.second /= GCD;
        get_sin[n] = SIN;
    }
    return get_sin[n];
}

int main() {
    ios::sync_with_stdio(0); cin.tie(NULL);
    int64_t a, b, n; cin >> a >> b >> n;
    get_cos[1] = make_pair(((b * b - a * a) % + M) % M, (a * a + b * b) % M);
    int GCD = gcd(get_cos[1].first, get_cos[1].second);
    get_cos[1].first /= GCD, get_cos[1].second /= GCD;
    get_sin[1] = make_pair(2 * a * b % M, (a * a + b * b) % M);
    GCD = gcd(get_sin[1].first, get_sin[1].second);
    get_sin[1].first /= GCD, get_sin[1].second /= GCD;
    pair<int64_t, int64_t> t = fcos(n + 1);
    if(n & 1) {
        t.first *= -1;
    }
    cout << (t.first * inv(t.second, M) % M + 2 * M) % M << '\n';
    return 0;
}
