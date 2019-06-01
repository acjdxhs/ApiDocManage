package com.scut.backend.config;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Arrays;
import java.util.List;

public class AccessManager {
    private static PathMatcher matcher = new AntPathMatcher();

    // 无需验证列表
    private static List<String> uncheckedURLList = Arrays.asList(
            "/js/**",
            "/css/**",
            "/statistics/**",
            "/user/signUp",
            "/user/login",

            //访问静态资源
            "/images/upload/**",
            "/good/getPicture"
    );

    /**
     * 判断url是否匹配uriList中的uri的正则表达式
     */
    public static boolean matchURIList(String url, List<String> uriList) {
        for (String pattern : uriList) {
            if (matcher.match(pattern, url)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果该url不需验证就返回true, 否则返回false
     */
    public static boolean matchUncheckedURIList(String uri) {
        return matchURIList(uri, uncheckedURLList);
    }

    public static List<String> getUncheckedURIList() {
        return uncheckedURLList;
    }


}
