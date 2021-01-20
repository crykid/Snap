package com.margin.snap.temp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Run {
    public static void main(String[] args) {
        String s = "abcabcbb";
        int i = lengthOfLongestSubstring(s);
        System.out.println("size = " + i);
    }

    static int lengthOfLongestSubstring(String s) {
        int start = 0, end = 0;
        ArrayList<Character> temp = new ArrayList<>();
        int length = s.length();
        int size = 0;
        while (start < length) {
            char c = s.charAt(end);
            if (end == length - 1) {
                size = Math.max(size, end - start);
                return size;
            } else if (temp.contains(c)) {
                int index = temp.indexOf(c);
                Iterator<Character> iterator = temp.iterator();
                while (iterator.next() != null && iterator.next().equals(c)) {
                    iterator.remove();
                }
                start = index + 1;
                size = Math.max(size, end - start);
            }
            end++;
            temp.add(c);
        }
        return size;
    }
}
