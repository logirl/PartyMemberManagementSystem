package cc.logirl.pmms.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xinxi on 2016/10/24.
 * <p>
 * 放在业务层的方法上，标注了该注解的方法即拥有了事务支持
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transactional {
}
