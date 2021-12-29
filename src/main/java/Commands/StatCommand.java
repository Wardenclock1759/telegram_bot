package Commands;

import Bot.Bot;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StatCommand extends ServiceCommand {
    private final Bot bot;
    public StatCommand(String identifier, String description, Bot bot) {
        super(identifier, description);
        this.bot = bot;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        Long chatId = chat.getId();
        String userName = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());
        bot.setCurrentState(BotState.INPUT_STEAMID_QUERY);
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Введите ID вашего друга: ");
    }
}
