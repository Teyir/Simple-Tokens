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

    public void giveMoney(String uuid, int amount) {
        DBRequest dbRequest = new DBRequest(plugin);

        try {
            dbRequest.givePlayerTokens(uuid, amount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setMoney(String uuid, int amount) {
        DBRequest dbRequest = new DBRequest(plugin);

        try {
            dbRequest.setPlayerTokens(uuid, amount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMoney(String uuidSender, String uuidReceiver, int amount) {
        DBRequest dbRequest = new DBRequest(plugin);

        try {
            dbRequest.removePlayerTokens(uuidSender, amount);
            dbRequest.givePlayerTokens(uuidReceiver, amount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
