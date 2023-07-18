package idv.chy.v3;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Component
public class MyJpaBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        // dynamically registry
        MyJpaClassPathBeanDefinitionScanner scanner = new MyJpaClassPathBeanDefinitionScanner(beanDefinitionRegistry);

        // 限制必須 implement Repository interface
        scanner.addIncludeFilter(new AssignableTypeFilter(Repository.class));

        scanner.scan("idv.chy.repository");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
