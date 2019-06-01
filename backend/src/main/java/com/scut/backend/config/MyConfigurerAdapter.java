package com.scut.backend.config;

import com.scut.backend.filter.TokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AuthorizationInterceptor()).addPathPatterns("/**");
//        registry.addInterceptor(new CrossOriginInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public FilterRegistrationBean getDemoFilter() {
//        CrossFilter crossFilter = new CrossFilter();
        TokenFilter tokenFilter = new TokenFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(crossFilter);
        registrationBean.setFilter(tokenFilter);
//        registrationBean.setFilter(crossFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("80MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/");
    }

//    @Bean
//    public TestFilter setTestFilter(){
//        return new TestFilter();
//    }
}
