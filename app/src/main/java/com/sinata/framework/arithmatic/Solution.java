package com.sinata.framework.arithmatic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjq
 * @Date 28/4/2021
 * @Time 5:02 PM
 * @Describe:
 */


class Solution {
    /**
     * 输入：l1 = [2,4,3], l2 = [5,6,4]
     * 输出：[7,0,8]
     * 解释：342 + 465 = 807.
     */
    public static void main(String[] args) {
//        ListNode l1 = new ListNode(2);
//        ListNode l2;
        lengthOfLongestSubstring("abbsacsd");
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode root = new ListNode(0);
        ListNode cursor = root;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int valuel1 = l1 == null ? 0 : l1.val;
            int valuel2 = l2 == null ? 0 : l2.val;
            int sumVal = valuel1 + valuel2 + carry;
            carry = sumVal / 10;
            ListNode sumListNode = new ListNode(sumVal % 10);
            cursor.next = sumListNode;
            cursor = sumListNode;

            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        return root.next;
    }

    //给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
    public static int lengthOfLongestSubstring(String s) {
        // 记录字符上一次出现的位置
        int[] last = new int[128];
        for (int i = 0; i < 128; i++) {
            last[i] = -1;
        }
        int n = s.length();

        int res = 0;
        int start = 0; // 窗口开始位置
        for (int i = 0; i < n; i++) {
            int index = s.charAt(i);
            start = Math.max(start, last[index] + 1);
            res = Math.max(res, i - start + 1);
            last[index] = i;
        }
        return res;
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int left = (m + n + 1) / 2;
        int right = (m + n + 2) / 2;
        return (sortBinarySearch(nums1, 0, nums2, 0, left) + sortBinarySearch(nums1, 0, nums2, 0, right)) / 2.0;
    }

    private double sortBinarySearch(int[] nums1, int i, int[] nums2, int j, int k) {
        if (i >= nums1.length) return nums2[j + k - 1];
        if (j >= nums2.length) return nums1[i + k - 1];
        if (k == 1) {
            return Math.min(nums1[i], nums2[j]);
        }
        int midVal1 = i - k / 2 + 1 < nums1.length ? nums1[i - k / 2 + 1] : Integer.MAX_VALUE;
        int midVal2 = j - k / 2 + 1 < nums2.length ? nums2[j - k / 2 + 1] : Integer.MAX_VALUE;
        if (midVal1 < midVal2) {
            return sortBinarySearch(nums1, i + k / 2, nums2, j, k - k / 2);
        } else {
            return sortBinarySearch(nums1, i, nums2, j + k / 2, k - k / 2);
        }
    }

    public int reverse(int x) {
        long n = 0;
        while (x != 0) {
            n = n * 10 + x % 10;
            x = x / 10;
        }

        return n == n ? (int) n : 0;
    }


    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        long n = 0;
        while (x != 0) {
            n = n * 10 + x % 10;
            x /= 10;
        }

        return x == n;
    }


    public int romanToInt(String s) {
        int n = s.length();
        int sum = 0;
        for (int i = 0; i < n; i++) {
            switch (s.charAt(i)) {
                case 'I':
                    sum += 1;
                    break;

                case 'V':
                    sum += 5;
                    break;
                case 'X':
                    sum += 10;
                    break;
                case 'L':
                    sum += 50;
                    break;
                case 'C':
                    sum += 100;
                    break;
                case 'D':
                    sum += 500;
                    break;
                case 'M':
                    sum += 1000;
                    break;
            }

            if (i != 0) {
                if ((s.charAt(i) == 'X' || s.charAt(i) == 'V') && (s.charAt(i - 1) == 'I')) {
                    sum = sum - 1 * 2;
                } else if ((s.charAt(i) == 'C' || s.charAt(i) == 'L') && (s.charAt(i - 1) == 'X')) {
                    sum = sum - 10 * 2;
                } else if ((s.charAt(i) == 'M' || s.charAt(i) == 'D') && (s.charAt(i - 1) == 'C')) {
                    sum = sum - 100 * 2;
                }
            }
        }

        return sum;
    }

    //编写一个函数来查找字符串数组中的最长公共前缀
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        StringBuilder prefixSb = new StringBuilder();
        String first = strs[0];
        int n = first.length();
        boolean isEquals = true;
        for (int i = 0; i < n; i++) {
            char charStr = first.charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (!(strs[j].length() > i && strs[j].charAt(i) == charStr)) {
                    isEquals = false;
                    break;
                }
            }

            if (!isEquals) {
                break;
            }
            if (isEquals) {
                prefixSb.append(charStr);
            }
        }

        String str = prefixSb.toString();
        if (str == null || str.isEmpty()) {
            return "";
        }
        return str;
    }

    //有效字符串需满足：
    //左括号必须用相同类型的右括号闭合。
    //左括号必须以正确的顺序闭合
    public static boolean isValid(String s) {
        int n = s.length() / 2;
        for (int i = 0; i < n; i++) {
            s = s.replace("()", "").replace("{}", "").replace("[]", "");
        }
        return s.length() == 0;
    }


    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode res = l1.val < l2.val ? l1 : l2;
        res.next = mergeTwoLists(res.next, l1.val >= l2.val ? l1 : l2);
        return res;
    }

    public int removeDuplicates(int[] nums) {
        if (nums.length < 2) return nums.length;
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[j] != nums[i]) nums[++j] = nums[i];
        }
        return ++j;
    }

    public static int removeElement(int[] nums, int val) {
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) nums[j++] = nums[i];
        }
        System.out.println(nums);
        return j;
    }

    public int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }

    public int searchInsert(int[] nums, int target) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                index = i;
                break;
            }
        }
        if (nums[nums.length - 1] < target) {
            index = nums.length;
        }
        return index;
    }


//    public int[] decode(int[] encoded) {
//        int[] perm = new int[encoded.length];
//        for (int i = 0; i < encoded.length; i++) {
//            perm[i] = encoded[i] - perm[i + 1];
//        }
//    }

}
