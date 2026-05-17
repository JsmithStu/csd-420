/**
 * Fan class represents a single record in the `fans` table.  It contains
 * simple getters and setters for the columns ID, firstname, lastname and
 * favoriteteam.  This object is used to transfer data between the user
 * interface and the database access layer.
 */
public class Fan {

    private int id;
    private String firstname;
    private String lastname;
    private String favoriteTeam;

    /**
     * Default constructor.
     */
    public Fan() {}

    /**
     * Constructor that initializes all fields.
     *
     * @param id           the unique identifier of the fan
     * @param firstname    the fan's first name
     * @param lastname     the fan's last name
     * @param favoriteTeam the fan's favorite team
     */
    public Fan(int id, String firstname, String lastname, String favoriteTeam) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.favoriteTeam = favoriteTeam;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFavoriteTeam() {
        return favoriteTeam;
    }

    public void setFavoriteTeam(String favoriteTeam) {
        this.favoriteTeam = favoriteTeam;
    }

    @Override
    public String toString() {
        return "Fan{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", favoriteTeam='" + favoriteTeam + '\'' +
                '}';
    }
}