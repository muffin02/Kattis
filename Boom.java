import java.io.*;
import java.util.*;
import java.math.BigInteger;
    
public class CP{
    private static int MAX = Integer.MAX_VALUE;
    private static int MIN = Integer.MIN_VALUE;
    private static int R;
    private static int[][] dpLeft, dpRight, dpTop, dpBottom, dp;
    private static Set<Integer> bombHit[][];
    private static int sum = 0;
    
    public static void main(String[] args){
        FastScanner sc = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        
        int n = sc.nextint();
        R = sc.nextint();
        dpLeft = new int[n][n]; dpRight = new int[n][n];
        dpTop = new int[n][n]; dpBottom = new int[n][n];
        dp = new int[n][n]; bombHit = new Set[n][n];
        
        char[][] grid = new char[n][n];
        for(int i = 0; i < n; i++) {
            grid[i] = sc.next().toCharArray();
            for(int j = 0; j < n; j++) {
                bombHit[i][j] = new HashSet<Integer>();
                dpLeft[i][j] = dpTop[i][j] = -1;
                dpBottom[i][j] = dpRight[i][j] = n;
                dp[i][j] = -1;
            }
        }
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == '*' || grid[i][j] == '#') {
                    dpLeft[i][j] = j;
                }
                else {
                    dpLeft[i][j] = (j-1 >= 0 ? dpLeft[i][j-1] : -1);
                }
            }
            for(int j = n-1; j >= 0; j--) {
                if(grid[i][j] == '*' || grid[i][j] == '#') {
                    dpRight[i][j] = j;
                }
                else {
                    dpRight[i][j] = (j+1 < n ? dpRight[i][j+1] : n);
                }
            }
        }
        
        for(int j = 0; j < n; j++) {
            for(int i = 0; i < n; i++) {
                if(grid[i][j] == '*' || grid[i][j] == '#') {
                    dpTop[i][j] = i;
                }
                else {
                    dpTop[i][j] = (i-1 >= 0 ? dpTop[i-1][j] : -1);
                }
            }
            for(int i = n-1; i >= 0; i--) {
                if(grid[i][j] == '*' || grid[i][j] == '#') {
                    dpBottom[i][j] = i;
                }
                else {
                    dpBottom[i][j] = (i+1 < n ? dpBottom[i+1][j] : n);
                }
            }
        }
        
        int nw = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] != '*' || dp[i][j] >= 0) {
                    continue;
                }
                dp[i][j] = nw;
                Queue<Point> q = new ArrayDeque<>();
                q.offer(new Point(i,j));
                while(!q.isEmpty()) {
                    Point p = q.poll();
                    int r = p.r, c = p.c;
                    if(c > 0 && dpLeft[r][c-1] >= 0) {
                        enq(q, grid, r, c, r, dpLeft[r][c-1]);
                    }
                    if(c+1 < n && dpRight[r][c+1] < n) {
                        enq(q, grid, r, c, r, dpRight[r][c+1]);
                    }
                    if(r > 0 && dpTop[r-1][c] >= 0) {
                        enq(q, grid, r, c, dpTop[r-1][c], c);
                    }
                    if(r+1 < n && dpBottom[r+1][c] < n) {
                        enq(q, grid, r, c, dpBottom[r+1][c], c);
                    }
                }
                ++nw;
            }
        }
        Map<Set<Integer>, Integer> mp = new HashMap<>();
        
        int stumps = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == 'S') {
                    ++stumps;
                    Set<Integer> dups = new HashSet<>();
                    if(dpLeft[i][j] >= 0 && j-dpLeft[i][j] <= R && grid[i][dpLeft[i][j]] == '*') {
                        dups.add(dp[i][dpLeft[i][j]]);
                    }
                    if(dpRight[i][j] < n && dpRight[i][j]-j <= R && grid[i][dpRight[i][j]] == '*') {
                        dups.add(dp[i][dpRight[i][j]]);
                    }
                    if(dpTop[i][j] >= 0 && i-dpTop[i][j] <= R && grid[dpTop[i][j]][j] == '*') {
                        dups.add(dp[dpTop[i][j]][j]);
                    }
                    if(dpBottom[i][j] < n && dpBottom[i][j]-i <= R && grid[dpBottom[i][j]][j] == '*') {
                        dups.add(dp[dpBottom[i][j]][j]);
                    }
                    
                    List<Integer> nums = new ArrayList<>(dups);
                    bombHit[i][j] = new HashSet<>(dups);
                    
                    dfs(nums, mp, new ArrayList<Integer>(), 0);
                }
            }
        }
        
        int ans = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == '.') {
                    Set<Integer> nws = new HashSet<>();
                    if(dpLeft[i][j] >= 0 && j-dpLeft[i][j] <= R && grid[i][dpLeft[i][j]] == '*') {
                        nws.add(dp[i][dpLeft[i][j]]);
                    }
                    if(dpRight[i][j] < n && dpRight[i][j] - j <= R && grid[i][dpRight[i][j]] == '*') {
                        nws.add(dp[i][dpRight[i][j]]);
                    }
                    if(dpTop[i][j] >= 0 && i-dpTop[i][j] <= R && grid[dpTop[i][j]][j] == '*') {
                        nws.add(dp[dpTop[i][j]][j]);
                    }
                    if(dpBottom[i][j] < n && dpBottom[i][j] - i <= R && grid[dpBottom[i][j]][j] == '*') {
                        nws.add(dp[dpBottom[i][j]][j]);
                    }
                    
                    List<Integer> nums = new ArrayList<>(nws);
                    dfs2(nums, mp, new ArrayList<Integer>(), 0);
                    
                    //check to see if there are any free stumps
                    for(int a = Math.max(dpLeft[i][j]+1, j-R); a <= Math.min(dpRight[i][j]-1, j+R); a++) {
                        if(grid[i][a] == 'S') {
                            boolean cov = false;
                            for(int k : nums) {
                                if(bombHit[i][a].contains(k)) {
                                    cov = true;
                                    break;
                                }
                            }
                            if(!cov) {
                                ++sum;
                            }
                        }
                    }
                    
                    for(int a = Math.max(dpTop[i][j]+1, i-R); a <= Math.min(dpBottom[i][j]-1, i+R); a++) {
                        if(grid[a][j] == 'S') {
                            boolean cov = false;
                            for(int k : nums) {
                                if(bombHit[a][j].contains(k)) {
                                    cov = true;
                                    break;
                                }
                            }
                            if(!cov) {
                                ++sum;
                            }
                        }
                    }
                    if(sum == stumps) {
                        ++ans;
                    }
                    sum = 0;
                }
            }
        }
        
        out.println(ans);
        out.close();
    }
    private static void dfs2(List<Integer> nums, Map<Set<Integer>, Integer> mp, List<Integer> cur, int index) {
        if(index == nums.size()) {
            if(cur.size() != 0) {
                Set<Integer> copy = new HashSet<>(cur);
                sum += (copy.size() % 2 == 1 ? mp.getOrDefault(copy, 0) : -1 * mp.getOrDefault(copy, 0));
            }
            return;
        }
        cur.add(nums.get(index));
        dfs2(nums, mp, cur, index+1);
        cur.remove(cur.size()-1);
        dfs2(nums, mp, cur, index+1);
    }
    private static void dfs(List<Integer> nums, Map<Set<Integer>, Integer> mp, List<Integer> cur, int index) {
        if(index == nums.size()) {
            if(cur.size() != 0) {
                Set<Integer> copy = new HashSet<>(cur);
                mp.put(copy, mp.getOrDefault(copy, 0)+1);
            }
            return;
        }
        cur.add(nums.get(index));
        dfs(nums, mp, cur, index+1);
        cur.remove(cur.size()-1);
        dfs(nums, mp, cur, index+1);
    }
  
    private static void enq(Queue<Point> q, char[][] grid, int r, int c, int nr, int nc) {
        if(grid[nr][nc] != '*' || dp[nr][nc] >= 0) {
            return;
        }
        if(Math.abs(nr-r) + Math.abs(c-nc) <= R) {
            dp[nr][nc] = dp[r][c];
            q.offer(new Point(nr, nc));
        }
    }
    
    static void sort(int[] a) {
        Arrays.sort(a);
    }
    
    static void sort(long[] a) {
        Arrays.sort(a);
    }
    private static PrintWriter out;
    private static BufferedReader br;
    private static StringTokenizer st;
    
    private static class FastScanner{
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
        int nextint() {
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

