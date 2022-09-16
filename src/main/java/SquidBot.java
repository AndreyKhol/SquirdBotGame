import db.DbHelper;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import support.KeyboardUtils;
import support.LanguageUtils;

import java.util.*;

public class SquidBot extends TelegramLongPollingBot {
    private final Map<Long, Chat> chatMap = new HashMap<>();

    private DbHelper dbHelper;
    private final Chat.ChatCallback callback = new Chat.ChatCallback() {
        @Override
        public void onGameAlreadyStarted(Long chatId, int replyMessageId) {
            sendMessage(chatId, replyMessageId, null, "Игра уже идет!");
        }

        @Override
        public Message onStartCompetition(Long chatId, int time) {
            String text = "Игра начнется скоро начнется! Хотите принять участие?\n\nОсталось " + LanguageUtils.getTimeLeft(time);
            return sendMessage(chatId, null, KeyboardUtils.getJoinKeyboard(), text);
        }

        @Override
        public void onFinishCompetition(Long chatId) {
            chatMap.remove(chatId);
            sendMessage(chatId, null,null, "Игра была заврешена((");
        }

        @Override
        public Message onStartGame(Long chatId, String rules, List<String> answers, int time) {
            String text = "Вопрос:\n " + rules+ "\n\nОсталось " + LanguageUtils.getTimeLeft(time);
            return sendMessage(chatId, null, KeyboardUtils.getAnswerQuestionKeyboard(answers), text);
        }

        @Override
        public void onDoubleJoined(String callbackQueryId) {
            sendCallback(callbackQueryId, "Ты уже в игре");
        }

        @Override
        public void onUpdateJoinTime(Message message, int playersQuantity, int time) {
            //TODO: Добавить вывод текущего количества игроков
            if (time > 0) {
                String text = message.getText().substring(0, message.getText().indexOf("Осталось "));
                text = text.concat("Осталось " + LanguageUtils.getTimeLeft(time));
                updateMessage(message, text, message.getReplyMarkup());
            } else {
                updateMessage(message, "Игра началась!\nПопреветствуем " +LanguageUtils.getPlayersQuantity(playersQuantity), null);
            }
        }

        @Override
        public void onUpdateQuestionTime(Message message, int playersQuantity, int time) {
            //TODO: Добавить вывод текущего количества игроков
            String text = message.getText().substring(0, message.getText().indexOf("Осталось "));
            text = text.concat("Осталось " + LanguageUtils.getTimeLeft(time));
            updateMessage(message, text, message.getReplyMarkup());
            dbHelper.testMethod();
        }


        @Override
        public void onGameFinished(Message message, String answer, int playersQuantity) {
            String text = message.getText().substring(0, message.getText().indexOf("Осталось "));
            text = "Раунд завершен!\n"+text+"\nПравильный ответ "+answer+"!\nПрошло " +LanguageUtils.getPlayersQuantity(playersQuantity);
            updateMessage(message, text, null);

        }

        @Override
        public void onPlayerJoined(String callbackQueryId) {
            sendCallback(callbackQueryId, "Отлично! Игра вот-вот начнется!");
        }

        @Override
        public void onPlayerAnswered(String callbackQueryId) {
            sendCallback(callbackQueryId, "Ответ принят!");
        }

        @Override
        public void onDoubleAnswered(String callbackQueryId) {
            sendCallback(callbackQueryId, "Упс... В этом раунде только одна попытка на ответ");
        }
    };

    public SquidBot(){
        dbHelper = new DbHelper();
        dbHelper.init();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatID = update.getMessage().getChatId();
            Chat chat;
            if (chatMap.containsKey(chatID)) {
                chat = chatMap.get(update.getMessage().getChatId());
            } else {
                chat = new Chat(callback, chatID);
                chatMap.put(chatID, chat);
            }
            chat.handleUpdate(update);

        } else if (update.hasCallbackQuery()) {
            try {
                if (chatMap.containsKey(update.getCallbackQuery().getMessage().getChatId())) {
                    chatMap.get(update.getCallbackQuery().getMessage().getChatId()).handleUpdate(update);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Message sendMessage(long chatId, Integer replyMessageId, ReplyKeyboard keyboard, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        if (replyMessageId != null) {
            sendMessage.setReplyToMessageId(replyMessageId);
        }
        if (keyboard != null) {
            sendMessage.setReplyMarkup(keyboard);
        }
        try {
            return execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendCallback(String callbackQueryId, String text) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setText(text);
        answerCallbackQuery.setShowAlert(true);
        answerCallbackQuery.setCallbackQueryId(callbackQueryId);
        try {
            execute(answerCallbackQuery);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void updateMessage(Message message, String text, InlineKeyboardMarkup keyboard) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId());
        if (keyboard != null) {
            editMessageText.setReplyMarkup(keyboard);
        }
        editMessageText.setText(text);
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "SquidChatBot";
    }

    @Override
    public String getBotToken() {
        return "5543126550:AAFsTxKMDRxZiQql6u33hP4YW7cnUkDLVGY";
    }
}