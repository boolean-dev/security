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

    public static Result successMsg() {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("请求成功");
        return result;
    }

    public static Result successMsg(Object data) {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("请求成功");
        result.setData(data);
        return result;
    }

    public static Result unOauth() {
        Result result = new Result();
        result.setCode(401);
        result.setMsg("未授权");
        return result;
    }

    public static Result errorMsg() {
        Result result = new Result();
        result.setCode(500);
        result.setMsg("内部错误");
        return result;
    }


}
