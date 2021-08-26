package com.opencode.dingding.entity.context;

import com.opencode.dingding.entity.ding.CheckLoginMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseContext {

    private String res;
    private CheckLoginMessage message;

}
