import java.sql.SQLException;
import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {

        UserDao userDao = new UserDao();

        int contador = 1;
        long t0 = System.currentTimeMillis();
        while(contador <= 10000){
            User joao = new User(contador, "JoãoDeDeus");

            try {
                userDao.insert(joao);
                userDao.update(joao.getId());
                userDao.delete(joao.getId());
                contador ++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        long t1 = System.currentTimeMillis();
        long tempoTotal = t1 - t0;

        System.out.println("Durou: " + tempoTotal);
    }
}
