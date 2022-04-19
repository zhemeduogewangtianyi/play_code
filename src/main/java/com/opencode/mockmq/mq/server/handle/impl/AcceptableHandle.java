package com.opencode.mockmq.mq.server.handle.impl;

import com.opencode.mockmq.mq.server.handle.Handle;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class AcceptableHandle implements Handle<SocketChannel> {

    @Override
    public SocketChannel handle(SocketChannel socketChannel) {
        return null;
    }

    @Override
    public boolean supported(SelectionKey selectionKey) {
        return Handle.super.supported(selectionKey);
    }
}
