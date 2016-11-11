package cc.logirl.pmms.domain;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinxi on 2016/10/22.
 */
public class Info implements Serializable {
    private Integer id;
    private Integer studentNumber;
    private String name;
    private Date activistTime;
    private Date developTime;
    private Integer status;
    private Integer score;
    private Integer batch = 1;
    private Map<String, Object> error = new HashMap<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getActivistTime() {
        return activistTime;
    }

    public void setActivistTime(Date activistTime) {
        this.activistTime = activistTime;
    }

    public Date getDevelopTime() {
        return developTime;
    }

    public void setDevelopTime(Date developTime) {
        this.developTime = developTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public Map<String, Object> getError() {
        return error;
    }

    public void setError(Map<String, Object> error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Info{" +
                "id=" + id +
                ", studentNumber=" + studentNumber +
                ", name='" + name + '\'' +
                ", activistTime=" + activistTime +
                ", developTime=" + developTime +
                ", status=" + status +
                ", score=" + score +
                ", batch=" + batch +
                '}';
    }

    public boolean validate() {
        boolean flag = true;
        if (!(studentNumber + "").matches("\\d{6}")) {
            error.put("studentNumber", "学号必须是6位数字");
            flag = false;
        }
        if (StringUtils.isBlank(name)) {
            error.put("name", "姓名不能是空啊");
            flag = false;
        }
        if (status != 0 && status != 1) {
            error.put("status", "此项填写有误");
            flag = false;
        }
        if (score < 0 || score > 100) {
            error.put("score", "党课成绩填写有误");
            flag = false;
        }
        return flag;
    }
}
