package ad.mynfcdemo.utils;

/**
 * 日志实体类
 * Created by 农启兵 on 2017/11/24.
 */

public class LogNfc {
    private  String xihao;
    private  String caozuo;
    private String time;

    public LogNfc(String xihao, String caozuo, String time) {
        this.xihao = xihao;
        this.caozuo = caozuo;
        this.time = time;
    }

    public String getXihao() {
        return xihao;
    }
    public String getCaozuo() {
        return caozuo;
    }

    public String getTime() {
        return time;
    }
}
