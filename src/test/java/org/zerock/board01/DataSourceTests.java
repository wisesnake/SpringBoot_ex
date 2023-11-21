package org.zerock.board01;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
@Log4j2
public class DataSourceTests {

    @Autowired
    DataSource dataSource;

    @Test
    public void testDataSource() throws SQLException {
        @Cleanup
        Connection conn = dataSource.getConnection();

        log.info(conn);
        Assertions.assertNotNull(conn);

    }
}
