package demo.inherit;

/**
 * @author shm
 * @desc ******
 * @Date 2022/3/8 12:45
 */
public class DemoData {

    private String data;

    public DemoData(String data) {
        this.data = data;
    }

    public static class WebDemoData extends DemoData{
        private String data;

        public WebDemoData(String data) {
            super(data);
            this.data = data;
        }
    }

}
