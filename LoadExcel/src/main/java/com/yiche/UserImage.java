package com.yiche;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserImage implements Serializable{


    private String id;   //cid+bid
    private String cid;
          /*更新电话两种方式  一电话模板  2 分类系统得到的分类是41 手机号码*/
    private Set<String> phoneNumber=new HashSet<String>();  //电话号码
    private Set<String> carType=new HashSet();    //车型


    private Integer credit=-1;     //是否贷款  -1初始值  0 没意向    1 回答有意向
    private Integer changeCar=-1;  //是否置换  -1 初始化值 0 没意向  1 有意向
    private Integer local=-1;   //是否当地     -1 初始化值 0 不是    1 是

    private String recentlyClassify; //最近分类
    private Set<String> recentlyCarType=new HashSet<String>();  //最近车型
    private String newClassify; //最新分类
    private Set<String> newCarType=new HashSet<String>();  //最新车型


    private  String content; // content为接收到的c端的内容

    public UserImage() {
    }

    public UserImage(String id, String cid, Set<String> phoneNumber, Set<String> carType, Integer credit, Integer changeCar, Integer local, String recentlyClassify, Set<String> recentlyCarType, String newClassify, Set<String> newCarType, String content) {
        this.id = id;
        this.cid = cid;
        this.phoneNumber = phoneNumber;
        this.carType = carType;
        this.credit = credit;
        this.changeCar = changeCar;
        this.local = local;
        this.recentlyClassify = recentlyClassify;
        this.recentlyCarType = recentlyCarType;
        this.newClassify = newClassify;
        this.newCarType = newCarType;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Set<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Set<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<String> getCarType() {
        return carType;
    }

    public void setCarType(Set<String> carType) {
        this.carType = carType;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getChangeCar() {
        return changeCar;
    }

    public void setChangeCar(Integer changeCar) {
        this.changeCar = changeCar;
    }

    public Integer getLocal() {
        return local;
    }

    public void setLocal(Integer local) {
        this.local = local;
    }

    public String getRecentlyClassify() {
        return recentlyClassify;
    }

    public void setRecentlyClassify(String recentlyClassify) {
        this.recentlyClassify = recentlyClassify;
    }

    public Set<String> getRecentlyCarType() {
        return recentlyCarType;
    }

    public void setRecentlyCarType(Set<String> recentlyCarType) {
        this.recentlyCarType = recentlyCarType;
    }

    public String getNewClassify() {
        return newClassify;
    }

    public void setNewClassify(String newClassify) {
        this.newClassify = newClassify;
    }

    public Set<String> getNewCarType() {
        return newCarType;
    }

    public void setNewCarType(Set<String> newCarType) {
        this.newCarType = newCarType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "UserImage{" +
                "id='" + id + '\'' +
                ", cid='" + cid + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", carType=" + carType +
                ", credit=" + credit +
                ", changeCar=" + changeCar +
                ", local=" + local +
                ", recentlyClassify='" + recentlyClassify + '\'' +
                ", recentlyCarType=" + recentlyCarType +
                ", newClassify='" + newClassify + '\'' +
                ", newCarType=" + newCarType +
                ", content='" + content + '\'' +
                '}';
    }
}
