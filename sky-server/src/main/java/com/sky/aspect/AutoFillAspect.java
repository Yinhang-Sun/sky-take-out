package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * Customize aspects to implement automatic filling processing logic of public fields
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * Pointcut
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {}

    /**
     * Pre-notification, assign values to public fields in the notification
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("Start auto-filling public fields...");

        //Get the database operation type on the currently intercepted method
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//method signature object
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//get the annotation object on the method
        OperationType operationType = autoFill.value();//get the database operation type

        //Get the parameters of the currently intercepted method--the entity object
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0) {
            return;
        }

        Object entity = args[0];

        //Prepare data for assignment
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        //According to the current operation type, assign values to the corresponding properties through reflection
        if(operationType == OperationType.INSERT) {
            //assign for 4 public fields
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //Assigning values to object properties through reflection
                setCreateTime.invoke(entity, now);
                setCreateUser.invoke(entity, currentId);
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(operationType == OperationType.UPDATE) {
            //assign for 2 public fields
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //Assigning values to object properties through reflection
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
