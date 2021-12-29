package Commands;
import Bot.Bot;
import dao.Settings;
import dao.database;
import Bot.Utils;

public class nonPrompt {
    private database db;
    public String nonPromptExecute(long chatID, String userName, String text, BotState currentState, database db) {
        Settings settings;
        String answer;
        this.db = db;

        try {

            switch (currentState){
                case INITIAL:
                    answer = "Вы ввели неправильную команду.";
                    break;
                case INPUT_STEAMID_SETTINGS:
                    if (db.getUserByChatID(String.valueOf(chatID)).size() == 0) {
                        db.addUser(String.valueOf(chatID), text);
                    }
                    else {
                        db.updateSteamID(String.valueOf(chatID), text);
                    }
                    saveUserSettings(chatID, new Settings());
                    answer = "Steam ID успешно изменен";
                    break;
                case INPUT_STEAMID_QUERY:
                    answer = Utils.findStat(text);
                    break;
                default:
                    answer = "Дефолт";
            }
        }catch (Exception e) {
            answer = e.getMessage();
        }
        return answer;
    }

    private void saveUserSettings(Long chatId, Settings settings){
        if (!settings.equals(Settings.getDefaultSettings())) {
            Bot.getUserSettings().put(chatId, settings);
        } else {
            Bot.getUserSettings().remove(chatId);
        }
    }
}
