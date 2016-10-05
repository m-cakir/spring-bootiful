package com.bootiful.web.config;

import org.resthub.web.springmvc.router.RouterConfigurationSupport;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RouterConfig extends RouterConfigurationSupport {

    @Override
    public List<String> listRouteFiles() {

        List<String> routeFiles = new ArrayList();
        routeFiles.add("classpath://routes.conf");

        return routeFiles;
    }
}
