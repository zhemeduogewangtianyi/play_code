package com.opencode.mockmq.mq.server.handle;

import java.nio.channels.SocketChannel;

public interface Handle<R> extends Supported {

    R handle(SocketChannel socketChannel);

}
