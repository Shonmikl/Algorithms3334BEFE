package hashMap;

//Отсортированный массив
public class K {

    //Перебор
    //O(n^2)
    public static int[] directSearch(int[] num, int k) {
        for (int i = 0; i < num.length; i++) {
            for (int j = i + 1; j < num.length; j++) {
                if(num[i] + num[j] == k) {
                    return new int[]{num[i], num[j]};
                }
            }
        }
        return new int[0];
    }

    //TwoFocus
    //O(n)
    public static int[] twoFocus(int[] num, int k) {
        int l = 0;
        int r = num.length-1;

        while (l < r) {
            int sum = num[l] + num[r];
            if(sum == k) {
                return new int[]{num[l], num[r]};
            }
            if(sum < k) {
                l++;
            } else r--;
        }
        return new int[0];
    }

    public static void main(String[] args) {
        //twoFocus()
    }
}