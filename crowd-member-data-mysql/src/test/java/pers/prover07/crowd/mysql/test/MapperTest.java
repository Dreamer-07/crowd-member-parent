package pers.prover07.crowd.mysql.test;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author by Prover07
 * @classname MapperTest
 * @description TODO
 * @date 2022/2/25 19:06
 */
@SpringBootTest
public class MapperTest {

    @Autowired
    private DataSource dataSource;

    private final Logger logger = LoggerFactory.getLogger(MapperTest.class);

    @Test
    public void testMysqlConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        logger.debug(connection.toString());
    }

}
