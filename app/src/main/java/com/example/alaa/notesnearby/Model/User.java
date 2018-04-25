package com.example.alaa.notesnearby.Model;

public class User {
    private static String name;
    private static String password;
    private static String phone;
    public User(){
        this.name=name;
        this.password=password;
        this.phone=phone;
    }
    public String getName(){ return name;}
    public String getPassword(){return password;}
    public String getPhone(){return phone;}

    public void setName(String name){
        this.name = name;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

}
