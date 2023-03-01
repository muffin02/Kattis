#include <bits/stdc++.h>
using namespace std;

#define dbg(x) cerr << #x << ": " << x << '\n'
template<typename A, typename B> ostream& operator<<(ostream &os, const pair<A, B> &p) { return os << '(' << p.first << ", " << p.second << ')'; }
template<typename T_container, typename T = typename enable_if<!is_same<T_container, string>::value, typename T_container::value_type>::type> ostream& operator<<(ostream &os, const T_container &v) { os << '['; string sep; for (const T &x : v) os << sep << x, sep = ", "; return os << ']'; }

using cd = complex<double>;
void fft(vector<cd>& a, bool invert) {
  int n = a.size();
  if (n == 1) return;
  cd w = 1;
  double angle = 2 * M_PI / n * (invert ? -1 : 1);
  cd wn(cos(angle), sin(angle));

  vector<cd> even, odd;
  for (int i = 0; i * 2 < n; i++) {
    even.push_back(a[i * 2]);
    odd.push_back(a[i * 2 + 1]);
  }
  fft(even, invert); fft(odd, invert);
  int op = n >> 1;

  for (int i = 0; i * 2 < n; i++) {
    a[i] = even[i] + w * odd[i];
    a[i + op] = even[i] - w * odd[i];
    if (invert) {
      a[i] /= 2;
      a[i + op] /= 2;
    }
    w *= wn;
  }
}

vector<long long> fft_multiply(vector<long long>& a, vector<long long>& b) {
    int n = 1;
    while(n < a.size() + b.size()) {
        n <<= 1;
    }
    vector<cd> A(n), B(n);
    for(int i = 0; i < a.size(); i++) {
        A[i] = cd(a[i], 0);
    }
    for(int i = a.size(); i < n; i++) {
        A[i] = cd(0, 0);
    }
    for(int i = 0; i < b.size(); i++) {
        B[i] = cd(b[i], 0);
    }
    for(int i = b.size(); i < n; i++) {
        B[i] = cd(0, 0);
    }

    fft(A, false), fft(B, false);
    for(int i = 0; i < n; ++i) {
        A[i] *= B[i];
    }

    fft(A, true);
    vector<long long> ret;
    for(auto x : A) {
        ret.push_back(round(x.real()));
    }

    return ret;
}


int main() {
    ios::sync_with_stdio(0); cin.tie(NULL);

    int n, numZeroes = 0; cin >> n;
    vector<long long> a(100001), v(n);
    for(int i = 0; i < n; ++i){
        int x; cin >> x;
        v[i] = x;
        if(x == 0) ++numZeroes;
        a[x+50000]++; 
    }

    a = fft_multiply(a, a);

    //A * B now computed
    for(auto x : v){
        a[2*(x+50000)]--; 
    }

    long long ret = 0;
    for(auto x : v){
        int targ = x + 100000;
        if(x == 0){
            ret += a[targ] - 2*(numZeroes-1);
        }
        else{
            ret += a[targ] - 2*numZeroes;
        }
    }

    cout << ret << '\n';
    return 0;
}
