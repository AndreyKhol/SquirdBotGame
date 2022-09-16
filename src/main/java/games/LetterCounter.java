package games;

import support.GameInterface;
import support.Vocabulary;
import java.util.Arrays;
public class LetterCounter extends SuperGame{
    private Vocabulary vocabulary = new Vocabulary();
    private String word = vocabulary.RandomWord();
    private int total = 0;

    public LetterCounter(int winnersQuantity){

        gameType = GameInterface.GameType.LetterCounter;
        this.winnersQuantity = winnersQuantity;

        Arrays.asList(word.split("")).forEach(a -> total += 1);
        answer = String.valueOf(total);
    }

    public String getRules(){
        return "Сколько букв в слове " + word + "?";
    }
}

