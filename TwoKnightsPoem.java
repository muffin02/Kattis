import java.io.*;
import java.util.*;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
public class TwoKnightsPoem{
    static int MAX = Integer.MAX_VALUE, MIN = Integer.MIN_VALUE;
    static FastScanner sc = new FastScanner();
    static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
    static Map<Integer, Character> mp = new HashMap<>();
    static Map<Integer, Character> mpUpper = new HashMap<>();
    static int[][] dir = {{-2, -1}, {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}};
    public static void main(String[] args){
        solve();
        out.close();
    }
    
    static void solve(){
        mp.put(0, 'q'); mp.put(1, 'w'); mp.put(2, 'e'); mp.put(3, 'r'); mp.put(4, 't'); mp.put(5, 'y'); mp.put(6, 'u');
        mp.put(7, 'i'); mp.put(8, 'o'); mp.put(9, 'p'); mp.put(10, 'a'); mp.put(11, 's'); mp.put(12, 'd'); mp.put(13, 'f');
        mp.put(14, 'g'); mp.put(15, 'h'); mp.put(16, 'j'); mp.put(17, 'k'); mp.put(18, 'l'); mp.put(19, ';');
        mp.put(20, 'z'); mp.put(21, 'x'); mp.put(22, 'c'); mp.put(23, 'v'); mp.put(24, 'b'); mp.put(25, 'n');
        mp.put(26, 'm'); mp.put(27, ','); mp.put(28, '.'); mp.put(29, '/');
        for(int i = 32; i <= 37; ++i) {
            mp.put(i, ' ');
        }
        
        mpUpper.put(0, 'Q'); mpUpper.put(1, 'W'); mpUpper.put(2, 'E'); mpUpper.put(3, 'R'); mpUpper.put(4, 'T');
        mpUpper.put(5, 'Y'); mpUpper.put(6, 'U'); mpUpper.put(7, 'I'); mpUpper.put(8, 'O'); mpUpper.put(9, 'P');
        mpUpper.put(10, 'A'); mpUpper.put(11, 'S'); mpUpper.put(12, 'D'); mpUpper.put(13, 'F'); mpUpper.put(14, 'G');
        mpUpper.put(15, 'H'); mpUpper.put(16, 'J'); mpUpper.put(17, 'K'); mpUpper.put(18, 'L'); mpUpper.put(19, ':');
        mpUpper.put(20, 'Z'); mpUpper.put(21, 'X'); mpUpper.put(22, 'C'); mpUpper.put(23, 'V'); mpUpper.put(24, 'B');
        mpUpper.put(25, 'N'); mpUpper.put(26, 'M'); mpUpper.put(27, '<'); mpUpper.put(28, '>'); mpUpper.put(29, '?');
        for(int i = 32; i <= 37; ++i) {
            mpUpper.put(i, ' ');
        }
        while(true) {
            char[] s = sc.nextline().toCharArray();
            if(s.length == 1 && s[0] == '*') {
                break;
            }
            int[][][][][][] dp = new int[s.length][4][10][4][10][2];
            for(int i = 0; i < s.length; ++i) {
                for(int j = 0; j < 4; ++j) {
                    for(int k = 0; k < 10; ++k) {
                        for(int l = 0; l < 4; ++l) {
                            for(int m = 0; m < 10; ++m) {
                                for(int n = 0; n < 2; ++n) {
                                    dp[i][j][k][l][m][n] = -1;
                                }
                            }
                        }
                    }
                }
            }
            //starts at dp[0][30][39].
            if(dfs(s, dp, 0, 1, 1, 3, 9, 0) == 1 || dfs(s, dp, 0, 2, 2, 3, 9, 0) == 1|| dfs(s, dp, 0, 3, 0, 1, 8, 1) == 1 || dfs(s, dp, 0, 3, 0, 2, 7, 1) == 1) {
                out.println(1);
            }
            else {
                out.println(0);
            }
        }
    }
    
