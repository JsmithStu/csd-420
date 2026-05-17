import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.*;

/**
 * Basic unit tests for the {@link FanDAO} class.  The tests use an H2
 * in‑memory database so that they do not depend on an external MySQL
 * installation.  A simple schema is created and populated before each
 * test to provide deterministic results.  These tests verify that
 * {@link FanDAO#getFanById(int)} returns the expected record and that
 * {@link FanDAO#updateFan(Fan)} updates rows correctly.
 */
public class FanDAOTest {

    private Connection connection;
    private FanDAO fanDAO;

    @BeforeEach
    public void setUp() throws Exception {
        // Create an H2 in‑memory database.  Auto‑close ensures the
        // database is destroyed after the connection closes.
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE fans (" +
                    "id INT PRIMARY KEY, " +
                    "firstname VARCHAR(25), " +
                    "lastname VARCHAR(25), " +
                    "favoriteteam VARCHAR(25))");
            stmt.execute("INSERT INTO fans VALUES (1, 'Alice', 'Anderson', 'Lions')");
            stmt.execute("INSERT INTO fans VALUES (2, 'Bob', 'Baker', 'Tigers')");
        }
        // Override the DAO's connection factory by subclassing it so that
        // it returns our in‑memory connection instead of a MySQL connection.
        fanDAO = new FanDAO() {
            @Override
            protected Connection getConnection() throws SQLException {
                return connection;
            }
        };
    }

    @AfterEach
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void testGetFanById() throws Exception {
        Fan fan = fanDAO.getFanById(1);
        Assertions.assertNotNull(fan, "Fan with ID 1 should exist");
        Assertions.assertEquals(1, fan.getId());
        Assertions.assertEquals("Alice", fan.getFirstname());
        Assertions.assertEquals("Anderson", fan.getLastname());
        Assertions.assertEquals("Lions", fan.getFavoriteTeam());

        Fan missing = fanDAO.getFanById(99);
        Assertions.assertNull(missing, "Non‑existent fan should return null");
    }

    @Test
    public void testUpdateFan() throws Exception {
        Fan fan = fanDAO.getFanById(2);
        Assertions.assertNotNull(fan);
        fan.setFirstname("Robert");
        fan.setLastname("Barker");
        fan.setFavoriteTeam("Eagles");
        boolean updated = fanDAO.updateFan(fan);
        Assertions.assertTrue(updated, "Update should return true when a row is modified");

        // Reload and verify changes
        Fan updatedFan = fanDAO.getFanById(2);
        Assertions.assertEquals("Robert", updatedFan.getFirstname());
        Assertions.assertEquals("Barker", updatedFan.getLastname());
        Assertions.assertEquals("Eagles", updatedFan.getFavoriteTeam());
    }
}