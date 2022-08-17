import java.util.Arrays;

/**
 * @author Freg
 * @time 2022/8/17  9:38
 */
public class splitDemo {
    public static void main(String[] args) {
        String line = ".123..456...78";
        String[] data = line.split("\\.",3);
        System.out.println(Arrays.toString(data));
    }
}
