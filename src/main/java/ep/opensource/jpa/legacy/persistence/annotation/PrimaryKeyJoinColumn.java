package ep.opensource.jpa.legacy.persistence.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation specifies a primary key column that is used as a foreign key to join to another table.
 */
@Documented
@Target(value=TYPE)
@Retention(value=RUNTIME)
public @interface PrimaryKeyJoinColumn {
    /**
     * (Optional) The name of the primary key column of the current table.
     * Defaults to the same name as the primary key column of the primary table.
     */
    String name() default "";
    
    /**
     * (Optional) The name of the primary key column of the table being joined to.
     * Defaults to the same name used in the {@link PrimaryKeyJoinColumn#name()} property.
     */
    String referencedColumnName() default "";
}
