package vo;

/**
 * Created by 41463 on 2019/3/30.
 */
public class Image {
    private int id;
    private String imageId;
    private String repoTags;
    private String machineIp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getRepoTags() {
        return repoTags;
    }

    public void setRepoTags(String repoTags) {
        this.repoTags = repoTags;
    }

    public String getMachineIp() {
        return machineIp;
    }

    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }
}
