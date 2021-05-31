import java.io.*;
import java.util.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Bitwise{
    static int MAX = Integer.MAX_VALUE, MIN = Integer.MIN_VALUE;
    static FastScanner sc = new FastScanner();
    static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
    static int size;
    public static void main(String[] args){
        solve();
        out.close();
    }
    
    static void solve(){
        int n = sc.nextint(), targ = sc.nextint();
        int[] nums = sc.readint(n);
        int ans = 0, totOR = 0, highest = 0;
        int[] bitCounts = new int[32];
        for(int i = 0; i < n; ++i) {
            int curNum = nums[i];
            int bitIdx = 0;
            while(curNum > 0) {
                if((curNum&1) == 1) {
                    bitCounts[bitIdx]++;
                }
                ++bitIdx;
                curNum >>= 1;
            }
        }
        
        for(int i = 31; i >= 0; --i) {
            if(bitCounts[i] >= targ) {
                highest = i;
                break;
            }
        }
        
        for(int i = highest; i >= 0; --i) {
            int mask = ans | (1<<i);
            boolean ok = true;
            for(int j = 0; j <= highest && ok; ++j) {
                int left = 0, right = n-1, curVal = 0, count = 0;
                if(((1<<j)&mask) == 0){
                    continue;
                }
                while(left < n) {
                    curVal |= nums[left++];
                    if((curVal&(1<<j)) != 0){
                        break;
                    }
                }
                
                if((curVal&mask) == mask) {
                    ++count;
                    curVal = 0;
                }
                else {
                    while(right >= 0) {
                        curVal |= nums[right--];
                        if((curVal & mask) == mask) {
                            ++count;
                            curVal = 0;
                            break;
                        }
                    }
                }
                
                for(int k = left; k <= right; ++k) {
                    curVal |= nums[k];
                    if((curVal&mask) == mask) {
                        ++count;
                        curVal = 0;
                    }
                }
                
                if(count >= targ) {
                    ans |= (1<<i);
                    break;
                }
            }
        }
        
        out.println(ans);
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

