package com.github.spring.cloud.context.support.attachment;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 附件转换成上下文切面
 *
 * @author liuyan
 * @date 2018-10-15
 */
@Aspect
public class OlaAttachment2ContextAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    private void olaAttachmentRestControllerAnnotated() {
    }

    @Around("olaAttachmentRestControllerAnnotated()")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = getCurrentRequest();
        try {
            if (request != null) {
                addOlaAttachment2Context(request);
            }
            return joinPoint.proceed();
        } finally {
            OlaAttachmentContext.clear();
        }
    }

    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return requestAttributes.getRequest();
        }
        return null;
    }

    private void addOlaAttachment2Context(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = request.getHeader(name);
                if (name.startsWith(Constants.ATTACHMENT_KEY_PREFIX)) {
                    OlaAttachmentContext.addAttachment(name, value);
                }
            }
        }
    }
}
