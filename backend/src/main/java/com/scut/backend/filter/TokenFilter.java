package com.scut.backend.filter;

import com.alibaba.fastjson.JSONObject;
import com.scut.backend.config.AccessManager;
import com.scut.backend.model.Result;
import com.scut.backend.model.TokenSession;
import com.scut.backend.uits.HttpUtil;
import com.scut.backend.uits.RedisUtil;
import com.scut.backend.uits.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Access Token验证过滤器
 */
//@WebFilter(filterName = "tokenFilter", urlPatterns = "/**")
public class TokenFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Expose-Headers", "*");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "0");
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,uid, token,Access-Token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("XDomainRequestAllowed","1");
        String url = request.getRequestURI();
        String accessToken = request.getHeader("Access-Token");
        if(StringUtils.isEmpty(accessToken)){
            // 区分大小写
            accessToken = request.getParameter("Access-Token");
        }
        TokenSession tokenSession = null;

        // 判断当前请求是否为OPTIONS（在跨域请求中，询问服务器是否运行访问的 第一步）
        if(request.getMethod().equals("OPTIONS")){
            filterChain.doFilter(request, response);
            return;
        }

        // 不需要token和role验证的url
        if (AccessManager.matchUncheckedURIList(url)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 需要token验证的url
        if (true) {
            // 检测token是否存在
            //如果不存在
            if (StringUtils.isEmpty(accessToken)) {
                Result result = ResultUtil.returnBadResult("禁止访问,需要Access-Token");
                JSONObject responseBody = JSONObject.parseObject(JSONObject.toJSONString(result));
                HttpUtil.setResponse(response,responseBody);
                return;
            }
            //如果存在就找出来
            tokenSession = RedisUtil.getTokenSession(accessToken);
            //判断是否过期
            //没过期
            if (tokenSession != null) {
                request.setAttribute("tokenSession",tokenSession);
                // 更新token生命周期
                RedisUtil.updateToken(accessToken);
                filterChain.doFilter(request, response);

            }else {
                //token无效或者过期
                Result result = ResultUtil.returnBadResult("无效的Access-Token");
                JSONObject responseBody = JSONObject.parseObject(JSONObject.toJSONString(result));
                HttpUtil.setResponse(response,  responseBody);
            }

        }
    }

}
