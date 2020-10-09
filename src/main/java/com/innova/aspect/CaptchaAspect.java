package com.innova.aspect;

import com.innova.exception.CaptchaExpectedException;
import com.innova.exception.ForbiddenException;
import com.innova.model.Attempt;
import com.innova.repository.AttemptRepository;
import com.innova.service.CaptchaValidator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class CaptchaAspect {

    @Autowired
    private CaptchaValidator captchaValidator;

    private static final String CAPTCHA_HEADER_NAME = "captcha-response";

    @Autowired
    AttemptRepository attemptRepository;

    @Around("@annotation(RequiresCaptcha)")
    public Object validateCaptcha(ProceedingJoinPoint joinPoint) throws Throwable{

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();


        Attempt attempt = attemptRepository.findById(request.getRemoteAddr()).orElseThrow(()->
                new UsernameNotFoundException("User Not Ip addr: " + request.getRemoteAddr()));
        if(attempt.getAttemptCounter() >= 3){
            System.out.println("Hey there!");
            String captchaResponse = request.getHeader(CAPTCHA_HEADER_NAME);
            boolean isValidCaptcha = captchaValidator.validateCaptcha(captchaResponse);
            if(!isValidCaptcha){
                try {
                    throw new Exception("Captcha was expected");
                }
                catch (Exception ex){
                    throw new CaptchaExpectedException(ex.getMessage());
                }
            }
            else{
                return joinPoint.proceed();
            }
        }
        return joinPoint.proceed();
    }
}