package cilicili.jz2.pojo;

import java.io.Serializable;
import java.time.LocalDate;



public class History implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer historyId;

    private Integer userId;

    private Integer videoId;

    private LocalDate watchTime;


    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public LocalDate getWatchTime() {
        return watchTime;
    }

    public void setWatchTime(LocalDate watchTime) {
        this.watchTime = watchTime;
    }


    @Override
    public String toString() {
        return "History{" +
                "historyId=" + historyId +
                ", userId=" + userId +
                ", videoId=" + videoId +
                ", watchTime=" + watchTime +
                "}";
    }
}