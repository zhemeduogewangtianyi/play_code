package com.opencode.mockmq.mq.server.handle;

import java.nio.channels.SelectionKey;

public interface Supported {

    default boolean supported(SelectionKey selectionKey){
        return false;
    }

}
