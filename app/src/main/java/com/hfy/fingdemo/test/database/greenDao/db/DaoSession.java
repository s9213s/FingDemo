package com.hfy.fingdemo.test.database.greenDao.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.hfy.fingdemo.bean.CreditCard;
import com.hfy.fingdemo.bean.IdCard;
import com.hfy.fingdemo.bean.Student;

import com.hfy.fingdemo.test.database.greenDao.db.CreditCardDao;
import com.hfy.fingdemo.test.database.greenDao.db.IdCardDao;
import com.hfy.fingdemo.test.database.greenDao.db.StudentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig creditCardDaoConfig;
    private final DaoConfig idCardDaoConfig;
    private final DaoConfig studentDaoConfig;

    private final CreditCardDao creditCardDao;
    private final IdCardDao idCardDao;
    private final StudentDao studentDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        creditCardDaoConfig = daoConfigMap.get(CreditCardDao.class).clone();
        creditCardDaoConfig.initIdentityScope(type);

        idCardDaoConfig = daoConfigMap.get(IdCardDao.class).clone();
        idCardDaoConfig.initIdentityScope(type);

        studentDaoConfig = daoConfigMap.get(StudentDao.class).clone();
        studentDaoConfig.initIdentityScope(type);

        creditCardDao = new CreditCardDao(creditCardDaoConfig, this);
        idCardDao = new IdCardDao(idCardDaoConfig, this);
        studentDao = new StudentDao(studentDaoConfig, this);

        registerDao(CreditCard.class, creditCardDao);
        registerDao(IdCard.class, idCardDao);
        registerDao(Student.class, studentDao);
    }
    
    public void clear() {
        creditCardDaoConfig.clearIdentityScope();
        idCardDaoConfig.clearIdentityScope();
        studentDaoConfig.clearIdentityScope();
    }

    public CreditCardDao getCreditCardDao() {
        return creditCardDao;
    }

    public IdCardDao getIdCardDao() {
        return idCardDao;
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

}
