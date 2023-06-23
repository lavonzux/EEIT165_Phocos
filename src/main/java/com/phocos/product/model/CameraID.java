package com.phocos.product.model;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CameraID implements IdentifierGenerator {

    private int currentValue = 1;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        int generatedValue = currentValue;
        currentValue += 2; // 基数递增

        return generatedValue;
    }
}