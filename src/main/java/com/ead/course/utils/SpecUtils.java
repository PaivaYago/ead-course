package com.ead.course.utils;

import org.springframework.data.jpa.domain.Specification;

public class SpecUtils {

    public static <T>Specification<T> safeAnd(Specification<T> base, Specification<T> other){
        try{
            if(other == null) return base;
            return base.and(other);
        }catch (Exception ex){
            return base;
        }
    }
}
