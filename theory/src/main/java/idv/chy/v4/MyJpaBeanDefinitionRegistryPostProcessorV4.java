package idv.chy.v4;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *  動態註冊 Repository post processor
 */
@Component
public class MyJpaBeanDefinitionRegistryPostProcessorV4 implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        // dynamically registry
        MyJpaClassPathBeanDefinitionScannerV4 scanner = new MyJpaClassPathBeanDefinitionScannerV4(beanDefinitionRegistry);

        // 限制必須 implement Repository interface
        scanner.addIncludeFilter(new AssignableTypeFilter(Repository.class));

        scanner.scan("idv.chy.repository");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
