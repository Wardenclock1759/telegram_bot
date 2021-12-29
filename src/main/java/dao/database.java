package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class database {
    private final String url = "";
    private final String user = "";
    private final String password = "";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }


    public void addUser(String chat_id, String steam_id) {
        String SQL = "INSERT INTO bot.\"User\" (chat_id,steam_id) "
                + "VALUES(?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.NO_GENERATED_KEYS)) {

            pstmt.setString(1, chat_id);
            pstmt.setString(2, steam_id);
            int affectedRows = pstmt.executeUpdate();
            System.out.println(affectedRows);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<String> getUserByChatID(String chat_id) {
        String SQL = "SELECT * FROM bot.\"User\" WHERE chat_id = '" + chat_id + "'";
        List<String> res = new ArrayList();
        try (Connection conn = connect();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SQL)) {
            while (rs.next())
            {
                res.add(rs.getString(2));
                System.out.println(rs.getString(1));
            }
            return res;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public int updateSteamID(String chat_id, String steam_id) {
        String SQL = "UPDATE bot.\"User\" "
                + "SET steam_id = ? "
                + "WHERE chat_id = ?";

        int affectedrows = 0;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, steam_id);
            pstmt.setString(2, chat_id);

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;
    }
}
