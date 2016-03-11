package me.nithanim.urlplaceholder.util;

import java.util.regex.Pattern;

public class MatcherHelper {
    public static Pattern PATH_PARAMETER_PATTERN = Pattern.compile("\\{([\\w\\d]+)\\}");

    /**
     * Replaces all occurrences of "{placeholder}" with
     * "replacement".
     *
     * @param sb the StringBuilder to modify
     * @param placeholder the placeholder between left and right
     * @param replacement the String to replace all matched placeholders with
     */
    public static void replaceAll(StringBuilder sb, String placeholder, String replacement) {
        int start = 0, end = 0;
        
        while((start = indexOf(sb, '{', start)) > -1) {
            end = indexOf(sb, '}', start);
            if(sb.substring(start, end).equals(placeholder)) {
                sb.replace(start, end, replacement);
            }
            start++;
        }
    }
    
    public static int indexOf(StringBuilder sb, char c, int start) {
        for(int i = start; i < sb.length(); i++) {
            if(sb.charAt(i) == c) {
                return i;
            }
        }
        return -1;
    }

    private MatcherHelper() {
    }
}
