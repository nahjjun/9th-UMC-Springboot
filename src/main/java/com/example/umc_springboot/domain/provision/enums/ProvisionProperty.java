package com.example.umc_springboot.domain.provision.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum ProvisionProperty {
    @Schema(description = "필수 약관")
    ESSENTIAL,
    @Schema(description = "선택 약관")
    SELECTIVE
}
