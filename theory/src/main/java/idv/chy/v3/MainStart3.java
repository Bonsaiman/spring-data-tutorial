package idv.chy.v3;

import idv.chy.config.SpringDataJPAConfig;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

public class MainStart3 {

    public static void main(String[] args) {
        // spring  application context  spring container --> ioc 加載過程: 建立所有的 Bean, 包刮 Repository 的 Bean
        AnnotationConfigApplicationContext ioc =
                new AnnotationConfigApplicationContext(SpringDataJPAConfig.class);

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) ioc.getBeanFactory();
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);
        scanner.scan("idv.chy");
        for (String beanDefinitionName : ioc.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
    }
}
