import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 最大匹配
 */
public class MaximumMatcher {

    /**
     * 正向最大匹配
     * @param text
     * @param words 最大匹配所用的词表
     * @return 最大匹配到的词，是words的子集
     */
    public static List<String> forwardMaxMatch(String text, Set<String> words){
        List<String> ret = new ArrayList<>();
        int length = text.length();
        int left = 0;
        int right;
        String word;
        boolean matched = false;

        // 从左到右尽可能划分出一段连续字符，使得其等于词典中的某个词，然后将这段连续字符提取出来，对余下的部分进行同样的操作。
        while (left < length){
            matched = false;
            for (right=length; right>left;right--){
                word = text.substring(left,right);
                if (words.contains(word)){
                    ret.add(word);
                    matched = true;
                    break;
                }
            }
            left = matched ? right:right+1;
        }
        return ret;
    }

    /**
     * 逆向最大匹配
     * @param text
     * @param words 最大匹配所用的词表
     * @return 最大匹配到的词，是words的子集
     */
    public static List<String> backwardMaxMatch(String text,Set<String> words){
        List<String> ret = new ArrayList<>();
        int length = text.length();
        int left = 0;
        int right = length;
        String word;
        boolean matched = false;

        // 从右到左尽可能划分出一段连续字符，使得其等于词典中的某个词，然后将这段连续字符提取出来，对余下的部分进行同样的操作。
        while (right > 0 ){
            matched = false;
            for (left=0; right>left;left++){
                word = text.substring(left,right);
                if (words.contains(word)){
                    ret.add(word);
                    matched = true;
                    break;
                }
            }
            right = matched ? left:left-1;
        }
        Collections.reverse(ret);
        return ret;
    }

    /**
     * 双向最大匹配
     * @param text
     * @param words 最大匹配所用的词表
     * @return 最大匹配到的词，是words的子集
     */
    public static List<String> biwardMaxMatch(String text,Set<String> words){
        List<String> ret = new ArrayList<>();
        List<String> forwardMatches = forwardMaxMatch(text,words);
        List<String> backwardMatches = backwardMaxMatch(text,words);

        // 如果两者相同，返回其中一个。这种情况占绝大多数。
        if (forwardMatches.equals(backwardMatches)){
            return forwardMatches;
        }
        // 如果两者的词数不同，则返回词数更少的那一个。
        else if (forwardMatches.size() < backwardMatches.size()){
            return forwardMatches;
        }
        else if (forwardMatches.size() > backwardMatches.size()){
            return backwardMatches;
        }
        // 否则，返回两者中单字更少的那一个。
        else{
            List<String> forwardSingles = forwardMatches.stream().filter(w->w.length()==1).collect(Collectors.toList());
            List<String> backwardSingles = backwardMatches.stream().filter(w->w.length()==1).collect(Collectors.toList());
            if (forwardMatches.size() < backwardMatches.size()) {
                return forwardMatches;
            }
            else if (forwardMatches.size() > backwardMatches.size()){
                return backwardMatches;
            }
            // 如果单字数也相同，返回逆向最长匹配的结果。
            else{
                return backwardMatches;
            }

        }

    }
}
