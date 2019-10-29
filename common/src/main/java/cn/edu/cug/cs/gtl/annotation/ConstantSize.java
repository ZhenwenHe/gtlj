package cn.edu.cug.cs.gtl.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConstantSize {
    long value();
}
