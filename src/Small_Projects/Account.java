package Small_Projects;

public class Account {
    int balance = 1000;

    //이 메서드에 synchronized가 없다면 잔액이 마이너스가 나오게 된다.
    //이렇게 메서드의 선언부에 synchronized 명령어를 넣어도 되고, 메서드 안에서 synchronized(this) {...} 를 이용해서
    //부분적으로만 적용을 시킬 수도 있다. 경우에 따라 더 효율적이기도 함.
    public synchronized void withDraw(int money) throws InterruptedException {
        if(balance >= money) {
            Thread thread = Thread.currentThread();
            System.out.println(thread.getName() + "출금금액 --> " + money);
            Thread.sleep(1000);
            balance -= money;
            System.out.println(thread.getName() + "'s balance : " + balance);
        }
    }
}
