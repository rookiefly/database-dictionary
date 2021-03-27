package com.rookiefly.open.dictionary.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 数据校验处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler({BindException.class, ConstraintViolationException.class})
    public ModelAndView validatorExceptionHandler(Exception e) {
        String msg = e instanceof BindException ? ((BindException) e).getBindingResult().toString()
                : ((ConstraintViolationException) e).getConstraintViolations().toString();
        ModelAndView m = new ModelAndView();
        log.error("error:", e);
        m.addObject("exception", msg);
        m.setViewName("error/500");
        return m;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView processException(Exception e) {
        ModelAndView m = new ModelAndView();
        log.error("error:", e);
        m.addObject("exception", e.getMessage());
        m.setViewName("error/500");
        return m;
    }
}