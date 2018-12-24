import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        final UserDao userDao = new UserDao();

        int contador = 1;
        final long t0 = System.currentTimeMillis();
        final BlockingQueue<Integer> queueInsert = new ArrayBlockingQueue<Integer>(20);
        final BlockingQueue<Integer> queueUpdate = new ArrayBlockingQueue<Integer>(20);
        final BlockingQueue<Integer> queueDelete = new ArrayBlockingQueue<Integer>(20);
        while(contador <= 1000){
            queueInsert.put(contador);

            Runnable runnableInsert = new Runnable() {
                public void run() {
                    try {
                        Integer take = queueInsert.take();
                        User user = new User(take,"JoãoDeDeus");
                        userDao.insert(user);
                        queueUpdate.put(take);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            Runnable runnableUpdate = new Runnable() {
                public void run() {
                    try {
                        Integer take = queueUpdate.take();
                        userDao.update(take);
                        queueDelete.put(take);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            Runnable runnableDelete = new Runnable() {
                public void run() {

                    try {
                        Integer take = queueDelete.take();
                        userDao.delete(take);

                        if (take == 1000) {
                            long t1 = System.currentTimeMillis();
                            long tempoTotal = t1 - t0;

                            System.out.println("Durou: " + tempoTotal);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            Thread threadInsert = new Thread(runnableInsert);
            Thread threadUpdate = new Thread(runnableUpdate);
            Thread threadDelete = new Thread(runnableDelete);
            threadInsert.start();
            threadUpdate.start();
            threadDelete.start();

            contador ++;

        }
    }
}
