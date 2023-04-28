package com.kerbalogy.srpc.common.loadbalance;

import java.util.List;

/**
 * @Author : Artis Yao
 */
public interface LoadBalance {

    Object select(List<Object> list, String key);

}

