package games;

import support.GameInterface;

import java.util.Random;
public class MissingMath extends SuperGame{
    private int[] numbers = new int[5];
    private int selector = new Random().nextInt(3);
    private int questionPlace;

    public MissingMath(int winnersQuantity){

        gameType = GameInterface.GameType.MissingMath;
        this.winnersQuantity = winnersQuantity;

        generateNumLine();
        questionPlace = new Random().nextInt(numbers.length);
        answer = String.valueOf(numbers[questionPlace]);
    }
    public void generateNumLine(){
        if(selector == 0 || selector == 1) {
            int firstNum = new Random().nextInt(53) + 1;
            int counter = new Random().nextInt(7) + 2;
            if(selector == 0) {
                for (int i = 0; i < numbers.length; i++) {
                    numbers[i] = firstNum;
                    firstNum += counter;
                }
            }
            if(selector == 1) {
                for (int i = numbers.length - 1; i >= 0; i--) {
                    numbers[i] = firstNum;
                    firstNum += counter;
                }
            }
        }
        if(selector == 2 || selector == 3) {
            int firstNum = new Random().nextInt(3) + 2;
            int counter = new Random().nextInt(1) + 2;
            if(selector == 2) {
                for (int i = 0; i < numbers.length; i++) {
                    numbers[i] = firstNum;
                    firstNum *= counter;
                }
            }
            if(selector == 3) {
                for (int i = numbers.length - 1; i >= 0; i--) {
                    numbers[i] = firstNum;
                    firstNum *= counter;
                }
            }
        }
    }

    public String getRules(){
        String rules = "";
        for (int i = 0; i < numbers.length; i++) {
            if(i == questionPlace){
                rules += "? ";
            }else {
                rules += numbers[i] + " ";
            }
        }
        return rules;
    }
}
