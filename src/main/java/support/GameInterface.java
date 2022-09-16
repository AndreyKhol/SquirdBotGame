package support;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface GameInterface {

    enum GameType {
        DateGuess,ExerciseMathCount,HowMany,LetterCounter,MissingMath,SoundCounter
    }
    //Возвращает краткое описание игры и условие
    String getRules();

    //Получает сообщение от пользователя. Если ответ правильный, сохраняет значение
    void makeMove(String userName, String message);

    //Возвращает продолжительность игры в секундах
    int getGameTime();

    //Возвращает лист победителей
    List<String> getWinners();

    //Возвращает true, если все проходные места заняты
    boolean isAllWinnersDefined();

    //Возвращает список доступных ответов
    List<String> getOptions();

    //Возвращает объект сообщения из чата
    Message getGameMessage();

    //Устанавливает объект сообщения из чата
    void setGameMessage(Message gameMessage);

    //Возвращает правилтный ответ
    String getAnswer();

    //Возвращает тип игры
    GameType getGameType();
}
