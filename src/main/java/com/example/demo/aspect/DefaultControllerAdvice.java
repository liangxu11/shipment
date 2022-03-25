package com.example.demo.aspect;

import com.example.demo.dto.Result;
import com.example.demo.exception.OpenApiRespEnum;
import com.example.demo.exception.OpenapiBizException;
import com.example.demo.exception.ParamInvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @param * @Param null:
 * @author liangxu
 * @description Global exception handling
 * @date 2022-03-16 13:00:03
 * @return * @return: null
 **/
@Slf4j
@RestControllerAdvice
public class DefaultControllerAdvice implements ResponseBodyAdvice {


    private static final String EXCEPTION_ATTR = "exception";
    private static final String RESPONSE_CODE_ATTR = "code";

    // Process all requests
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        return body;
    }


    /**
     * Handle exceptions and write the request context for easy logging
     *
     * @param code
     * @param e
     */
    private void handleException(int code, Exception e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest servletRequest = attributes.getRequest();

        servletRequest.setAttribute(RESPONSE_CODE_ATTR, code);
        servletRequest.setAttribute(EXCEPTION_ATTR, e);
    }

    /**
     * Parameter invalid exception
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.info("", e);
        handleException(3, e);
        return new Result<>(String.valueOf(OpenApiRespEnum.ReqParamNotVaild.getCode()),
                OpenApiRespEnum.ReqParamNotVaild.getMessage() + e.getMessage());
    }


    /**
     * Basic information query by parameter does not exist or is not enabled
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ParamInvalidException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> paramInValidExceptionHandler(ParamInvalidException e) {
        handleException(OpenApiRespEnum.ReqParamEntityNotExist.getCode(), e);
        return new Result<>(String.valueOf(OpenApiRespEnum.ReqParamEntityNotExist.getCode()), e.getMessage());
    }


    /**
     * Business exceptions
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = OpenapiBizException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> openapiBizException(OpenapiBizException e) {
        log.error(String.valueOf(e.getCode()), e);
        handleException(e.getCode(), e);


        return new Result<>(String.valueOf(e.getCode()), e.getMessage());
    }


    /**
     * Unknown abnormal
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> exceptionHandler(Exception e) {
        log.error("", e);
        handleException(5, e);

        return new Result<>(String.valueOf(5),
                "Unknown abnormal: " + e.getMessage());
    }

}
