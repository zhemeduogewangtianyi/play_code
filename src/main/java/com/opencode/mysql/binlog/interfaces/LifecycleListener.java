package com.opencode.mysql.binlog.interfaces;

import com.opencode.mysql.binlog.client.BinaryLogClient;

public interface LifecycleListener {


    /**
     * Called once client has successfully logged in but before started to receive binlog events.
     */
    void onConnect(BinaryLogClient client);

    /**
     * It's guarantied to be called before {@link #onDisconnect(BinaryLogClient)}) in case of
     * communication failure.
     */
    void onCommunicationFailure(BinaryLogClient client, Exception ex);

    /**
     * Called in case of failed event deserialization. Note this type of error does NOT cause client to
     * disconnect. If you wish to stop receiving events you'll need to fire client.disconnect() manually.
     */
    void onEventDeserializationFailure(BinaryLogClient client, Exception ex);

    /**
     * Called upon disconnect (regardless of the reason).
     */
    void onDisconnect(BinaryLogClient client);

}
