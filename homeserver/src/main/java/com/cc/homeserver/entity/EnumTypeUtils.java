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

    public enum ImageStateType{
        PUBLIC("公开"),
        PRIVATEALL("组间私有"),
        PRIVATEGROUP("组内私有"),;
        private String name;

        ImageStateType(String name){
            this.name = name;
        }
    }
}
