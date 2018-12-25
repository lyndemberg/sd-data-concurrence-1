import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    private static String INSTANCE_APP;
    private static String PATH_FILE_LOCK_SHARED;
    private static int CAPACITY_QUEUES;

    public static void main(String[] args) throws InterruptedException, IOException {
        INSTANCE_APP = args[0];
        PATH_FILE_LOCK_SHARED = args[1];
        CAPACITY_QUEUES = Integer.valueOf(args[2]);

        File fileLock = new File(PATH_FILE_LOCK_SHARED);
        RandomAccessFile raf = new RandomAccessFile(fileLock, "rw");
        FileChannel channel = raf.getChannel();

        final UserDao userDao = new UserDao();

        int contador = 1;
        final int limite = 1000;
        final long t0 = System.currentTimeMillis();
        final BlockingQueue<Integer> queueInsert = new ArrayBlockingQueue<Integer>(CAPACITY_QUEUES);
        final BlockingQueue<Integer> queueUpdate = new ArrayBlockingQueue<Integer>(CAPACITY_QUEUES);
        final BlockingQueue<Integer> queueDelete = new ArrayBlockingQueue<Integer>(CAPACITY_QUEUES);
        while(contador <= limite){
            //LOCK DATABASE BETWEEN FILE LOCK SHARED
                FileLock lock = channel.lock();
                int lastIdLocked = userDao.getLastIdLocked();
                if(lastIdLocked == limite){
                    //JÁ HOUVE RESERVAS ATÉ O LIMITE
                    lock.release();
                    imprimirTempo(t0);
                    break;
                }else{
                    //RESERVAR NOVO ID
                    contador = lastIdLocked + 1;
                    userDao.lockId(contador, INSTANCE_APP);
                    lock.release();
                }
            //UNLOCK DATABASE

            queueInsert.put(contador);

            Runnable runnableInsert = new Runnable() {
                public void run() {
                    try {
                        Integer take = queueInsert.take();
                        User user = new User(take,"JoãoDeDeus");
                        userDao.insert(user);
                        queueUpdate.put(take);
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
                        if (take == limite) {
                            imprimirTempo(t0);
                        }
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

    private static void imprimirTempo(long inicio){
        long t1 = System.currentTimeMillis();
        long tempoTotal = t1 - inicio;
        System.out.println(INSTANCE_APP + " - Durou: " + tempoTotal);
    }
}
