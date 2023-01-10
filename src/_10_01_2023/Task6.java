package _10_01_2023;

import java.util.Arrays;

public class Task6 {
    //todo
    public static int singleNumber(int[] nums) {
        Arrays.sort(nums); // 0 2 2 3 3 ?
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != nums[i + 1]) {
                return nums[i];
            }
        }
        return 0;
    }

    //todo Mikhail
    public static int singleNumber1(int[] nums) {
        int xor = 0;
        for (int x : nums) {
            xor = xor ^ x;
        }
        return xor;
    }

    public static void main(String[] args) {
        //[2 2 3] [7 7 8 8 5] [8 9 8 9 1]
//        int[] arr = {2, 2, 0};
        int[] arr1 = {7, 7, 7, 5, 8, 8};
//        System.out.println(singleNumber1(arr));
        System.out.println(singleNumber1(arr1));
        System.out.println();
    }
}