    static int dfs(char[] s, int[][][][][][] dp, int idx, int aR, int aC, int bR, int bC, int move) {
        if(aR < 0 || aR > 3 || aC < 0 || aC > 9 || bR < 0 || bR > 3 || bC < 0 || bC > 9 || aR == bR && aC == bC) {
            return 0;
        }
        if(idx == s.length) {
            return 1;
        }
        if(dp[idx][aR][aC][bR][bC][move] != -1) {
            return dp[idx][aR][aC][bR][bC][move];
        }
        
        dp[idx][aR][aC][bR][bC][move] = 0;
        if(move == 0) {
            if((aR == 3) && (aC == 0 || aC == 1 || aC == 8 || aC == 9)) {
                //NO CHARACTER TYPED, A MOVED ONTO CAPS
                for(int[] d : dir) {
                    //b moves
                    dp[idx][aR][aC][bR][bC][move] |= dfs(s, dp, idx, aR, aC, bR + d[0], bC + d[1], 1);
                }
                for(int[] d : dir) {
                    //a moves
                    dp[idx][aR][aC][bR][bC][move] |= dfs(s, dp, idx, aR + d[0], aC + d[1], bR, bC, 0);
                }
            }
            else if((bR == 3) && (bC == 0 || bC == 1 || bC == 8 || bC == 9)) {
                //CHARACTER TYPED, MUST BE UPPER-CASE
                if(s[idx] != mpUpper.get(10*aR + aC)) {
                    dp[idx][aR][aC][bR][bC][move] = 0;
                    return 0;
                }
                //CHARACTER SUCCESSFULLY TYPED.
                for(int[] d : dir) {
                    dp[idx][aR][aC][bR][bC][move] |= dfs(s, dp, idx+1, aR + d[0], aC + d[1], bR, bC, 0);
                }
                for(int[] d : dir) {
                    dp[idx][aR][aC][bR][bC][move] |= dfs(s, dp, idx+1, aR, aC, bR + d[0], bC + d[1], 1);
                }
            }
            else {
                //CHARACTER TYPED, MUST BE LOWER-CASE
                if(s[idx] != mp.get(10*aR + aC)) {
                    dp[idx][aR][aC][bR][bC][move] = 0;
                    return 0;
                }
                for(int[] d : dir) {
                    dp[idx][aR][aC][bR][bC][move] |= dfs(s, dp, idx+1, aR + d[0], aC + d[1], bR, bC, 0);
                }
                for(int[] d : dir) {
                    dp[idx][aR][aC][bR][bC][move] |= dfs(s, dp, idx+1, aR, aC, bR + d[0], bC + d[1], 1);
                }
            }
        }
        else {
            //b moved
            if((bR == 3) && (bC == 0 || bC == 1 || bC == 8 || bC == 9)) {
                //NO CHARACTER TYPED, B MOVED ONTO CAPS
                for(int[] d : dir) {
                    //b moves
                    dp[idx][aR][aC][bR][bC][move] |= dfs(s, dp, idx, aR, aC, bR + d[0], bC + d[1], 1);
                }
                for(int[] d : dir) {
                    //a moves
                    dp[idx][aR][aC][bR][bC][move] |= dfs(s, dp, idx, aR + d[0], aC + d[1], bR, bC, 0);
                }
            }
            else if((aR == 3) && (aC == 0 || aC == 1 || aC == 8 || aC == 9)) {
                //CHARACTER TYPED, MUST BE UPPER-CASE
                if(s[idx] != mpUpper.get(10*bR + bC)) {
                    dp[idx][aR][aC][bR][bC][move] = 0;
                    return 0;
                }
                //CHARACTER SUCCESSFULLY TYPED.
                for(int[] d : dir) {
                    dp[idx][aR][aC][bR][bC][move] |= dfs(s, dp, idx+1, aR + d[0], aC + d[1], bR, bC, 0);
                }
                for(int[] d : dir) {
                    dp[idx][aR][aC][bR][bC][move] |= dfs(s, dp, idx+1, aR, aC, bR + d[0], bC + d[1], 1);
                }
            }
            else {
                //CHARACTER TYPED, MUST BE LOWER-CASE
                if(s[idx] != mp.get(10*bR + bC)) {
                    dp[idx][aR][aC][bR][bC][move] = 0;
                    return 0;
                }
                for(int[] d : dir) {
                    dp[idx][aR][aC][bR][bC][move] |= dfs(s, dp, idx+1, aR + d[0], aC + d[1], bR, bC, 0);
                }
                for(int[] d : dir) {
                    dp[idx][aR][aC][bR][bC][move] |= dfs(s, dp, idx+1, aR, aC, bR + d[0], bC + d[1], 1);
                }
            }
        }
        
        return dp[idx][aR][aC][bR][bC][move];
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
