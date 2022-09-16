package games;

import org.telegram.telegrambots.meta.api.objects.Message;
import support.GameInterface;
import support.OptionsSelector;

import java.util.ArrayList;
import java.util.List;

public abstract class SuperGame implements GameInterface{
    protected Message message;

    private boolean isFinished = false;
    private final int MAX_TIME = 10;
    private ArrayList<String> winners = new ArrayList<>();
    protected int winnersQuantity;
    protected String answer;
    protected GameInterface.GameType gameType;

    public void makeMove(String userName, String message){
        if(message.equals(String.valueOf(answer))){
            if(!isAllWinnersDefined()) {
                if (!winners.contains(userName)) {
                    winners.add(userName);
                }
            }
        }else{
            winners.remove(userName);
        }
    }

    public List<String> getWinners(){
        return winners;
    }

    public int getGameTime(){
        return MAX_TIME;
    }

    public List<String> getOptions(){
        OptionsSelector optionsSelector = new OptionsSelector(Integer.parseInt(answer));
        switch (gameType){
            case MissingMath:
            case ExerciseMathCount:
                optionsSelector.generate();
                break;
            case DateGuess:
            case HowMany:
            case SoundCounter:
            case LetterCounter:
                optionsSelector.generatePositive();
                break;
        }
        return optionsSelector.getOptions();
    }
    public Message getGameMessage(){
        return message;
    }

    public void setGameMessage(Message message){
        this.message = message;
    }
    public boolean isAllWinnersDefined(){
        if(winners.size() >= winnersQuantity){
            return true;
        }
        return false;
    }
    public abstract String getRules();
    
    public String getAnswer(){
        return answer;
    }

    public GameType getGameType(){
        return gameType;
    }


}
