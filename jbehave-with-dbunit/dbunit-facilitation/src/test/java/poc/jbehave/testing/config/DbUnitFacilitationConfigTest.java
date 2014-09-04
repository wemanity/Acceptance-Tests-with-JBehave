/**
 * 
 */
package poc.jbehave.testing.config;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.fest.assertions.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Test Case for {@link DbUnitFacilitationConfig}.
 * 
 * @author Xavier Pigeon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class DbUnitFacilitationConfigTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbUnitFacilitationConfigTest.class);

    @Autowired
    private DbUnitFacilitationConfig dbUnitFacilitationConfig;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private Connection connection;
    @Autowired
    private IDatabaseTester databaseTester;

    @Configuration
    @EnableDbUnitFacilitation
    static class Config {

        @Bean
        DataSource dataSource() {
            DataSource dataSourceMock = createNiceMock(DataSource.class);
            try {
                expect(dataSourceMock.getConnection()).andReturn(connection());
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
            return dataSourceMock;
        }

        @Bean
        Connection connection() {
            Connection connectionMock = createNiceMock(Connection.class);
            return connectionMock;
        }

        @Bean
        IDatabaseTester databaseTester() {
            return createNiceMock(IDatabaseTester.class);
        }

        @PostConstruct
        private void replayMocks() {
            replay(dataSource(), connection(), databaseTester());
        }
    }

    @Test
    public void test() {
        assertThat(dbUnitFacilitationConfig).isNotNull();

        verify(dataSource, connection, databaseTester);
    }
}