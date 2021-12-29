package Commands;

import dao.Settings;
import dao.database;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import Bot.Bot;

import java.util.List;
import java.util.Objects;

public class SettingsCommand extends ServiceCommand {
    private Bot bot;
    public SettingsCommand(String identifier, String description, Bot bot) {
        super(identifier, description);
        this.bot = bot;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        Long chatId = chat.getId();
        Settings settings = Bot.getUserSettings(chatId);
        String userName = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());
        List<String> id = bot.db.getUserByChatID(String.valueOf(chatId));
        String text = "";
        if (id.size() != 0) text = "Ваш SteamID: " + id.get(0);
        else text = "Вы пока не ввели ваш SteamID. Пожалуйста укажите его, используя /setsettings";
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                 text);
    }
}
