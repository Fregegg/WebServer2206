/**
 * @author Freg
 * @time 2022/8/16  11:25
 * 单例模式
 */
public class Singleton {
    private static Singleton singleton = new Singleton();
    public static Singleton getInstance() {
        return singleton;
    }
    private Singleton(){}

}
