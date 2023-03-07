package com.me.community.entity;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class AuditingEntity extends AuditingFields {

    // 수정 일자
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modifiedAt;

    public void modified() {
        this.modifiedAt = LocalDateTime.now();
    }

}
