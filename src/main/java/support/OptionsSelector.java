package support;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class OptionsSelector {
    private int answer;
    private int answerPosition;
    private ArrayList<Integer> options;
    public OptionsSelector(int answer){
        this.answer = answer;
        this.options = new ArrayList<>(4);
    }
    public void generate(){
        int i = 0;
        while(i < 4) {
            int changer = new Random().nextInt((answer + 3) - (answer - 2)) + answer - 2;
            if (changer == answer || options.contains(changer)) {
                continue;
            }
            options.add(changer);
            i++;
        }
        answerPosition = new Random().nextInt(4);
        options.set(answerPosition, answer);
    }
    public void generatePositive(){
        if(answer >= 3){
            generate();
        }else {
            int i = 0;
            while (i < 4) {
                int changer = new Random().nextInt(5) + 1;
                if (changer == answer || options.contains(changer)) {
                    continue;
                }
                options.add(changer);
                i++;
            }
            answerPosition = new Random().nextInt(4);
            options.set(answerPosition, answer);
        }
    }
    public List<String> getOptions(){
        return options.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
    }
}
