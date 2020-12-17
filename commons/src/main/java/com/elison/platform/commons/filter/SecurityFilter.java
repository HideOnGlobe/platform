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

    private boolean needCheck;
    private String signKey;
    private int signTimeout;
    private Set<String> ignoreSingUri;

    @Value("${security.needCheck}")
    private void setNeedCheck(String needCheck) {
        this.needCheck = Boolean.parseBoolean(needCheck);
    }


    @Value("${security.signKey}")
    private void setSignKey(String signKey) {
        this.signKey = signKey;
    }


    @Value("${security.signTimeout}")
    private void setSignTimeout(String signTimeout) {
        this.signTimeout = Integer.parseInt(signTimeout);
    }

    @Value("${security.ignoreSingUri}")
    private void setIgnoreSingUri(String ignoreSingUri) {
        this.ignoreSingUri = Arrays.stream(ignoreSingUri.split(",")).collect(Collectors.toSet());
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init SecurityFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
            ServletException {
        // 防止流读取一次后就没有了, 所以需要将流继续写出去
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String requestUri = httpRequest.getRequestURI();

        // 校验当前环境是否需要校验
        if (!needCheck) {
            filterChain.doFilter(httpRequest, response);
            return;
        }

        // 校验当前URI是否需要进行校验
        boolean isMatch = false;
        for (String uri : ignoreSingUri) {
            isMatch = requestUri.contains(uri);
            if (isMatch) {
                break;
            }
        }
        log.debug("###请求URL: {}  ###是否需要进行解密: {}", httpRequest.getRequestURI(), isMatch);
        if (isMatch) {
            filterChain.doFilter(httpRequest, response);
            return;
        }

        // 获取签名相关信息
        String sign = httpRequest.getHeader("Sign");
        Long timestamp = Convert.toLong(httpRequest.getHeader("Timestamp"));
        String jsessionId = null;
        try {
            jsessionId =
                    Arrays.stream(httpRequest.getCookies()).filter(cookie -> "JSESSIONID".equals(cookie.getName())).findFirst().get().getValue();
        } catch (Exception e) {

        }

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
        if (jsessionId != null && verifySign(sign, timestamp, jsessionId)) {
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

    private boolean verifySign(String sign, Long timestamp, String token) {
        String paramsJsonStr = timestamp + (ObjectUtil.isNotEmpty(token) ? token : "") + signKey;
        return ObjectUtil.isNotEmpty(paramsJsonStr) && sign.equals(DigestUtils.md5DigestAsHex(paramsJsonStr.getBytes()).toUpperCase());
    }

    @Override
    public void destroy() {
        log.info("destroy SecurityFilter");
    }
}
