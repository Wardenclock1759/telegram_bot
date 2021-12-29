package Bot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String findStat(String steamID) {
        List<String> res = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.opendota.com/api/players/" + steamID + "/wl")
                .method("GET", null)
                .addHeader("Accept", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            JsonParser parser = new JsonParser();
            assert response.body() != null;
            String data = response.body().string();
            JsonObject obj = parser.parse(data).getAsJsonObject();
            int num_win = obj.get("win").getAsInt();
            int num_lose = obj.get("lose").getAsInt();
            double  wl = Math.round((double) num_win / ((double) num_win + (double) num_lose) * 100);

            res.add(String.valueOf(num_win));
            res.add(String.valueOf(num_lose));
            res.add(String.valueOf(wl));

            return "🎯 " + res.get(0) + "\n" + "❌ " + res.get(1) + "\n" + "⚖ " + res.get(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
