package games;

import support.GameInterface;

import java.util.Random;

public class HowMany extends SuperGame{
    private int width;
    private int height;
    private String [][] table;
    private String target;
    private int answerInt;

    private String[] options = {"1","2","3","4","5","6","7","8","9","0"};

    public HowMany(int winnersQuantity){
        answerInt = 0;
        gameType = GameInterface.GameType.HowMany;
        this.winnersQuantity = winnersQuantity;

        height = 5;
        width = 3;
        table = new String[width][height];
        target = String.valueOf(new Random().nextInt(10));
        generate();
        answer = String.valueOf(answerInt);
    }
    public void generate(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                table[i][j] = String.valueOf(new Random().nextInt(10));
                if(table[i][j].equals(target)){
                    answerInt += 1;
                }
            }
        }
    }
    public String getTable(){
        String line = "";
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                line += table[i][j] + " ";
            }
            line += "\n";
        }
        return line;
    }

    public String getRules(){
        return getTable() + "Сколько раз " + target + " встречается среди цифр?";
    }
}
