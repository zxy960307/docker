package vo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by 41463 on 2019/3/15.
 */
public class Container {



    private String containerId;
    private int createAdminId;
    private Timestamp createTime;
    private int status;
    private String image;
    private int id;

    public String getMachineIp() {
        return machineIp;
    }

    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }

    private String machineIp;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public int getCreateAdminId() {
        return createAdminId;
    }

    public void setCreateAdminId(int createAdminId) {
        this.createAdminId = createAdminId;
    }
}
