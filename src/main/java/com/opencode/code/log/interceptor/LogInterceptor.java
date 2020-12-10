package com.opencode.code.log.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opencode.code.log.interceptor.annotation.Loggable;
import com.opencode.code.log.interceptor.callback.LogCallback;
import com.opencode.code.log.interceptor.enums.LogScopeEnum;
import com.opencode.code.log.interceptor.enums.LogTypeEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Aspect
public class LogInterceptor {

    /** 日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);

    private static final String COMMA = ",";

    /** 回调方法 */
    private final LogCallback logCallback;

    @Around(value = "@annotation(loggable)")
    public Object loggableProcessor(ProceedingJoinPoint point, Loggable loggable){
        try{

            String descp = loggable.descp();
            String include = loggable.include();
            LogTypeEnum logTypeEnum = loggable.type();
            boolean console = loggable.loggable();
            boolean scope = loggable.scope().contains(LogScopeEnum.BEFORE);
            boolean db = loggable.db();

            String className = point.getSignature().getDeclaringType().getSimpleName();

            String methodName = point.getSignature().getName();

            Map<String, Object> argsMap = processArgs(point, include);

            String paramsText = JSON.toJSONString(argsMap);


            if(console && (scope)){
                if(LOGGER.isInfoEnabled()){
                    //可以搞下日志级别，日志的模板。。。。
                    LOGGER.info("【{}】 接口入参成功!, 类名称：【{}】, 方法名称:【{}】, 请求参数:【{}】",descp,className, methodName , paramsText);
                }
            }

            Object proceed = point.proceed();

            Class<?>[] paramsCls = getParams(point);

            Method method = point.getTarget().getClass().getMethod(point.getSignature().getName(), paramsCls);

            //获取返回值类型
            Type t = method.getAnnotatedReturnType().getType();

            Object returnValue;

            if(t.getTypeName().equals(String.class.getName())){
                returnValue = JSONObject.parseObject(JSON.toJSONString(proceed),String.class);
            }else{
                returnValue = JSON.toJSONString(proceed);
            }

            String returnText =  JSON.toJSONString(returnValue);

            if(console && (scope)){
                if(LOGGER.isInfoEnabled()){
                    //可以搞下日志级别，日志的模板。。。。
                    LOGGER.info("【{}】 接口参数变化! 类名称：【{}】, 方法名称:【{}】, 处理后的请求参数:【{}】",descp,className, methodName , paramsText);
                    LOGGER.info("【{}】 接口返回结果! 类名称：【{}】,  方法名称:【{}】, 返回结果:【{}】",descp,className, methodName , returnText);
                }
            }

            if(db){
                Map<String,Object> params = new HashMap<>();
                params.put("className",className);
                params.put("methodName",methodName);
                params.put("descp",descp);
                params.put("in",paramsText);
                params.put("out",returnText);
                logCallback.call(params);
            }

            return proceed;
        } catch (Throwable e) {
            LOGGER.error("runtime exception !",e);
        }
        return null;
    }


    /** 处理入参和缓存的key */
    private Map<String,Object> processArgs(ProceedingJoinPoint point,String include) {
        if(StringUtils.isEmpty(include)){
            return new LinkedHashMap<>();
        }
        String[] split;
        //用逗号分割多个args
        if(include.contains(COMMA)){
            split = include.split(COMMA);
        }else{
            split = new String[]{include};
        }

        Map<String,Object> buff = new LinkedHashMap<>();
        String[] parameterNames = ((MethodSignature) point.getSignature()).getParameterNames();
        for(int i = 0 ; i < parameterNames.length ; i++){
            for (String s : split) {
                if (s.equals(parameterNames[i])) {
                    buff.put(s,point.getArgs()[i]);
                }
            }
        }
        return buff;
    }

    /** 获取缓存数据 */
    private Object doGet(ProceedingJoinPoint point,String key) throws NoSuchMethodException {
        String cache = "redisTemplates.get(key)";
        if (!StringUtils.isEmpty(cache)) {
            Class<?>[] paramsCls = getParams(point);
            //获取方法
            Method method = point.getTarget().getClass().getMethod(point.getSignature().getName(), paramsCls);
            //获取返回值类型
            Type t = method.getAnnotatedReturnType().getType();
            String data = "";
            if(StringUtils.isEmpty(data)){
                return null;
            }
            if(t.getTypeName().equals(String.class.getName())){
                return JSONObject.parseObject(JSON.toJSONString(data),String.class);
            }
            return JSONObject.parseObject(data,t);
        }
        return null;
    }

    /** 获取参数 */
    private Class<?>[] getParams(ProceedingJoinPoint point){
        Object[] args = point.getArgs();
        Class<?>[] paramsCls = new Class<?>[args.length];
        for (int i = 0; i < args.length; ++i) {
            paramsCls[i] = args[i].getClass();
        }
        return paramsCls;
    }


    public LogInterceptor(LogCallback logCallback) {
        this.logCallback = logCallback;
    }

}
