package games;

import support.GameInterface;
import java.util.ArrayList;
import java.util.Random;
public class ExerciseMathCount extends SuperGame{
    private String[] output = new String[2];;
    private int length;
    private ArrayList<Integer> numbers;
    private ArrayList<String> actions;
    private String rules;
    public ExerciseMathCount(int winnersQuantity){

        gameType = GameInterface.GameType.ExerciseMathCount;
        this.winnersQuantity = winnersQuantity;

        length = 4;
        numbers = new ArrayList<>();
        actions = new ArrayList<>();
        generateExercise();
        rules = generateRules();
        calculate();
    }
    public void generateExercise(){
        for (int i = 0; i < length; i++) {
            numbers.add(randomNumber());
            if (i < length - 1) {
                actions.add(randomAction());
            }
        }
        checkExerciseForDivision();
    }
    public void calculate(){
        while(actions.contains("*") || actions.contains("/")){
            int multiplyIndex = actions.indexOf("*");
            int divisionIndex = actions.indexOf("/");
            if(multiplyIndex >= 0 && divisionIndex >= 0) {
                if (multiplyIndex < divisionIndex) {
                    countMultiply(multiplyIndex);
                    remove(multiplyIndex);
                } else {
                    countDivision(divisionIndex);
                    remove(divisionIndex);
                }
            }
            if(multiplyIndex < 0){
                countDivision(divisionIndex);
                remove(divisionIndex);
            }
            if(divisionIndex < 0){
                countMultiply(multiplyIndex);
                remove(multiplyIndex);
            }
        }
        while(actions.contains("+") || actions.contains("-")){
            int plusIndex = actions.indexOf("+");
            int minusIndex = actions.indexOf("-");
            if(plusIndex >= 0 && minusIndex >= 0) {
                if (plusIndex < minusIndex) {
                    countPlus(plusIndex);
                    remove(plusIndex);
                } else {
                    countMinus(minusIndex);
                    remove(minusIndex);
                }
            }
            if(plusIndex < 0){
                countMinus(minusIndex);
                remove(minusIndex);
            }
            if(minusIndex < 0){
                countPlus(plusIndex);
                remove(plusIndex);
            }
        }
        answer = String.valueOf(numbers.get(0));
    }
    public void countMultiply(int i){
        numbers.set(i, numbers.get(i) * (numbers.get(i + 1)));
    }
    public void countDivision(int i){
        numbers.set(i, numbers.get(i) / (numbers.get(i + 1)));
    }
    public void countPlus(int i){
        numbers.set(i, numbers.get(i) + (numbers.get(i + 1)));
    }
    public void countMinus(int i){
        numbers.set(i, numbers.get(i) - (numbers.get(i + 1)));
    }
    public void remove(int i){
        actions.remove(i);
        numbers.remove(i + 1);
    }
    public void checkExerciseForDivision(){
        if(actions.contains("/")){
            for (int i = 0; i < actions.size(); i++) {
                if (actions.get(i).equals("/")) {
                    if(!(isDivisible(numbers.get(i), numbers.get(i+1))) || numbers.get(i) == numbers.get(i+1)){
                        numbers.set(i, multiply(numbers.get(i+1)));
                    }
                }
            }
        }
    }
    public boolean isDivisible(int a, int b){
        return a % b == 0;
    }
    public int multiply(int x){
        return x * (new Random().nextInt(1) + 2);
    }
    public int randomNumber(){
        return new Random().nextInt(8) + 2;
    }
    public String randomAction(){
        String[] actions = {"+","-","*","/"};
        String action;
        while (true){
            action = actions[new Random().nextInt(4)];
            if (action.equals("*") || action.equals("/")){
                if (this.actions.size() > 0 && this.actions.get(this.actions.size() - 1).equals(action)){
                    continue;
                }
            }
            if (action.equals("/")){
                if (this.actions.size() > 0 && this.actions.get(this.actions.size() - 1).equals("*")){
                    continue;
                }
            }
            break;
        }
        return action;
    }
    public String generateRules(){
        String exercise = "";
        for (int i = 0; i < length; i++) {
            exercise += numbers.get(i);
            if (i < length - 1) {
                exercise += actions.get(i);
            }
        }
        return exercise;
    }
    public String getRules(){
        return rules;
    }
}