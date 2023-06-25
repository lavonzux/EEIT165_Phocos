package com.phocos.product.model;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import jakarta.persistence.Query;

public class CameraID implements IdentifierGenerator {

     private int currentValue = 1;
        private boolean skipZero = true;


    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        int generatedValue = currentValue;

        if (skipZero && generatedValue == 0) {
            generatedValue = 1;
        }
        currentValue += 2; // 基數遞增

        // 檢查生成的奇數是否已存在於數據庫中
        while (isDuplicate(generatedValue, session)) {
            generatedValue += 2; // 自動往下搜索
        }

        return generatedValue;
    }

    private boolean isDuplicate(int value, SharedSessionContractImplementor session) {
        // 執行查詢以檢查數據庫中是否存在相同的奇數值
        Query query = session.createQuery("SELECT COUNT(c.productID) FROM Camera c WHERE c.productID = :value");
        query.setParameter("value", value);
        Long count = (Long) query.getSingleResult();

        return count > 0;
    }
}