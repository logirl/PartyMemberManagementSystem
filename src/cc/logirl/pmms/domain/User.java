package cc.logirl.pmms.domain;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinxi on 2016/10/21.
 */
public class User implements Serializable {
    private Integer id;
    private String name;
    private String password;
    private Integer flag;
    private Map<String, String> error = new HashMap<>();

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Map<String, String> getError() {
        return error;
    }

    public void setError(Map<String, String> error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", flag=" + flag +
                '}';
    }

    /**
     * 验证用户名不能为空，密码必须是6~12位
     *
     * @return 通过返回true，否则false
     */
    public boolean validate() {
        boolean flag = true;
        if (StringUtils.isBlank(name)) {
            error.put("name", "用户名不能为空");
            flag = false;
        }
        if (StringUtils.isBlank(password) || password.length() < 6 || password.length() > 12) {
            error.put("password", "密码必须是6-12位");
            flag = false;
        }
        return flag;
    }
}
