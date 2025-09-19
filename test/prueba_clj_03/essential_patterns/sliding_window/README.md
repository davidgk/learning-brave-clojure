# Title

Find the longest substring with at most k distinct characters.

```java
import java.util.*;
public int longestSubstringKDistinct(String s, int k) {
    Map<Character, Integer> freq = new HashMap<>();
    int start = 0, maxLen = 0;
    for (int end = 0; end < s.length(); end++) {
        char right = s.charAt(end);
        freq.put(right, freq.getOrDefault(right, 0) + 1);
        while (freq.size() > k) {
            char left = s.charAt(start);
            freq.put(left, freq.get(left) - 1);
            if (freq.get(left) == 0) freq.remove(left);
            start++;
        }
        maxLen = Math.max(maxLen, end - start + 1);
    }
    return maxLen;
}
````