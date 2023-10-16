//package com.opencode.code.mybatis.interceptor;
//
//import com.alibaba.fastjson.JSON;
//import com.opencode.code.dao.AliXchangeBigDataMapMapper;
//import org.apache.ibatis.executor.parameter.ParameterHandler;
//import org.apache.ibatis.executor.statement.RoutingStatementHandler;
//import org.apache.ibatis.executor.statement.StatementHandler;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.ParameterMapping;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.session.Configuration;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ReflectionUtils;
//
//import java.lang.reflect.Field;
//import java.sql.Statement;
//import java.text.DateFormat;
//import java.util.*;
//
//@Intercepts(
//        {
//            @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
////            @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
////            @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
//        }
//)
//@Component
//public class BigDataMapMybatisInterceptor implements Interceptor , ApplicationContextAware {
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//
//        Object target = invocation.getTarget();
//        StatementHandler statementHandler = (StatementHandler)target;
//
//        BoundSql boundSql = statementHandler.getBoundSql();
//        if(target instanceof RoutingStatementHandler){
//
//            Field delegate = ReflectionUtils.findField(target.getClass(), "delegate");
//            delegate.setAccessible(true);
//            Object delegateObj = delegate.get(target);
//            Field mappedStatement = ReflectionUtils.findField(delegateObj.getClass(),"mappedStatement");
//            mappedStatement.setAccessible(true);
//            Object mappedStatementObj = mappedStatement.get(delegateObj);
//            Field id = ReflectionUtils.findField(mappedStatementObj.getClass(), "id");
//            id.setAccessible(true);
//            String mapperNameAndMethodName = (String)id.get(mappedStatementObj);
//
//            if((AliXchangeBigDataMapMapper.class.getName() + ".selectByPrimaryKey").equals(mapperNameAndMethodName)){
//                ParameterHandler parameterHandler = ((RoutingStatementHandler) target).getParameterHandler();
//
//                //boundSql.getSql();
//                String sql = showSql(boundSql);
//
//                Object parameterObject = boundSql.getParameterObject();
//                List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();
//                System.out.println(sql);
//                String mSql = sql + " and 1 = 1";
//                Field field = boundSql.getClass().getDeclaredField("sql");
//                field.setAccessible(true);
//                field.set(boundSql, mSql);
//
//                String updatestr= JSON.toJSONString(boundSql);
//            }
//
//        }
//
//        Object proceed = invocation.proceed();
//        return proceed;
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//
//    }
//
//    private String getParameterValue(Object obj) {
//        //to_timestamp(to_char(sysdate,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS')
//        String value = null;
//        if (obj instanceof String) {
//            value = "'" + obj.toString() + "'";
//        } else if (obj instanceof java.sql.Timestamp) {
//            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
//            value = "to_timestamp(to_char(" + formatter.format(obj) + ",'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS')";
//        }
//        else if (obj instanceof Date) {
//            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
//            value = "'" + formatter.format(obj) + "'";
////	            System.out.println(value);
//        } else {
//            if (obj != null) {
//                value = obj.toString();
//            } else {
//                value = "";
//            }
//
//        }
//        return value;
//    }
//
//    public String showSql(BoundSql boundSql) {
//        Object parameterObject = boundSql.getParameterObject();
//        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
//        if (parameterMappings.size() > 0 && parameterObject != null) {
//            //TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
////            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
////                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
////
////            } else {
//            Configuration configuration=new Configuration();
//            MetaObject metaObject = configuration.newMetaObject(parameterObject);
//            for (ParameterMapping parameterMapping : parameterMappings) {
//                String propertyName = parameterMapping.getProperty();
//                if (metaObject.hasGetter(propertyName)) {
//                    Object obj = metaObject.getValue(propertyName);
//                    sql = sql.replaceFirst("\\?", getParameterValue(obj));
//                } else if (boundSql.hasAdditionalParameter(propertyName)) {
//                    Object obj = boundSql.getAdditionalParameter(propertyName);
//                    sql = sql.replaceFirst("\\?", getParameterValue(obj));
//                }
//            }
//            //   }
//        }
//        return sql;
//    }
//
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        Map<String, SqlSessionFactory> beansOfType = applicationContext.getBeansOfType(SqlSessionFactory.class);
//        for(Iterator<Map.Entry<String,SqlSessionFactory>> car = beansOfType.entrySet().iterator() ; car.hasNext() ; ){
//            Map.Entry<String, SqlSessionFactory> next = car.next();
//            String key = next.getKey();
//            SqlSessionFactory value = next.getValue();
//            value.getConfiguration().addInterceptor(this);
//            System.out.println("mybatis 拦截器加载成功 ： " + key);
//        }
//    }
//}
