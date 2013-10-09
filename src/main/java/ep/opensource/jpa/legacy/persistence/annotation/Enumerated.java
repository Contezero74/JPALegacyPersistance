package ep.opensource.jpa.legacy.persistence.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import ep.opensource.jpa.legacy.persistence.metadata.EnumType;

/**
 * Specifies that a persistent property or field should be persisted as a enumerated type
 * The Enumerated annotation may be used in conjunction with the {@link Basic} annotation.
 * If the enumerated type is not specified or the Enumerated annotation is not used, the {@link EnumType} value is
 * assumed to be {@link EnumType#ORDINAL}.
 */
@Target(value = FIELD)
@Retention(value = RUNTIME)
public @interface Enumerated {
    /**
     * (Optional) The type used in mapping an enum type. If not specified, defaults to {@link EnumType#ORDINAL}.
     */
    EnumType value() default EnumType.ORDINAL;
}
