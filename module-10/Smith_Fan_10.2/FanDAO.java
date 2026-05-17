import java.sql.*;

/**
 * FanDAO encapsulates all database interactions for the {@code fans} table.
 * It provides methods to retrieve and update fan records without exposing
 * JDBC details to the rest of the application.  The DAO uses a JDBC
 * connection to a MySQL database named {@code databasedb} with the
 * credentials provided in the assignment description.  You should not
 * create or drop the table in this class – it assumes the table exists.
 */
public class FanDAO {

    /** JDBC URL pointing at the databasedb database with credentials encoded. */
    private static final String JDBC_URL =
            "jdbc:mysql://localhost:3306/databasedb?user=student1&password=pass";

    /** Name of the MySQL JDBC driver.  Modern versions of the driver
     * automatically register themselves so the explicit call to
     * {@code Class.forName} is not strictly required, but it is left here to
     * document the dependency. */
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Retrieves a single fan record by ID.
     *
     * @param id the primary key value to search for
     * @return a Fan object populated with the record data, or {@code null}
     *         if no matching record could be found
     * @throws SQLException if a database access error occurs
     */
    public Fan getFanById(int id) throws SQLException {
        String sql = "SELECT id, firstname, lastname, favoriteteam FROM fans WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Fan fan = new Fan();
                    fan.setId(rs.getInt("id"));
                    fan.setFirstname(rs.getString("firstname"));
                    fan.setLastname(rs.getString("lastname"));
                    fan.setFavoriteTeam(rs.getString("favoriteteam"));
                    return fan;
                }
                return null;
            }
        }
    }

    /**
     * Updates the given fan record in the database.  Only the firstname,
     * lastname and favoriteteam columns are modified; the id remains fixed.
     *
     * @param fan the fan to update; the id must be set
     * @return {@code true} if the update modified exactly one row, or
     *         {@code false} otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean updateFan(Fan fan) throws SQLException {
        if (fan == null) {
            throw new IllegalArgumentException("fan must not be null");
        }
        String sql = "UPDATE fans SET firstname = ?, lastname = ?, favoriteteam = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fan.getFirstname());
            ps.setString(2, fan.getLastname());
            ps.setString(3, fan.getFavoriteTeam());
            ps.setInt(4, fan.getId());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated == 1;
        }
    }

    /**
     * Opens a new database connection using the configured JDBC URL.  The
     * caller is responsible for closing the returned connection.  The
     * connection uses auto-commit mode, which is sufficient for the simple
     * operations in this assignment.
     *
     * @return a new {@link Connection} instance
     * @throws SQLException if the driver cannot be loaded or a connection
     *                      cannot be established
     */
    protected Connection getConnection() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found. Make sure the driver is on the classpath.", e);
        }
        return DriverManager.getConnection(JDBC_URL);
    }
}