package support;

public class LanguageUtils {

    public static String getTimeLeft(int time) {
        String stringTime = String.valueOf(time);
        String result = stringTime;
        if (stringTime.endsWith("4") || stringTime.endsWith("3") || stringTime.endsWith("2")) {
            result += " секунды";
        } else if (stringTime.endsWith("1")) {
            result += " секунда";
        } else {
            result += " секунд";
        }
        return result;
    }

    public static String getPlayersQuantity(int playersQuantity) {
        String stringPlayersQuantity = String.valueOf(playersQuantity);
        String result = stringPlayersQuantity;
        if (stringPlayersQuantity.endsWith("1")) {
            result += " игрока";
        } else {
            result += " игроков";
        }
        return result;
    }
}
