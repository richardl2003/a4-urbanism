package ca.mcmaster.cas.se2aa4.a2.island.Specification;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotations for pre features; required actions before the implementation of island
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreFeatures {

}
