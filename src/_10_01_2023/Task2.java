package _10_01_2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Task2 {

    //todo решить двумя указателями + добавить проверку на (> target)
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int comp = target - nums[i];
            if(map.containsKey(comp) && map.get(comp) !=i) {
                return new int[]{i, map.get(comp)};
            }
        }
        return new int[]{};
    }

    public static void main(String[] args) {
        int[] array = {15, 3, 50, 510, 8, 9, 6, 50};
        // 1 2 3 4 5 6 7 8 9
        int target = 100;
        System.out.println(Arrays.toString(twoSum(array, target)));
    }
}