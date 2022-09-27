package org.example.teahouse.core.db;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.UserType;

public class UuidUserType implements UserType<UUID> {
    @Override
    public int getSqlType() {
        return StandardBasicTypes.UUID.getSqlTypeCode();
    }

    @Override
    public Class<UUID> returnedClass() {
        return UUID.class;
    }

    @Override
    public boolean equals(UUID x, UUID y) {
        return x.equals(y);
    }

    @Override
    public int hashCode(UUID x) {
        return x.hashCode();
    }

    @Override
    public UUID nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        String string = rs.getString(position);
        return string != null ? UUID.fromString(string) : null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, UUID value, int index, SharedSessionContractImplementor session) throws SQLException {
        st.setString(index, value != null ? value.toString() : null);
    }

    @Override
    public UUID deepCopy(UUID value) {
        return UUID.fromString(value.toString());
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(UUID value) {
        return value;
    }

    @Override
    public UUID assemble(Serializable cached, Object owner) {
        return null;
    }

    @Override
    public UUID replace(UUID detached, UUID managed, Object owner) {
        return null;
    }
}
