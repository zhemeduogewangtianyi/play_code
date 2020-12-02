package com.opencode.code.mybatis.generator;

import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class OpenCodeIntrospectedTable extends IntrospectedTableMyBatis3Impl {

    @Override
    protected String calculateMyBatis3FallbackSqlMapNamespace() {
        StringBuilder sb = new StringBuilder();
        sb.append(calculateSqlMapPackage());
        sb.append('.');
        if (stringHasValue(tableConfiguration.getMapperName())) {
            sb.append(tableConfiguration.getMapperName());
        } else {
            sb.append(fullyQualifiedTable.getDomainObjectName());
            sb.append("Mapper"); //$NON-NLS-1$
        }
        return sb.toString();
    }

    //update DO file name
    protected void calculateModelAttributes() {
        String pakkage = calculateJavaModelPackage();

        StringBuilder sb = new StringBuilder();
        sb.append(pakkage);
        sb.append('.');
        sb.append(fullyQualifiedTable.getDomainObjectName());
        sb.append("DO");
        sb.append("Key"); //$NON-NLS-1$
        setPrimaryKeyType(sb.toString());

        sb.setLength(0);
        sb.append(pakkage);
        sb.append('.');
        sb.append(fullyQualifiedTable.getDomainObjectName());
        sb.append("DO");
        setBaseRecordType(sb.toString());

        sb.setLength(0);
        sb.append(pakkage);
        sb.append('.');
        sb.append(fullyQualifiedTable.getDomainObjectName());
        sb.append("DO");
        sb.append("WithBLOBs"); //$NON-NLS-1$
        setRecordWithBLOBsType(sb.toString());

        sb.setLength(0);
        sb.append(pakkage);
        sb.append('.');
        sb.append(fullyQualifiedTable.getDomainObjectName());
        sb.append("DO");
        sb.append("Example"); //$NON-NLS-1$
        setExampleType(sb.toString());
    }


}
