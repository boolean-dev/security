package com.tao.security.core.result;

import lombok.Data;

/**
 * @ClassName Result
 * @Descriiption 返回结果处理类
 * @Author yanjiantao
 * @Date 2019/7/10 16:49
 **/
@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;
}
