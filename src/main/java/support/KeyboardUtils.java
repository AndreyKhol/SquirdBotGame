package support;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardUtils {

    public static InlineKeyboardMarkup getAnswerQuestionKeyboard(List<String> values) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText(values.get(0));
        button1.setCallbackData(values.get(0));
        row1.add(button1);
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText(values.get(1));
        button2.setCallbackData(values.get(1));
        row1.add(button2);
        keyboard.add(row1);


        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText(values.get(2));
        button3.setCallbackData(values.get(2));
        row2.add(button3);
        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setText(values.get(3));
        button4.setCallbackData(values.get(3));
        row2.add(button4);
        keyboard.add(row2);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getJoinKeyboard() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Принять участие");
        button.setCallbackData("join");
        row.add(button);
        keyboard.add(row);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }
}
