import db.DbHelper;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import support.Logger;

import javax.swing.*;

public class Starter {

    public static void main(String[] args){
        try {
            Logger.log("main", "startApp");
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new SquidBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static void showFrame() {
        JFrame frame = new JFrame("SquidBot") {};
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
    }
}
