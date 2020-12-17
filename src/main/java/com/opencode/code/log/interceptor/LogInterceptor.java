package com.opencode.code.log.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opencode.code.log.interceptor.annotation.Loggable;
import com.opencode.code.log.interceptor.callback.LogCallback;
import com.opencode.code.log.interceptor.enums.LogSourceEnum;
import com.opencode.code.log.interceptor.result.LogResult;
import com.opencode.code.velocity.VelocityUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Aspect
public class LogInterceptor {

    private static final String COMMA = ",";
    private static final String SP_POINT = "\\.";
    private static final String POINT = ".";

    /** 日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);

    /** 回调方法 */
    private final LogCallback logCallback;

    @Around(value = "@annotation(loggable)")
    public Object loggableProcessor(ProceedingJoinPoint point, Loggable loggable){

        String title = loggable.title();
        int type = loggable.type();
        String descp = loggable.descp();
        String include = loggable.include();
        LogSourceEnum logSourceEnum = loggable.source();
        boolean db = loggable.db();

        try{

            String className = point.getSignature().getDeclaringType().getSimpleName();

            String methodName = point.getSignature().getName();

            Map<String, Object> argsMap = new HashMap<>(processArgs(point, include));

            String userName = "小明明明";

            argsMap.put("SYS_USER",userName);
            argsMap.put("SYS_TIME", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            String paramsText = JSON.toJSONString(argsMap);

            Object proceed = null;

            try{
                proceed = point.proceed();
            }catch(Exception e){
                LOGGER.error("business process error !");
            }


            Signature sig = point.getSignature();
            MethodSignature msig = (MethodSignature) sig;
            Object target = point.getTarget();
            Method method = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

            //获取返回值类型
            Type t = method.getAnnotatedReturnType().getType();

            Object returnValue = null;

            try{
                if(t.getTypeName().equals(String.class.getName())){
                    returnValue = JSONObject.parseObject(JSON.toJSONString(proceed),String.class);
                }else{
                    returnValue = JSON.toJSONString(proceed);
                }
            }catch(Exception e){
                LOGGER.error("business process error returnValue is null !");
            }


            String returnText =  returnValue == null ? null : JSON.toJSONString(returnValue);

            if(db){
                LogResult logResult = new LogResult();
                logResult.setClassName(className);
                logResult.setMethodName(methodName);

                String descpTpl = VelocityUtils.generator(descp, argsMap);
                logResult.setDescp(descpTpl);
                logResult.setLogSource(logSourceEnum.getValue());
                logResult.setParams(paramsText);
                logResult.setResult(returnText);
                logResult.setSysUser(userName);
                logResult.setTitle(title);
                logResult.setType(type);
                logCallback.call(logResult);
            }

            return proceed;
        } catch (Throwable e) {
            LOGGER.error("runtime exception !",e);
        }
        return null;
    }

    /** 处理入参和缓存的key */
    private Map<String,Object> processArgs(ProceedingJoinPoint point,String include) throws NoSuchFieldException, IllegalAccessException {
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
        Object[] args = point.getArgs();
        for(int i = 0 ; i < parameterNames.length ; i++){
            Object arg = args[i];
            for (String s : split) {
                if(s.contains(POINT)){
                    String[] psp = s.split(SP_POINT);
                    String objName = psp[0];
                    String objArgsName = psp[1];
                    if (objName.equals(parameterNames[i])) {
                        if(arg instanceof Map){
                            buff.put(objArgsName,((Map<?, ?>) arg).get(objArgsName));
                        }else{
                            Field declaredField = ((MethodSignature) point.getSignature()).getParameterTypes()[0].getDeclaredField(objArgsName);
                            declaredField.setAccessible(true);
                            Object o = declaredField.get(arg);
                            buff.put(objArgsName,o);
                        }

                    }
                }else{
                    if (s.equals(parameterNames[i])) {
                        if(arg instanceof Map){
                            buff.put(s,arg);
                        }else{
                            buff.put(s,arg);
                        }

                    }
                }

            }
        }
        return buff;
    }

    public LogInterceptor(LogCallback logCallback) {
        this.logCallback = logCallback;
    }

}
