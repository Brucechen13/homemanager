package com.cc.homeserver.entity;

public class EnumTypeUtils {

    public enum StateType {
        NORMAL("正常"),
        LOCKED("禁用"),;
        private String name;

        StateType(String name){
            this.name = name;
        }
    }
}
