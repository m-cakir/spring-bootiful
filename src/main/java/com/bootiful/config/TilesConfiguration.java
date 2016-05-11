package com.bootiful.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.tiles2.dialect.TilesDialect;
import org.thymeleaf.extras.tiles2.spring4.web.configurer.ThymeleafTilesConfigurer;
import org.thymeleaf.extras.tiles2.spring4.web.view.ThymeleafTilesView;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class TilesConfiguration {
    @Bean
    public ThymeleafTilesConfigurer tilesConfigurer() {
        final ThymeleafTilesConfigurer configurer = new ThymeleafTilesConfigurer();
        configurer.setDefinitions(new String[] { "classpath:templates/tiles.xml" });
        return configurer;
    }
    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        final ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setViewClass(ThymeleafTilesView.class);
        resolver.setViewNames(new String[] {"*"});
        resolver.setCharacterEncoding("UTF-8");
        resolver.setOrder(Ordered.LOWEST_PRECEDENCE);
        resolver.setCache(false);
        return resolver;
    }
    @Bean 
    public TemplateResolver templateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);
        return resolver;
    }
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        Set<IDialect> dialects = new HashSet<>();
        dialects.add(new TilesDialect());
        templateEngine.setAdditionalDialects(dialects);
        return templateEngine;
    }
}
