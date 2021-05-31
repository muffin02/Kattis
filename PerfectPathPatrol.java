import java.io.*;
import java.util.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class PerfectPathPatrol{
    static int MAX = Integer.MAX_VALUE, MIN = Integer.MIN_VALUE;
    static FastScanner sc = new FastScanner();
    static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
    static List<int[]>[] adj;
    public static void main(String[] args){
        solve();
        out.close();
    }
    
    static void solve() {
        int n = sc.nextint();
        adj = new ArrayList[n];
        for(int i = 0; i < n; ++i) {
            adj[i] = new ArrayList<int[]>();
        }
        
        for(int i = 0; i < n-1; ++i) {
            int n1 = sc.nextint(), n2 = sc.nextint(), w = sc.nextint();
            adj[n1].add(new int[] {n2, w});
            adj[n2].add(new int[] {n1, w});
        }
        
        out.println(dfs(0, -1, 0));
    }
    
    static long dfs(int u, int parent, int extra) {
        long sum = 0, max = 0, ans = 0;
        for(int[] e : adj[u]) {
            if(e[0] != parent) {
                sum += e[1];
                max = max(max, e[1]);
                ans += dfs(e[0], u, e[1]);
            }
        }
        
        if(sum-max >= max-extra) {
            ans += max(0, (sum-extra+1)/2);
        }
        else {
            ans += max-extra;
        }
        return ans;
    }

    static void sort(int[] a) {
        Random r = new Random();
        int n = a.length;
        for(int i = 0; i < n; ++i) {
            int idx = r.nextInt(n);
            int temp = a[idx];
            a[idx] = a[i];
            a[i] = temp;
        }
        Arrays.sort(a);
    }
    
    static BufferedReader br;
    static StringTokenizer st;
    
    static class FastScanner{
        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        
        String next() {
            while(st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
        
        int[] readint(int n) {
            int[] res = new int[n];
            for(int i = 0; i < n; i++) {
                res[i] = nextint();
            }
            return res;
        }
        
        long[] readlong(int n) {
            long[] res = new long[n];
            for(int i = 0; i < n; i++) {
                res[i] = nextlong();
            }
            return res;
        }
        
        int nextint(){
            return Integer.parseInt(next());
        }
        
        long nextlong() {
            return Long.parseLong(next());
        }
        
        double nextdouble() {
            return Double.parseDouble(next());
        }
        
        String nextline() {
            String str = "";
            try {
                str = br.readLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}


