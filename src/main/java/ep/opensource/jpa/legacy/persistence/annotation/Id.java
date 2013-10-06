package ep.opensource.jpa.legacy.persistence.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies the primary key of an entity.
 * The mapped column for the primary key of the entity is assumed to be the primary key of the primary table.
 * If no {@link Column} annotation is specified, the primary key column name is assumed to be the name of the primary
 * key field.
 */
@Target(value=FIELD)
@Retention(value=RUNTIME)
public @interface Id {
}
