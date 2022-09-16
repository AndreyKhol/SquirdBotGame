package games;

import support.GameInterface;
import support.Vocabulary;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SoundCounter extends SuperGame{
    private Vocabulary vocabulary = new Vocabulary();
    private String word = vocabulary.RandomWord();
    private String[] a = {"а","о","у","э","ы","я","ё","е","ю","и"};
    private String[] b = {"б","в","г","д","з","ж","п","ф","к","т","с","ш","л","м","н","р","й","х","ц","ч","щ"};
    private int counterA = 0;
    private int counterB = 0;
    int whichSound;

    public SoundCounter(int winnersQuantity){

        gameType = GameInterface.GameType.SoundCounter;
        this.winnersQuantity = winnersQuantity;

        whichSound = new Random().nextInt(2);
        String[] letters = word.split("");
        for (String letter : letters) {
            if(contains(letter, a)){
                counterA += 1;
            }
            if(contains(letter, b)){
                counterB += 1;
            }
        }
        if(whichSound == 0){
            answer = String.valueOf(counterA);
        }else{
            answer = String.valueOf(counterB);
        }
    }
    public boolean contains(String letter, String[] sounds){
        List<String> list = Arrays.asList(sounds);
        return list.contains(letter);
    }

    public String getRules(){
        String selection = "";
        if(whichSound == 0){
            selection = "гласных";
        }else{
            selection = "согласных";
        }
        return "Сколько " + selection + " в слове " + word + "?";
    }
}