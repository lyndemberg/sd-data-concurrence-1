
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {

    public void insert(User user) throws SQLException {
        Connection con = null;
        try{
            con = ConFactory.getConnectionPostgres();
            String sql = "INSERT INTO usuario (id,nome,updated,deleted) VALUES (?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,user.getId());
            statement.setString(2,user.getNome()+user.getId());
            statement.setBoolean(3,false);
            statement.setBoolean(4,false);
            statement.executeUpdate();
        }finally {
            if(con != null)
                con.close();
        }
    }

    public void update(int id) throws SQLException {
        Connection con = null;
        try{
            con = ConFactory.getConnectionPostgres();
            String sql = "UPDATE usuario SET updated=? WHERE id=?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setBoolean(1,true);
            statement.setInt(2, id);
            statement.executeUpdate();
        }finally {
            if(con != null)
                con.close();
        }
    }

    public void delete(int id) throws SQLException {
        Connection con = null;
        try{
            con = ConFactory.getConnectionPostgres();
            String sql = "UPDATE usuario SET deleted=? WHERE id=?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setBoolean(1,true);
            statement.setInt(2, id);
            statement.executeUpdate();
        }finally {
            if(con != null)
                con.close();
        }
    }


}
