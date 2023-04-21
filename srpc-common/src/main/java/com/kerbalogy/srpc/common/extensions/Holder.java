package com.kerbalogy.srpc.common.extensions;

/**
 * TODO: Read
 * @Author : Artis Yao
 */
public class Holder<T> {

    private volatile T value;

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
