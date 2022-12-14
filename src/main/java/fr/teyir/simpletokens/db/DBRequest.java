package fr.teyir.simpletokens.db;

import fr.teyir.simpletokens.SimpleTokens;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBRequest {

    final private SimpleTokens plugin;
    final private Connection sql;

    public DBRequest(SimpleTokens plugin) {
        this.plugin = plugin;
        this.sql = plugin.getConnection().getConnection();
    }

    public void initDB() throws SQLException {

        final PreparedStatement preparedStatement = this.sql.prepareStatement(
                """
                        CREATE TABLE IF NOT EXISTS `simpletokens_data` (
                          `id` INT(11) NOT NULL AUTO_INCREMENT,
                          `uuid` VARCHAR(40) NOT NULL UNIQUE,
                          `tokens` BIGINT NOT NULL,
                          `last_updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
                          PRIMARY KEY (`id`)
                        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
                        ;"""
        );

        preparedStatement.executeUpdate();

    }

    public void initPlayer(String uuid, Integer tokens) throws SQLException {
        final PreparedStatement preparedStatement = this.sql.prepareStatement("INSERT INTO `simpletokens_data`( `uuid`, `tokens`) VALUES (?,?)");

        preparedStatement.setString(1, uuid);
        preparedStatement.setInt(2, tokens);

        preparedStatement.executeUpdate();
    }

    public boolean checkIfPlayerExist(String uuid) throws SQLException {
        final PreparedStatement preparedStatement = this.sql.prepareStatement("SELECT COUNT(id) as count FROM simpletokens_data WHERE uuid = ?");
        preparedStatement.setString(1, uuid);

        final ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            return rs.getInt("count") != 0;
        }

        return false;
    }

    /* -- DB REQUEST -- */

    public int getPlayerTokens(String uuid) throws SQLException {
        final PreparedStatement preparedStatement = sql.prepareStatement("SELECT tokens FROM simpletokens_data WHERE uuid = ?");

        preparedStatement.setString(1, uuid);

        final ResultSet rs = preparedStatement.executeQuery();

        if (rs.next())
            return (rs.getObject(1) != null) ? rs.getInt(1) : 0;

        return 0;
    }

    public void removePlayerTokens(String uuid, int amount) throws SQLException {
        final PreparedStatement preparedStatement = sql.prepareStatement("UPDATE simpletokens_data SET tokens = tokens - ?, last_updated = CURRENT_TIMESTAMP() WHERE uuid = ?");

        preparedStatement.setInt(1, amount);
        preparedStatement.setString(2, uuid);

        preparedStatement.executeUpdate();
    }

    public void removeAllPlayersTokens(int amount) throws SQLException {
        final PreparedStatement preparedStatement = sql.prepareStatement("UPDATE simpletokens_data SET tokens = tokens - ?, last_updated = CURRENT_TIMESTAMP()");

        preparedStatement.setInt(1, amount);

        preparedStatement.executeUpdate();
    }

    public void givePlayerTokens(String uuid, int amount) throws SQLException {
        final PreparedStatement preparedStatement = sql.prepareStatement("UPDATE simpletokens_data SET tokens = tokens + ?, last_updated = CURRENT_TIMESTAMP() WHERE uuid = ?");

        preparedStatement.setInt(1, amount);
        preparedStatement.setString(2, uuid);

        preparedStatement.executeUpdate();
    }

    public void giveAllPlayersTokens(int amount) throws SQLException {
        final PreparedStatement preparedStatement = sql.prepareStatement("UPDATE simpletokens_data SET tokens = tokens + ?, last_updated = CURRENT_TIMESTAMP()");

        preparedStatement.setInt(1, amount);

        preparedStatement.executeUpdate();
    }

    public void setPlayerTokens(String uuid, int amount) throws SQLException {
        final PreparedStatement preparedStatement = sql.prepareStatement("UPDATE simpletokens_data SET tokens = ?, last_updated = CURRENT_TIMESTAMP() WHERE uuid = ?");

        preparedStatement.setInt(1, amount);
        preparedStatement.setString(2, uuid);

        preparedStatement.executeUpdate();
    }

    public void setAllPlayersTokens(int amount) throws SQLException {
        final PreparedStatement preparedStatement = sql.prepareStatement("UPDATE simpletokens_data SET tokens = ?, last_updated = CURRENT_TIMESTAMP()");

        preparedStatement.setInt(1, amount);

        preparedStatement.executeUpdate();
    }

    public ResultSet getTopTokens() throws SQLException {
        final PreparedStatement preparedStatement = sql.prepareStatement("SELECT uuid, tokens FROM simpletokens_data ORDER BY tokens DESC LIMIT ?");

        preparedStatement.setInt(1, plugin.getConfiguration().getTopMaxDisplay());

        return preparedStatement.executeQuery();
    }
}

