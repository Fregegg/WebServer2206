/**
 * @author Freg
 * @time 2022/8/16  11:25
 */
public class SingletonDemo {

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        Singleton singleton1 = Singleton.getInstance();
        System.out.println(singleton);
        System.out.println(singleton1);
    }
}
