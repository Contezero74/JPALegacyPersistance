package ep.opensource.jpa.legacy.persistence.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import ep.opensource.jpa.legacy.persistence.metadata.InheritanceType;


/**
 * Defines the inheritance strategy to be used for an entity class hierarchy.
 * It is specified on the entity class that is the root of the entity class hierarchy.
 * If the Inheritance annotation is not specified or if no inheritance type is specified for an entity class hierarchy,
 * the {@link InheritanceType#SINGLE_TABLE} mapping strategy is used.
 */
@Target(value = TYPE)
@Retention(value = RUNTIME)
public @interface Inheritance {
    /**
     * The strategy to be used for the entity inheritance hierarchy.
     */
    InheritanceType strategy() default InheritanceType.SINGLE_TABLE;    
}
