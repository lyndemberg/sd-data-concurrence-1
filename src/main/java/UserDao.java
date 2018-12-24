import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {

    private Connection con;

    public UserDao() {
        try {
            this.con = ConFactory.getConnectionPostgres();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(User user) throws SQLException {
            String sql = "INSERT INTO usuario (id,nome,updated,deleted) VALUES (?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,user.getId());
            statement.setString(2,user.getNome()+user.getId());
            statement.setBoolean(3,false);
            statement.setBoolean(4,false);
            statement.executeUpdate();
    }

    public void update(int id) throws SQLException {
            String sql = "UPDATE usuario SET updated=? WHERE id=?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setBoolean(1,true);
            statement.setInt(2, id);
            statement.executeUpdate();
    }

    public void delete(int id) throws SQLException {
            String sql = "UPDATE usuario SET deleted=? WHERE id=?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setBoolean(1,true);
            statement.setInt(2, id);
            statement.executeUpdate();
    }
}
