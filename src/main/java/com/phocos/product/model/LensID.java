package com.phocos.product.model;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import jakarta.persistence.Query;

public class LensID implements IdentifierGenerator {

     private int currentValue = 2;
        private boolean skipZero = true;


    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        int generatedValue = currentValue;

        if (skipZero && generatedValue == 0) {
            generatedValue = 2;
        }
        currentValue += 2; 

        // 檢查生成的奇數是否已存在於數據庫中
        while (isDuplicate(generatedValue, session)) {
            generatedValue += 2; // 自動往下搜索
        }

        return generatedValue;
    }

    private boolean isDuplicate(int value, SharedSessionContractImplementor session) {

        Query query = session.createQuery("SELECT COUNT(l.productID) FROM Lens l WHERE l.productID = :value");
        query.setParameter("value", value);
        Long count = (Long) query.getSingleResult();

        return count > 0;
    }
}