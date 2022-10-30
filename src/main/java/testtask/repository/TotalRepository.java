package testtask.repository;

import java.sql.ResultSet;

public interface TotalRepository {
    void executeSql(String sql);

    ResultSet getUpdate(String sql);
}
