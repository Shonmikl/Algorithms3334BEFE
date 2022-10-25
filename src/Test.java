import java.nio.file.Path;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        String s = "S";
        s.hashCode();

        Map<Integer, String> map = new HashMap<>(); //90%
        map.put(1, "Nikolay"); //ind 2
        map.put(3, "TelRan"); // ind 2

        Map<Integer, Path> map1 = new TreeMap<>();

        List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(6);
        list.add(6);
    }
}