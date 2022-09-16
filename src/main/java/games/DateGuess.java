package games;

import support.GameInterface;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;
public class DateGuess extends SuperGame{
    private HashMap<String, String> dayNamesInRussian = new HashMap<>();
    private LocalDate today = LocalDate.now(), askedDate;
    private int daysDifference;

    public DateGuess(int winnersQuantity) {

        gameType = GameInterface.GameType.DateGuess;
        this.winnersQuantity = winnersQuantity;

        createDaysInRussian();
        // новое число +-7
        daysDifference = changeDay();
        // новая дата
        askedDate = today.plusDays(daysDifference);
        answer = String.valueOf(askedDate.getDayOfMonth());
    }

    public String getRules(){
        String wasOrWill = "";
        if(daysDifference > 0){
            wasOrWill += "будет";
        } else{
            wasOrWill += "было";
        }
        return "Какое число " + wasOrWill + " " + dayNamesInRussian.get(String.valueOf(askedDate.getDayOfWeek())) + "?";
    }

    public int changeDay(){
        int date = 0;
        while(true) {
            date = new Random().nextInt(15) - 7;
            if (date != 0) {
                break;
            }
        }
        return date;
    }
    public void createDaysInRussian(){
        dayNamesInRussian.put("MONDAY","в понедельник");
        dayNamesInRussian.put("TUESDAY","во вторник");
        dayNamesInRussian.put("WEDNESDAY","в среду");
        dayNamesInRussian.put("THURSDAY","в четверг");
        dayNamesInRussian.put("FRIDAY","в пятницу");
        dayNamesInRussian.put("SATURDAY","в суббота");
        dayNamesInRussian.put("SUNDAY","в воскресенье");
    }
}
