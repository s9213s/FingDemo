package com.hfy.fingdemo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class IdCard {
    @Id
    Long id;
    String userName;//用户名
    @Unique
    String idNo;//身份证号

    @Generated(hash = 141234591)
    public IdCard(Long id, String userName, String idNo) {
        this.id = id;
        this.userName = userName;
        this.idNo = idNo;
    }

    @Generated(hash = 1500073048)
    public IdCard() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
