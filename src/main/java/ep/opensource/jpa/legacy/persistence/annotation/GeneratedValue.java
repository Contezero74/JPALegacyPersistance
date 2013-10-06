package ep.opensource.jpa.legacy.persistence.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Provides for the specification of generation strategies for the values of primary keys.
 * In this implementation is used only to mark the field auto-generated fields.
 */
@Target(value=FIELD)
@Retention(value=RUNTIME)
public @interface GeneratedValue {
}
