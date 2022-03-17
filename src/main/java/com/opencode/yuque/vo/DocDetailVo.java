package com.opencode.yuque.vo;

import com.opencode.yuque.domain.DocAbilities;
import com.opencode.yuque.po.DocDetailSerializer;
import lombok.Data;

@Data
public class DocDetailVo {
    private DocAbilities abilities;
    private DocDetailSerializer data;
}
