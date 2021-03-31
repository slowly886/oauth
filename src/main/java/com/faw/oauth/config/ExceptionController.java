package com.faw.oauth.config;

import com.faw.oauth.domian.ResultVO;
import com.faw.oauth.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: yanke
 * @Date: 2018/10/10 0010 09:44
 * @Description:
 */
@Slf4j
@RestControllerAdvice
public class ExceptionController {

    // 捕捉ConstraintViolationException,--------hibernate validator数据校验   ,不知道为什么突然失效了
   /* @ExceptionHandler(ConstraintViolationException.class)
    public R constraintViolationException(ConstraintViolationException e) {
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                return new R(ResponseCode.NOT_ACCEPTABLE_406, item.getMessage());
            }
        }
        return new R(ResponseCode.NOT_ACCEPTABLE_406, e.getMessage());
    }*/

    // 捕捉 hibernate validator数据校验
  /*  @ExceptionHandler(BindException.class)
    public R bindException(BindException e) {
        String errorMessage = null;
        if (e instanceof BindException) {
            BindingResult bindingResult = e.getBindingResult();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError allError : allErrors) {
                errorMessage = allError.getDefaultMessage();
            }
        }
        return new R(ResponseCode.NOT_ACCEPTABLE_406, errorMessage);
    }*/

    // token校验
   /* @ExceptionHandler(InvocationTargetException.class)
    public R invalidTokenException(InvocationTargetException e) {
        log.info("token错误");
        return new R(ResponseCode.INTERNAL_SERVER_ERROR_10001, e.getMessage());
    }*/

    // 捕捉 hibernate validator数据校验
    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    public R businessException(MethodArgumentNotValidException e) {
        String errorMessage = null;
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        for (ObjectError allError : allErrors) {
            errorMessage = allError.getDefaultMessage();
        }
        return new R(ResponseCode.INTERNAL_SERVER_ERROR_10001, errorMessage);
    }*/

    // 捕捉其他所有异常
    @ExceptionHandler(AuthException.class)
    public ResultVO authException(AuthException exception, HttpServletRequest request, Throwable ex) {
        log.error(exception.getMessage());
        exception.printStackTrace();
        log.error(ex.getMessage());
        ex.printStackTrace();
        return new ResultVO(ResponseCode.NOT_ACCEPTABLE_400002.getCode(), ResponseCode.NOT_ACCEPTABLE_400002.getDesc());
    }

    // 捕捉其他所有异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResultVO globalException(Exception exception, HttpServletRequest request, Throwable ex) {
        log.error(exception.getMessage());
        exception.printStackTrace();
        log.error(ex.getMessage());
        ex.printStackTrace();
        return new ResultVO(ResponseCode.INTERNAL_SERVER_ERROR_10001.getCode(), ResponseCode.INTERNAL_SERVER_ERROR_10001.getDesc());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
