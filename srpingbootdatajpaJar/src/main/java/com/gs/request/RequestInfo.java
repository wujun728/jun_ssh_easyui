package com.gs.request;

import java.io.Serializable;

/**
 * @Author Administrator
 * @CreateDate 2018/4/17 11:02
 */
public class RequestInfo implements Serializable{

    private String name;

    private int age;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
