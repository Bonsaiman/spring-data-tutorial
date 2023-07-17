package idv.chy.v1;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyJpaRepository implements InvocationHandler {
    EntityManager entityManager;
    Class<?> clazz;

    public MyJpaRepository(EntityManager entityManager, Class<?> clazz) {
        this.entityManager = entityManager;
        this.clazz = clazz;
    }

    // method --> findById
    // args 目前方法的參數 --> id = 7L
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 動態代理統一 implementation class
        MyJpaProxy myJpaProxy = new MyJpaProxy(entityManager, clazz);

        Method jpaMethod = myJpaProxy.getClass().getMethod(
                method.getName(), method.getParameterTypes());

        return jpaMethod.invoke(myJpaProxy, args);
    }
}
