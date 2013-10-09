package ep.opensource.jpa.legacy.persistence.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies that the class is an entity. This annotation is applied to the entity class.
 */
@Documented
@Target(value = TYPE)
@Retention(value = RUNTIME)
public @interface Entity {
    /**
     * (Optional) The entity name.
     * Defaults to the unqualified name of the entity class. This name is used to refer to the entity in queries.
     */
    String name() default "";
}
