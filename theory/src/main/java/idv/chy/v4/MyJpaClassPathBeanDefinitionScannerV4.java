package idv.chy.v4;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

/**
 *  自定義 Scanner
 *  透過此 Scanner 進行動態 registry
 */
public class MyJpaClassPathBeanDefinitionScannerV4 extends ClassPathBeanDefinitionScanner {
    public MyJpaClassPathBeanDefinitionScannerV4(BeanDefinitionRegistry registry) {
        super(registry);
    }


    // 如果是 interface 則視為有效 Component, 必須 implement Repository
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        return metadata.isInterface();
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);

        // beanDefinition -> repositories("idv.chy.repository")
        for (BeanDefinitionHolder definitionHolder : beanDefinitionHolders) {

            ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition) definitionHolder.getBeanDefinition();

            String beanClass = beanDefinition.getBeanClassName();

            // 設定 constructor 值
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanClass);

            // 賦值
            beanDefinition.setBeanClass(MyJpaFactoryBean.class);
        }

        return beanDefinitionHolders;
    }
}
