package basic;

public class TestStackTrace {

    public static void f() throws Exception{
        throw new Exception("出问题啦！");
    }
    public static void g() throws Exception{
        f();
    }
    public static void main(String[] args) {
        try {
            g();
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("------------------------------");
            for(StackTraceElement elem : e.getStackTrace()) {
                System.out.println(elem);
            }
        }
    }
}
