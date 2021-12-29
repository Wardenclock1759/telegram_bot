package dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Settings {
    private String password;
    private String steamID;

    public Settings(String password, String steamID) {
        this.password = password;
        this.steamID = steamID;
    }

    public Settings(){
        this.password = "";
        this.steamID = "";
    }

    public static Settings getDefaultSettings() {
        return new Settings();
    }
}
