package db;

import org.sqlite.jdbc4.JDBC4ResultSet;
import support.Logger;

import javax.rmi.CORBA.Util;
import java.sql.*;

public class DbHelper {

    private Connection conn;

    public void init() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:someDB.db");
            createTableChat();
            createTablePlayer();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void createTableChat() {
        try {
            //TODO: Добавить поле для количества игр
            ResultSet resultSet = conn.createStatement().executeQuery("PRAGMA table_info(chat)");
            if (((JDBC4ResultSet) resultSet).emptyResultSet) {
                conn.createStatement().execute("create table chat (" +
                        "cht_id INTEGER primary key autoincrement," +
                        "cht_uid INTEGER)");
            }
            resultSet.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void createTablePlayer() {
        try {
            ResultSet resultSet = conn.createStatement().executeQuery("PRAGMA table_info(player)");
            if (((JDBC4ResultSet) resultSet).emptyResultSet) {
                conn.createStatement().execute("create table player (" +
                        "plr_id INTEGER primary key autoincrement," +
                        "plr_chatId INTEGER REFERENCES chat(cht_id) ON DELETE CASCADE," +
                        "plr_name TEXT," +
                        "plr_gameWins INTEGER," +
                        "plr_competitionWins INTEGER)");
            }
            resultSet.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public Long insertChat(Long chatUid) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("insert into chat (cht_uid) values (?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, chatUid);
            preparedStatement.executeUpdate();

            return getInsertedID(preparedStatement);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Long getInsertedID(PreparedStatement preparedStatement) throws SQLException {
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getLong(1);
        }
        return null;
    }


    public Long insertPlayer(Long chatUid, String name) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("insert into player (plr_plr_chatId, plr_name) values (?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, chatUid);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();

            return getInsertedID(preparedStatement);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public synchronized void testMethod(){
        Logger.log("testMethod", "start synchronized testMethod");
        try{
            Thread.sleep(10000);
        } catch (Exception e){
            e.printStackTrace();
        }
        Logger.log("testMethod", "end synchronized testMethod");
    }


}
