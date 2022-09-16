package support;

import java.util.Random;
public class Vocabulary {
    private final String[] words = {"адрес","азарт","актер","акула","акция","алмаз","альфа","ангар","ангел"};

    public String RandomWord(){
        return words[new Random().nextInt(words.length)];
    }
}
