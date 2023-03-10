package com.me.community.entity;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class AuditingEntity extends AuditingFields {

    // μμ  μΌμ
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modifiedAt;

    public void modified() {
        this.modifiedAt = LocalDateTime.now();
    }

}
