import games.*;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import support.GameInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chat {
    private final Object LOCK_OBJECT = new Object();

    private long chatID;
    private final int JOIN_TIME = 1;
    private List<String> players = new ArrayList<>();
    private ChatCallback callback;

    private GameInterface currentGame = null;

    public Chat(ChatCallback callback, long chatID) {
        this.callback = callback;
        this.chatID = chatID;
    }

    private ChatState chatState = ChatState.NOT_RUNNING;

    private enum ChatState {
        NOT_RUNNING, WAITING_PLAYERS, IN_GAME
    }

    public void handleUpdate(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().getText().equals("/start")) {
                handleStartCompetition(update);
            }
            if (update.getMessage().getText().equals("/end")) {
                handleFinishCompetition();
            }
        } else if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().equals("join")) {
                handleJoin(update);
            } else {
                handleAnswer(update);
            }
        }
    }

    private void handleFinishCompetition() {
        //TODO: Добавить полноценную остановку игры?...
        callback.onFinishCompetition(chatID);
    }

    private void handleStartCompetition(Update update) {
        if (chatState == ChatState.NOT_RUNNING) {
            chatState = ChatState.WAITING_PLAYERS;
            players.add(update.getMessage().getFrom().getUserName());
            Message message = callback.onStartCompetition(chatID, JOIN_TIME);
            startJoinThread(message);
        } else {
            callback.onGameAlreadyStarted(chatID, update.getMessage().getMessageId());
        }
    }

    private void handleJoin(Update update) {
        if (players.contains(update.getCallbackQuery().getFrom().getUserName())) {
            callback.onDoubleJoined(update.getCallbackQuery().getId());
        } else {
            players.add(update.getCallbackQuery().getFrom().getUserName());
            callback.onPlayerJoined(update.getCallbackQuery().getId());
        }
    }

    private void handleAnswer(Update update) {
        currentGame.makeMove(update.getCallbackQuery().getFrom().getUserName(), update.getCallbackQuery().getData());
        callback.onPlayerAnswered(update.getCallbackQuery().getId());
        if (currentGame.isAllWinnersDefined()) {
            finishGame(currentGame);
        }
    }

    private void finishGame(GameInterface gameForFinishing) {
        synchronized (LOCK_OBJECT) {
            if (gameForFinishing == currentGame) {
                callback.onGameFinished(currentGame.getGameMessage(), currentGame.getAnswer(), currentGame.getWinners().size());
                players = currentGame.getWinners();
                //TODO: Обновить статистику победивших игроков
                //TODO: Проверить, что осталось больше одного игрока
                startGame();
            }
        }
    }

    private void startJoinThread(Message message) {
        new Thread(() -> {
            int timeInSeconds = JOIN_TIME;
            while (timeInSeconds > 0) {
                int pause = calculatePause(timeInSeconds);
                timeInSeconds -= pause;
                try {
                    Thread.sleep(pause * 1000L);
                    callback.onUpdateJoinTime(message, players.size(), timeInSeconds);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //TODO: Если не набралось достаточное количество игроков, не начинать игру
            startGame();
        }).start();
    }

    private void startGameThread() {
        new Thread(() -> {
            GameInterface threadGame = currentGame;
            int timeInSeconds = threadGame.getGameTime();
            while (timeInSeconds > 0) {
                int pause = calculatePause(timeInSeconds);
                timeInSeconds -= pause;
                try {
                    Thread.sleep(pause * 1000L);
                    synchronized (LOCK_OBJECT) {
                        if (threadGame == currentGame && timeInSeconds > 0) {
                            callback.onUpdateQuestionTime(threadGame.getGameMessage(), players.size(), timeInSeconds);
                        } else {
                            break;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            finishGame(threadGame);
        }).start();
    }

    private void startGame() {
        chatState = ChatState.IN_GAME;
        chooseNewGame();
        //TODO: Передавать в игры, можно ли давать несколько ответов
        Message message = callback.onStartGame(chatID, currentGame.getRules(), currentGame.getOptions(), currentGame.getGameTime());
        currentGame.setGameMessage(message);
        startGameThread();
    }

    private void chooseNewGame() {
        int maxWinners = players.size() / 2;
        //TODO: УБРАТЬ ТЕСТОВЫЕ ДАННЫЕ
        maxWinners = 1;


        GameInterface.GameType newGameType = currentGame == null ? null : currentGame.getGameType();
        while (newGameType == null || (currentGame != null && newGameType == currentGame.getGameType())) {
            newGameType = GameInterface.GameType.values()[new Random().nextInt(GameInterface.GameType.values().length)];
        }
        switch (newGameType) {
            case HowMany:
                currentGame = new HowMany(maxWinners);
                break;
            case DateGuess:
                currentGame = new DateGuess(maxWinners);
                break;
            case ExerciseMathCount:
                currentGame = new ExerciseMathCount(maxWinners);
                break;
            case LetterCounter:
                currentGame = new LetterCounter(maxWinners);
                break;
            case MissingMath:
                currentGame = new MissingMath(maxWinners);
                break;
            case SoundCounter:
                currentGame = new SoundCounter(maxWinners);
                break;
        }
    }

    private int calculatePause(int time) {
        if (time > 15) {
            return 10;
        } else if (time > 5) {
            return 5;
        } else {
            return 1;
        }
    }

    public interface ChatCallback {
        void onGameAlreadyStarted(Long chatId, int replyMessageId);

        Message onStartCompetition(Long chatId, int time);

        void onFinishCompetition(Long chatId);

        Message onStartGame(Long chatId, String rules, List<String> answers, int time);

        void onPlayerJoined(String callbackQueryId);

        void onPlayerAnswered(String callbackQueryId);

        void onDoubleAnswered(String callbackQueryId);

        void onDoubleJoined(String callbackQueryId);

        void onUpdateJoinTime(Message message, int players, int time);

        void onUpdateQuestionTime(Message message, int players, int time);


        void onGameFinished(Message message, String answer, int playersQuantity);
    }


}

