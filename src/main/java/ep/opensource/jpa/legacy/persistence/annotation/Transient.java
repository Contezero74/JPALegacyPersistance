package ep.opensource.jpa.legacy.persistence.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies that the property or field is not persistent.
 * It is used to annotate a property of an entity class, or mapped superclass.
 */
@Target(value=FIELD)
@Retention(value=RUNTIME)
public @interface Transient {
}
