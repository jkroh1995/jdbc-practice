import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {

    /*
     * 초기 작업을 수행하기 위한 작업
     */
    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db_schema.sql")); //테이블을 만듦
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    void createTest() throws SQLException {
        UserDao userDao = new UserDao(); //Data accass object 의 줄임말. 즉, DB 작업을 수행할 때 UserDAO에 위임

        userDao.create(new User("jkRoh", "password", "name", "email"));

        User user = userDao.findByUserId("jkRoh");

        assertThat(user).isEqualTo(new User("jkRoh", "password", "name", "email"));
    }
}
