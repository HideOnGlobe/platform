import java.util.*;

/**
 * @ProjectName: platform
 * @Package: PACKAGE_NAME
 * @Description: -
 * @Author: elison
 * @CreateDate: 2019/10/28 22:12
 * @UpdateDate: 2019/10/28 22:12
 **/
public class Solution {


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        if (wordDict.size() == 0) {
            return false;
        }
        Set<String> set = new HashSet<>(wordDict);
        List<Integer> lengthList = new LinkedList<>();
        for (String word : wordDict) {
            if (lengthList.size() != 0) {
                if (!lengthList.get(0).equals(word.length())) {
                    if (lengthList.get(0) < word.length()) {
                        lengthList.add(0, word.length());
                    } else {
                        lengthList.add(word.length());
                    }
                }
            } else {
                lengthList.add(word.length());
            }
        }
        return wordBreak(s, set, lengthList);
    }

    private boolean wordBreak(String s, Set<String> set, List<Integer> lengthList) {
        if (s == null || s.length() == 0 || set.contains(s)) {
            return true;
        }
        boolean result = false;
        for (Integer length : lengthList) {
            if (length >= s.length()) {
                return false;
            }
            String temp = s.substring(0, length);
            if (set.contains(temp) && !result) {
                result = wordBreak(s.substring(length, s.length()), set, lengthList);
            }
        }
        return result;
    }


    public static void main(String[] args) {

    }
}
