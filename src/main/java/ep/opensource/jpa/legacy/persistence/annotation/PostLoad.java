package ep.opensource.jpa.legacy.persistence.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies a callback method for the corresponding lifecycle event.
 * This annotation may be applied to methods of an entity class, a mapped superclass, or a callback listener class.
 */
@Target(value = METHOD)
@Retention(value = RUNTIME)
public @interface PostLoad {
}
