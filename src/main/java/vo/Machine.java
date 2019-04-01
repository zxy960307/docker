package vo;

import java.sql.Timestamp;

/**
 * Created by 41463 on 2019/3/30.
 */
public class Machine {

    private int id;
    private String name;
    private String ip;
    private int status;
    private Timestamp createTime;
    private boolean pingFlag;

    public boolean isPingFlag() {
        return pingFlag;
    }

    public void setPingFlag(boolean pingFlag) {
        this.pingFlag = pingFlag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
