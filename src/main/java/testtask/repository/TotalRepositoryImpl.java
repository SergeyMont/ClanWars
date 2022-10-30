package testtask.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.sql.*;
@Singleton
public class TotalRepositoryImpl implements TotalRepository {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:mem:default";

    static final String USER = "sa";
    static final String PASS = "";

    final Logger log = LoggerFactory.getLogger(TotalRepositoryImpl.class);
    Connection conn = null;
    Statement stmt = null;

    public TotalRepositoryImpl(){
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            log.info("Connected to DB on URL"+DB_URL);
            stmt = conn.createStatement();
        }catch (SQLException se) {
            log.error("Connection to DB is fail");
            se.printStackTrace();
        } catch (Exception e) {
            log.error("Connection to DB is fail");
            e.printStackTrace();}
    }


    public void executeSql(String sql) {

        if(stmt!=null){
            try {
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ResultSet getUpdate(String sql) {
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

