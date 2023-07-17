package idv.chy.v3;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;

/**
 *  自定義 Scanner
 *  透過此 Scanner 進行動態 registry
 */
public class MyJpaClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {
    public MyJpaClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }


    // 如果是 interface 則視為有效 Component, 必須 implement Repository
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        return metadata.isInterface();
    }
}
