package Bot;

import Commands.*;
import dao.Settings;
import dao.database;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

public final class Bot extends TelegramLongPollingCommandBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    @Getter
    @Setter
    public BotState currentState;

    @Getter
    private static final Settings defaultSettings = new Settings();
    @Getter
    private static Map<Long, Settings> userSettings;

    private final Commands.nonPrompt nonPrompt;
    @Getter
    @Setter
    public database db;

    public Bot(String botName, String botToken, database db) {
        super();
        BOT_NAME = botName;
        BOT_TOKEN = botToken;

        this.currentState = BotState.INITIAL;
        this.nonPrompt = new nonPrompt();
        this.db = db;
        register(new StartCommand("start", "Старт"));
        register(new SettingsCommand("settings", "Настройки", this));
        register(new SetSettings("setsettings", "Задать настройки", this));
        register(new SelfStatCommand("mystats", "Отобразить свою статистику", this));
        register(new StatCommand("stats", "Отобразить статистику игрока", this));
        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);

        userSettings = new HashMap<>();
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);

        String answer = nonPrompt.nonPromptExecute(chatId, userName, msg.getText(), currentState, db);
        setAnswer(chatId, userName, answer);
        setCurrentState(BotState.INITIAL);
    }

    public static Settings getUserSettings(Long chatId) {
        Map<Long, Settings> userSettings = Bot.getUserSettings();
        Settings settings = userSettings.get(chatId);
        if (settings == null) {
            return defaultSettings;
        }
        return settings;
    }

    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    private void setAnswer(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {

        }
    }
}
