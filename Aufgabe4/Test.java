import java.util.Date;

public class Test {
    public static void main(String[] args) {
        Observation o = new OsmiaCornuta("test", new Date(), null);
        var o2 =new OsmiaCornuta("test2", new Date(), o);
        var oo = o2.earlier().next();
        System.out.println(oo.getComment());
    }
}