package ep.opensource.jpa.legacy.persistence.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is used to specify a secondary table for the annotated entity class.
 * Specifying one or more secondary tables indicates that the data for the entity class is stored across multiple tables.
 * 
 * If no SecondaryTable annotation is specified, it is assumed that all persistent fields or properties of the entity
 * are mapped to the primary table. If no primary key join columns are specified, the join columns are assumed to
 * reference the primary key columns of the primary table, and have the same names and types as the referenced primary
 * key columns of the primary table.
 */
@Documented
@Target(value = TYPE)
@Retention(value = RUNTIME)
public @interface SecondaryTable {
    /**
     * The name of the table.
     */
    String name();
    
    /**
     * (Optional) The columns that are used to join with the primary table.
     * Defaults to the column(s) of the same name(s) as the primary key column(s) in the primary table.
     */
    PrimaryKeyJoinColumn[] pkJoinColumns() default {};
    
    /**
     * (Optional) The schema of the table.
     * Defaults to the default schema for user.
     */
    String schema() default "";
}
