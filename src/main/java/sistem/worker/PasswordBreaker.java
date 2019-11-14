package sistem.worker;

public class PasswordBreaker extends Thread {
    @Override
    public void run() {
        super.run();
        while (true){
            System.out.println("... quebrando senha");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
