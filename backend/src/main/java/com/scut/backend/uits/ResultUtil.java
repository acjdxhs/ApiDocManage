package com.scut.backend.uits;

import com.scut.backend.model.Result;

public class ResultUtil {

    public static Result returnGoodResult() {
        return new Result(0, "ok", null);
    }

    public static Result returnGoodResult(Object data) {
        return new Result(0, "ok", data);
    }

    public static Result returnGoodResult(String msg, Object data) {
        return new Result(0, msg, data);
    }

    public static Result returnBadResult(int code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result returnBadResult(int code, String msg, Object data) {
        return new Result(code, msg, data);
    }

    public static Result returnBadResult(String msg) {
        return new Result(-1, msg, null);
    }
}
