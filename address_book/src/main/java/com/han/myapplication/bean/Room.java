package com.han.myapplication.bean;

/**
 * Created by aaa on 2017/3/22.
 */

public class Room {
    String name;
    int typeID;

    public Room(String name, int typeID) {
        this.typeID = typeID;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getName() {
        return name;
    }

    public int getTypeID() {
        return typeID;
    }
}
