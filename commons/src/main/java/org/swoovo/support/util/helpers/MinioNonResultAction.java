package org.swoovo.support.util.helpers;

@FunctionalInterface
public interface MinioNonResultAction<T> {
    void execute(T arg) throws Exception;
}
