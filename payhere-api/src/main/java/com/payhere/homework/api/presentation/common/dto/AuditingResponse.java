package com.payhere.homework.api.presentation.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditingResponse {

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

}
