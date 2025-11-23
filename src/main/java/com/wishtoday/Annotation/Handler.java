package com.wishtoday.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
* params
* first: The payload instance
* second: ServerContext or Client Context
* */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface Handler {
}
