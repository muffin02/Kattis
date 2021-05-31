import java.io.*;
import java.util.*;
import static java.lang.Math.max;
import static java.lang.Math.min;
public class Help{
    static int MAX = Integer.MAX_VALUE, MIN = Integer.MIN_VALUE;
    static FastScanner sc = new FastScanner();
    static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
    static Map<String, List<String>> adj;
    public static void main(String[] args){
        int t = sc.nextint();
        for(int i = 0; i < t; ++i) {
            solve();
        }
        out.close();
    }
    
    static void solve() {
        String[] s1 = sc.nextline().split(" "), s2 = sc.nextline().split(" ");
        adj = new HashMap<>();
        int n = s1.length;
        if(s1.length != s2.length) {
            out.println("-");
            return;
        }
        for(int i = 0; i < n; ++i) {
            if(s1[i].charAt(0) == '<') {
                s1[i] += "1";
            }
            if(s2[i].charAt(0) == '<') {
                s2[i] += "2";
            }
        }
        for(int i = 0; i < n; ++i) {
            List<String> cur1 = adj.getOrDefault(s1[i], new ArrayList<String>());
            cur1.add(s2[i]);
            adj.put(s1[i], cur1);
            
            List<String> cur2 = adj.getOrDefault(s2[i], new ArrayList<String>());
            cur2.add(s1[i]);
            adj.put(s2[i], cur2);
        }
        
        List<List<String>> comps = new ArrayList<>();
        Set<String> vis = new HashSet<>();
        for(String u : adj.keySet()) {
            if(!vis.contains(u)) {
                List<String> comp = new ArrayList<>();
                dfs(u, vis, comp);
                comps.add(comp);
            }
        }
        
        Map<String, String> res = new HashMap<>();
        for(List<String> comp : comps) {
            int count = 0;
            String targ = "";
            for(String s : comp) {
                if(s.charAt(0) != '<') {
                    targ = s;
                    ++count;
                }
            }
            if(count == 0) {
                for(String s : comp) {
                    res.put(s, "a");
                }
            }
            else if(count == 1) {
                for(String s : comp) {
                    res.put(s, targ);
                }
            }
            else {
                out.println("-");
                return;
            }
        }
        
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; ++i) {
            sb.append(res.get(s1[i]) + " ");
        }
        out.println(sb.toString());
    }
    
    static void dfs(String u, Set<String> vis, List<String> comp) {
        comp.add(u);
        vis.add(u);
        for(String v : adj.get(u)) {
            if(!vis.contains(v)) {
                dfs(v, vis, comp);
            }
        }
    }
    
    static BufferedReader br;
    static StringTokenizer st;
    
    static class FastScanner{
        Random r = new Random();
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
