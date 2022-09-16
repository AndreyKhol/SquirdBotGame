package depricated;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class SquidBotOld extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();

            message.setText(update.getMessage().getText());
            message.setChatId(update.getMessage().getChatId());
            message.setReplyMarkup(getInlineKeyboardMarkup());
            try {
                //sendApiMethod(message);
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            //ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder
            /*try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }*/
/*
            if(game == null){
                game = new depricated.Game();
                SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
                message.setChatId(update.getMessage().getChatId().toString());
                message.setText(game.getGameRules());

                try {
                    execute(message); // Call method to send the message
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(game.getGameTimeMs()-3000);
                            SendMessage message1 = new SendMessage(); // Create a SendMessage object with mandatory fields
                            message1.setChatId(update.getMessage().getChatId().toString());
                            message1.setText("3");
                            execute(message1);

                            Thread.sleep(1000);
                            SendMessage message2 = new SendMessage(); // Create a SendMessage object with mandatory fields
                            message2.setChatId(update.getMessage().getChatId().toString());
                            message2.setText("2");
                            execute(message2);
                            SendPhoto photo = new SendPhoto();
                            InputFile file = new InputFile();

                            Thread.sleep(1000);
                            SendMessage message3 = new SendMessage(); // Create a SendMessage object with mandatory fields
                            message3.setChatId(update.getMessage().getChatId().toString());
                            message3.setText("1");
                            execute(message3);

                            Thread.sleep(1000);
                            SendMessage message4 = new SendMessage(); // Create a SendMessage object with mandatory fields
                            message4.setChatId(update.getMessage().getChatId().toString());
                            if(game.finishGame()){
                                message4.setText("Победа");
                            } else {
                                message4.setText("Поражение");
                            }
                            execute(message4);
                            game = null;
                        } catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
                t.start();
            } else {
                game.addMove(update.getMessage().getText());
            }*/

        } else if(update.hasCallbackQuery()){
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
            answerCallbackQuery.setText(update.getCallbackQuery().getData());
            answerCallbackQuery.setShowAlert(true);
            answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
            try {
                //sendApiMethod(message);
                execute(answerCallbackQuery); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

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

    private ReplyKeyboardMarkup addKeyboard(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Option 1"));
        row1.add(new KeyboardButton("Option 2"));
        rows.add(row1);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("Option 3"));
        row2.add(new KeyboardButton("Option 4"));
        row2.add(new KeyboardButton("Option 5"));
        row2.add(new KeyboardButton("Option 6"));
        rows.add(row2);
        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboardRemove removeKeyboard(){
        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
        replyKeyboardRemove.setRemoveKeyboard(true);
        return replyKeyboardRemove;
    }


    private void testCallBackButtons(){
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("test1");
        inlineKeyboardButton.setCallbackData("callbackData1");
    }

    private static InlineKeyboardMarkup getInlineKeyboardMarkup() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("test1");
        button1.setCallbackData("callbackData1");
        row1.add(button1);
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("test2");
        button2.setCallbackData("callbackData2");
        row1.add(button2);
        keyboard.add(row1);


        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("test3");
        button3.setCallbackData("callbackData3");
        row2.add(button3);
        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setText("test4");
        button4.setCallbackData("callbackData4");
        row2.add(button4);
        keyboard.add(row2);


        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }
    private void testEditMessage(Update update){
        Message sentMessage = null;
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("StartEditing");
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setReplyMarkup(getInlineKeyboardMarkup());
        try {
            sentMessage=  execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            sentMessage = null;
        }
        try {
            Thread.sleep(3000);
        } catch (Exception e){
            e.printStackTrace();
        }
        if(sentMessage != null) {
            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setMessageId(sentMessage.getMessageId());
            editMessageText.setChatId(sentMessage.getChatId());

            editMessageText.setReplyMarkup(null);

            editMessageText.setText(sentMessage.getText() + ". Edited");
            try {
                execute(editMessageText);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

}