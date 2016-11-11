package cc.logirl.pmms.json;

/**
 * Created by xinxi on 2016/10/23.
 */
public class JsonResult {
    /**
     * 0表示显示一些错误信息，1表示正常响应，2表示字段校验等错误
     */
    private int code;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
