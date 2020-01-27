package com.github.yeecode.matrixauth.client.util;

public class ResultUtil {
    public static Result getSuccessResult() {
        return new Result(true, "成功", null);
    }

    public static Result getSuccessResult(Object data) {
        return new Result(true, "成功", data);
    }

    public static Result getFailResult() {
        return new Result(false, "失败", null);
    }

    public static Result getFailResult(String reason) {
        return new Result(false, reason, null);
    }

    public static Result getFailResult(String reason, Object data) {
        return new Result(false, reason, data);
    }

}
