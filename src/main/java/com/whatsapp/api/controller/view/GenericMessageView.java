package com.whatsapp.api.controller.view;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GenericMessageView {
    @Schema(description = "A descriptive message")
    final private String message;
}
