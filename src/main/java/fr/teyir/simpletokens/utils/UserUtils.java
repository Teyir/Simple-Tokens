package fr.teyir.simpletokens.utils;

import fr.teyir.simpletokens.SimpleTokens;
import fr.teyir.simpletokens.db.DBRequest;

import java.sql.SQLException;

public class UserUtils {

    private SimpleTokens plugin;

    public UserUtils(SimpleTokens plugin) {
        this.plugin = plugin;
    }


    public void removeMoney(String uuid, int amount) {
        DBRequest dbRequest = new DBRequest(plugin);

        try {
            dbRequest.removePlayerTokens(uuid, amount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
