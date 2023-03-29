import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:mem://localhost/~/jdbc-practice;MODE=MySQL;DB_CLOSE_DELAY=-1";
    private static final String DB_USERNAME = "sa";
    private static final String PASSWORD = "";
    private static final int MAXIMUM_POOL_SIZE = 40;

    private static final DataSource ds; // 얘 하나로 재사용하니까 static 하게 미리 만들어놓음

    static {
        /*
         *  각 함수들의 의미를 공부해보자.
         */
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(DB_DRIVER);
        hikariDataSource.setJdbcUrl(DB_URL);
        hikariDataSource.setUsername(DB_USERNAME);
        hikariDataSource.setPassword(PASSWORD);
        hikariDataSource.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
        hikariDataSource.setMinimumIdle(MAXIMUM_POOL_SIZE);

        ds = hikariDataSource;
    }

    public static Connection getConnection() { // 원시적인 방법의 Connection 가져오기.
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static DataSource getDataSource() {
        return ds;
    }
}
