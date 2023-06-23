package com.phocos.product.model;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class LensID implements IdentifierGenerator {

    private int currentValue = 2; // 起始值为2，即第一个生成的值为2

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        int generatedValue = currentValue;
        currentValue += 2; // 偶数递增

        return generatedValue;
    }
}