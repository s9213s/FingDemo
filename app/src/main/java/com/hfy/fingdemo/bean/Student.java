package com.hfy.fingdemo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.hfy.fingdemo.test.database.greenDao.db.DaoSession;
import com.hfy.fingdemo.test.database.greenDao.db.IdCardDao;
import com.hfy.fingdemo.test.database.greenDao.db.StudentDao;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;
import com.hfy.fingdemo.test.database.greenDao.db.CreditCardDao;

@Entity //@Entity 将我们的java普通类变为一个能够被greenDAO识别的数据库类型的实体类
public class Student {
    //@Id：主键，通过这个注解标记的字段必须是Long类型的，这个字段在数据库中表示它就是主键，并且它默认就是自增的；
    // (autoincrement = true)表示主键会自增，如果false就会使用旧值 。
    @Id(autoincrement = true)
    Long id;
    //@Property：设置一个非默认关系映射所对应的列名，默认是使用字段名，例如：@Property(nameInDb = "name")，可以自定义字段名，注意外键不能使用该属性
    //@Transient：表明这个字段不会被写入数据库，只是作为一个普通的java类字段，用来临时存储数据的，不会被持久化

    // @NotNull 设置数据库表当前列不能为空

    @Unique //  @Unique 该属性值必须在数据库中是唯一值
    int studentNo;//学号
    int age; //年龄
    String telPhone;//手机号
    String sex; //性别
    String name;//姓名
    String address;//家庭住址
    String schoolName;//学校名字
    String grade;//几年级
    //@ToOne：定义与另一个实体（一个实体对象）的关系
    //@ToMany：定义与多个实体对象的关系
    long idCardId;

    @ToOne(joinProperty = "idCardId")
    IdCard idCard;

    @ToMany(referencedJoinProperty = "studentId")
    List<CreditCard> creditCardsList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1943931642)
    private transient StudentDao myDao;

    @Generated(hash = 602561657)
    private transient Long idCard__resolvedKey;

    @Generated(hash = 754979344)
    public Student(Long id, int studentNo, int age, String telPhone, String sex, String name,
            String address, String schoolName, String grade, long idCardId) {
        this.id = id;
        this.studentNo = studentNo;
        this.age = age;
        this.telPhone = telPhone;
        this.sex = sex;
        this.name = name;
        this.address = address;
        this.schoolName = schoolName;
        this.grade = grade;
        this.idCardId = idCardId;
    }

    @Generated(hash = 1556870573)
    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(int studentNo) {
        this.studentNo = studentNo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

 

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1701634981)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getStudentDao() : null;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 34711231)
    public IdCard getIdCard() {
        long __key = this.idCardId;
        if (idCard__resolvedKey == null || !idCard__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            IdCardDao targetDao = daoSession.getIdCardDao();
            IdCard idCardNew = targetDao.load(__key);
            synchronized (this) {
                idCard = idCardNew;
                idCard__resolvedKey = __key;
            }
        }
        return idCard;
    }

    public long getIdCardId() {
        return this.idCardId;
    }

    public void setIdCardId(long idCardId) {
        this.idCardId = idCardId;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 57117707)
    public void setIdCard(@NotNull IdCard idCard) {
        if (idCard == null) {
            throw new DaoException(
                    "To-one property 'idCardId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.idCard = idCard;
            idCardId = idCard.getId();
            idCard__resolvedKey = idCardId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1268960764)
    public List<CreditCard> getCreditCardsList() {
        if (creditCardsList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CreditCardDao targetDao = daoSession.getCreditCardDao();
            List<CreditCard> creditCardsListNew = targetDao._queryStudent_CreditCardsList(id);
            synchronized (this) {
                if (creditCardsList == null) {
                    creditCardsList = creditCardsListNew;
                }
            }
        }
        return creditCardsList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 441911208)
    public synchronized void resetCreditCardsList() {
        creditCardsList = null;
    }

    /** To-one relationship, resolved on first access. */

}
