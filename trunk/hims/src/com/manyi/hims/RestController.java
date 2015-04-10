package com.manyi.hims;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.leo.jaxrs.fault.LeoFault;

@SuppressWarnings("deprecation")
@Validated
@SessionAttributes({ Global.SESSION_UID_KEY })
public class RestController{
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Response handleIOException(Throwable e,Locale locale,HttpServletRequest request) {
        WebApplicationContext context = (WebApplicationContext) request.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        Response res = new Response();
        
        if(e instanceof TypeMismatchException){
            //在方法参数或者返回值上直接使用formater发生异常时，spring会抛此异常
            TypeMismatchException tme = (TypeMismatchException)e;
            res.setErrorCode(-1);
            res.setMessage(context.getMessage("format.error", new Object[]{tme.getValue()}, locale));
        }else if(e instanceof MethodConstraintViolationException){
            //方法级别使validator发生异常时，spring抛出此异常
            MethodConstraintViolationException tme = (MethodConstraintViolationException)e;
            MethodConstraintViolation<?>  mc = tme.getConstraintViolations().iterator().next();
            res.setErrorCode(-1);
            String template = mc.getMessageTemplate();
            if(StringUtils.isNotBlank(template) && template.startsWith("{") && template.endsWith("}"))
                res.setMessage(context.getMessage(mc.getMessageTemplate().replace("{", "").replace("}", ""), new Object[]{mc.getInvalidValue()}, locale));
            else
                res.setMessage(mc.getMessage());
            
            System.out.println("message template------>" + template);
            
        }else if(e instanceof BindException){
            //Bean级别使用formater和validator发生异常时，spring抛出此异常
            BindException be = (BindException)e;
            BindingResult br = be.getBindingResult();
            List<FieldError> fes  = br.getFieldErrors();
            res.setMessage("");
            for(FieldError error:fes){
                if(error.isBindingFailure()){
                    res.setErrorCode(-1);
                    res.setMessage(res.getMessage() + context.getMessage("format.error", new Object[]{error.getRejectedValue()}, locale)+"\n");
                }else{
                    res.setErrorCode(-1);
                    res.setMessage(res.getMessage() + error.getDefaultMessage()+"\n");
                }
            }
            
            
        }else if(e instanceof HibernateOptimisticLockingFailureException){
            LeoFault lf = new LeoFault(LeoFault.OPTIMISTIC_LOCKING_ERROR, e);
            lf.setLocale(locale);
            res.setErrorCode(lf.getErrorCode());
            res.setMessage(lf.getMessage());
        }else if(e instanceof org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException){
            LeoFault lf = new LeoFault(LeoFault.OPTIMISTIC_LOCKING_ERROR, e);
            lf.setLocale(locale);
            res.setErrorCode(lf.getErrorCode());
            res.setMessage(lf.getMessage());
        }else if(e instanceof LeoFault){
            LeoFault lf = (LeoFault)e;
            lf.setLocale(locale);
            res.setErrorCode(lf.getErrorCode());
            res.setMessage(e.getMessage());
        }else{
        	e.printStackTrace();
            res.setErrorCode(LeoFault.SYSTEM_ERROR);
            res.setMessage(e.getMessage());
        }
        return res;
    }
}
