package com.syc.springboot_ford.common;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.common
 * @Author: syc
 * @CreateTime: 2023-02-15  17:28
 * @Description: 返回前台的包装类
 * @Version: 1.0
 */
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public static Result success(){
        Result result = new Result<>();
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    public static Result success(String code, String msg){
        Result result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> success(T data){
        Result result = new Result<>(data);
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }
    public static <T> Result<T> success(String code, String msg, T data){
        Result result = new Result<>(data);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result error(String code, String msg){
        Result result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
