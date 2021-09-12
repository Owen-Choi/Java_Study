package Small_Projects;

public class Thread_EX {
    public static void main(String[] args) {
        MyThread thread = new MyThread("I'm handsome guy");
        MyThread thread2 = new MyThread("I'm ugly guy");

        thread.start();
        thread2.start();

        System.out.println("Main Thread is end");
    }
}
