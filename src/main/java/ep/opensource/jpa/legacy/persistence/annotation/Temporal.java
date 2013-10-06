package ep.opensource.jpa.legacy.persistence.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import ep.opensource.jpa.legacy.persistence.metadata.TemporalType;



/**
 * This annotation must be specified for persistent fields or properties of type {@link java.util.Date} and
 * {@link java.util.Calendar}.
 * It may only be specified for fields of these types. The {@link Temporal} annotation may be used in conjunction
 * with the {@link Basic} annotation and the {@link Id} annotation.
 */
@Target(value=FIELD)
@Retention(value=RUNTIME)
public @interface Temporal {
    /**
     * The type used in mapping {@link java.util.Date} or {@link java.util.Calendar}.
     */
    TemporalType value();
}
