package com.elison.platform.commons.filter;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.elison.platform.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.filter
 * @Description: 签名验证
 * @Author: elison
 * @CreateDate: 2020/9/22 20:59
 * @UpdateDate: 2020/9/22 20:59
 **/
@Slf4j
@Component
public class SecurityFilter implements Filter {

    @Value("security.signTimeout")
    private int signTimeout;
    @Value("security.ignoreSingUri")
    private String ignoreSingUri;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init SecurityFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
            ServletException {
        // 防止流读取一次后就没有了, 所以需要将流继续写出去
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        Set<String> uriSet = Arrays.stream(ignoreSingUri.split(",")).collect(Collectors.toSet());
        String requestUri = httpRequest.getRequestURI();
        boolean isMatch = false;
        for (String uri : uriSet) {
            isMatch = requestUri.contains(uri);
            if (isMatch) {
                break;
            }
        }
        log.debug("当前请求的URI是==>{},isMatch==>{}", httpRequest.getRequestURI(), isMatch);
        if (isMatch) {
            filterChain.doFilter(httpRequest, response);
            return;
        }

        String sign = httpRequest.getHeader("Sign");
        Long timestamp = Convert.toLong(httpRequest.getHeader("Timestamp"));
        String jsessionId =
                Arrays.stream(httpRequest.getCookies()).filter(cookie -> "jseesionId".equals(cookie.getName())).findFirst().get().getValue();

        if (StrUtil.isEmpty(sign)) {
            returnFail("签名不允许为空", response);
            return;
        }

        if (timestamp == null) {
            returnFail("时间戳不允许为空", response);
            return;
        }

        //重放时间限制（单位分）
        Long difference = DateUtil.between(DateUtil.date(), DateUtil.date(timestamp * 1000), DateUnit.MINUTE);
        if (difference > signTimeout) {
            returnFail("已过期的签名", response);
            log.info("前端时间戳：{},服务端时间戳：{}", DateUtil.date(timestamp * 1000), DateUtil.date());
            return;
        }
        if (verifySign(sign, timestamp, jsessionId)) {
            filterChain.doFilter(httpRequest, response);
        } else {
            returnFail("签名验证不通过", response);
        }
    }

    private void returnFail(String msg, ServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String result = JSONUtil.toJsonStr(Result.fail(msg));
        out.println(result);
        out.flush();
        out.close();
    }

    private static boolean verifySign(String sign, Long timestamp, String token) {
        String paramsJsonStr = "Timestamp" + timestamp + "Token" + token;
        return ObjectUtil.isNotEmpty(paramsJsonStr) && sign.equals(DigestUtils.md5DigestAsHex(paramsJsonStr.getBytes()).toUpperCase());
    }

    @Override
    public void destroy() {
        log.info("destroy SecurityFilter");
    }
}
