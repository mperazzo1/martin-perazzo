package com.whatsapp.api.controller.view;


import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatView {
    @NotEmpty
    private String chatName;
    @NotNull
    private List<Integer> userIds;    
}
