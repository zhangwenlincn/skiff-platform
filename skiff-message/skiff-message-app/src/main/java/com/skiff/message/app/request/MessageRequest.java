package com.skiff.message.app.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class MessageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "content can not be blank")
    private String content;

    @NotBlank(message = "messageId can not be blank")
    private String messageId;

}
