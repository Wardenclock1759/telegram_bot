package Commands;

import Bot.Bot;
import dao.Settings;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import Bot.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelfStatCommand extends ServiceCommand {
    private Bot bot;
    public SelfStatCommand(String identifier, String description, Bot bot) {
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
        if (id.size() != 0) {
            text = Utils.findStat(id.get(0));
        }
        else text = "Вы пока не ввели ваш SteamID. Пожалуйста укажите его, используя /setsettings";
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                text);
    }


}
