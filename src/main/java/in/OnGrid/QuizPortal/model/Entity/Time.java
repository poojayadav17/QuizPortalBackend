package in.OnGrid.QuizPortal.model.Entity;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class Time {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", nullable = false)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", nullable = false)
    private Date updateTime;

    @PrePersist
    private void onCreate() {
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    @PreUpdate
    private void onUpdate() {
        this.updateTime = new Date();
    }
}