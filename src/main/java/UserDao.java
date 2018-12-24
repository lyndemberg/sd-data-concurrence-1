import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public void insert(User user){
        try {
            String sql = "INSERT INTO usuario (id,nome,updated,deleted) VALUES (?,?,?,?)";
            PreparedStatement statement = null;
            statement = con.prepareStatement(sql);
            statement.setInt(1,user.getId());
            statement.setString(2,user.getNome()+user.getId());
            statement.setBoolean(3,false);
            statement.setBoolean(4,false);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id){
        try {
            String sql = "UPDATE usuario SET updated=? WHERE id=?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setBoolean(1,true);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id){
        try {
            String sql = "UPDATE usuario SET deleted=? WHERE id=?";
            PreparedStatement statement = null;
            statement = con.prepareStatement(sql);
            statement.setBoolean(1,true);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getLastIdLocked() {
        int resultado = 0;
        try {
            String sql = "SELECT last_id FROM aux_lock ORDER BY last_id DESC LIMIT 1";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                resultado = result.getInt("last_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public void lockId(int id, String instanceApp){
        try {
            String sql = "INSERT INTO aux_lock (last_id, instance) VALUES (?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, instanceApp);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
