package com.cc.homeserver.entity;

import lombok.Data;

@Data
public class SocketMessage {
    int type;
    String sender;
    String receiver;
    String msg;
}
