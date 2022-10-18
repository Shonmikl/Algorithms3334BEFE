package lesson6dynamic;

import java.util.Random;

public class StringRandom {
    //todo доработать код
    public String getRandomString(int randomStringLength) {
        StringBuilder result = null;
        String source = "QWERTYUIOPASDFGHJKLZXCVBNM";
        for (int i = 0; i < randomStringLength; i++) {
            assert result != null;
            result.append(source.charAt(new Random().nextInt(26)));
        }
        return result.toString();
    }
}