package com.example.rest.global.aspact;

import com.example.rest.global.entity.RsData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ResponseAspect {

    //final 붙이고 @RequiredArgsConstructor를 설정해 스프링부트가 자동으로
    //HttpServletResponse에 맞는 값을 넣어준다.
//    private final HttpServletRequest request;
    private final HttpServletResponse response;

    @Around("""
            (
                within(
                        @org.springframework.web.bind.annotation.RestController *
                        )
            )
            &&
            (
                @annotation(org.springframework.web.bind.annotation.GetMapping)
                ||
                @annotation(org.springframework.web.bind.annotation.PostMapping)
                ||
                @annotation(org.springframework.web.bind.annotation.PutMapping)
                ||
                @annotation(org.springframework.web.bind.annotation.DeleteMapping)
            )
        """
    )
    public Object responseAspect(ProceedingJoinPoint jointPoint) throws Throwable {
//        System.out.println("pre");

        Object rst = jointPoint.proceed();

        if(rst instanceof RsData<?> rsData) {
//            String msg = rsData.getMsg();
//            System.out.println("msg: " + msg);

            //응답 코드를 설정
            int statusCode = rsData.getStatusCode();
            response.setStatus(statusCode);
        }


        System.out.println("post");

        return rst;
    }
}
