package org.swoovo.support.util.helpers;

@FunctionalInterface
public interface MinioAction<T, R> {
    R execute(T t) throws Exception;
}
