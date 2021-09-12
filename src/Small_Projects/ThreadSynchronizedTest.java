package Small_Projects;

public class ThreadSynchronizedTest {
    public static void main(String[] args) {
        Task task = new Task();
            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);

            thread1.setName("t1-thread1");
            thread2.setName("t2-thread2");

            thread1.start();
            thread2.start();
    }

    // 헷갈리는데, Runnable interface를 구현하는 함수는 항상 Thread가 해야할 일을
    //정의한다고 생각하면 된다.
    static class Task implements Runnable {
        Account account = new Account();
        @Override
        public void run() {
            while(account.balance > 0) {
                int money = (int)(Math.random() * 3 + 1) * 100;
                try {
                    account.withDraw(money);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
