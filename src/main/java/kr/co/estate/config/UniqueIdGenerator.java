package kr.co.estate.config;

import net.bytebuddy.utility.RandomString;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class UniqueIdGenerator implements IdentifierGenerator {
    public static final String NAME = "uniqueIdGenerator";
    public static final String STRATEGY = "kr.co.estate.config.UniqueIdGenerator";

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return generate();
    }

    public static String generate() {
        return RandomString.make(16);
    }
}
