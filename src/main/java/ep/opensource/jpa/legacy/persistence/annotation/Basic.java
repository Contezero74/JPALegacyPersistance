package ep.opensource.jpa.legacy.persistence.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The simplest type of mapping to a database column.
 * The {@link Basic} annotation can be applied to a persistent property or instance variable of any of the following
 * types:
 * Java primitive types, wrappers of the primitive types, {@link String}, {@link java.math.BigInteger},
 * {@link java.util.Date}, {@link java.util.Calendar}, <code>enums</code>, and any other type.
 */
@Target(value=FIELD)
@Retention(value=RUNTIME)
public @interface Basic {
    /**
     * (Optional) Defines whether the value of the field or property may be null. This is a hint and is disregarded for
     * primitive types; it may be used in schema generation. If not specified, defaults to <code>true</code>.
     */
    boolean optional() default true;
}
