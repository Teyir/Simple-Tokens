package fr.teyir.simpletokens.db;

import fr.teyir.simpletokens.SimpleTokens;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBRequest {

    final private SimpleTokens plugin;
    private Connection sql;

    public DBRequest(SimpleTokens plugin) {
        this.plugin = plugin;
        this.sql = plugin.getConnection().getConnection();
    }

    public void initDB() throws SQLException {

        final PreparedStatement preparedStatement = this.sql.prepareStatement(
                "CREATE TABLE IF NOT EXISTS `simpletokens_data` (\n" +
                        "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `uuid` VARCHAR(40) NOT NULL UNIQUE,\n" +
                        "  `tokens` BIGINT NOT NULL,\n" +
                        "  `last_updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,\n" +
                        "  PRIMARY KEY (`id`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;\n;"
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

        if(rs.next()) {
            return rs.getInt("count") != 0;
        }

        return false;
    }

    /* -- DB REQUEST -- */

    protected int getPlayerTokens(String uuid) throws SQLException {

        return 0;
    }
}

