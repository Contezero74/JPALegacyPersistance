package ep.opensource.jpa.legacy.persistence.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies the primary table for the annotated entity.
 * If no {@link Table} annotation is specified for an entity class, the default values apply.
 */
@Target(value = TYPE)
@Retention(value = RUNTIME)
public @interface Table {
    /**
     * (Optional) The name of the table.
     * Defaults to the entity name.
     */
    String name() default "";
    
    /**
     * (Optional) The schema of the table.
     * Defaults to the default schema for user.
     */
    String schema() default "";
}
