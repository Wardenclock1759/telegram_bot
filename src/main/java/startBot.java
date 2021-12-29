import Bot.Bot;
import dao.database;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class startBot {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            database db = new database();
            db.connect();
            botsApi.registerBot(new Bot("dota_itog_bot", "5087550179:AAHbqtKpvHAkacA2vM2OKg6VYvJyNcEigjI", db));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
