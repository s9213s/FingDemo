package com.hfy.fingdemo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CreditCard {
    @Id
    Long id;
    Long studentId;
    String userName;//持有者名字
    String cardNum;//卡号
    String whichBank;//哪个银行的
    @Generated(hash = 1914132820)
    public CreditCard(Long id, Long studentId, String userName, String cardNum,
            String whichBank) {
        this.id = id;
        this.studentId = studentId;
        this.userName = userName;
        this.cardNum = cardNum;
        this.whichBank = whichBank;
    }
    @Generated(hash = 1860989810)
    public CreditCard() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getStudentId() {
        return this.studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getCardNum() {
        return this.cardNum;
    }
    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
    public String getWhichBank() {
        return this.whichBank;
    }
    public void setWhichBank(String whichBank) {
        this.whichBank = whichBank;
    }
}
