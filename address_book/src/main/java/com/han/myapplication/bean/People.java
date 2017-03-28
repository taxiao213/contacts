package com.han.myapplication.bean;

/**
 * Created by aaa on 2017/3/22.
 */

public class People  {

    String name;
    String number;
    String room;
    int typeID;

    public People(String name, String number, String room, int typeID) {
        this.name = name;
        this.number = number;
        this.room = room;
        this.typeID = typeID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getRoom() {
        return room;
    }

    public int getTypeID() {
        return typeID;
    }
}
