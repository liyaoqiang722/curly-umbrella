package com.wj.spc.demo_1203.viewModel;

/**
 * Created by chenyixiong on 2018/6/13.
 */
public class SuccessResponse<T> {
    private Result result;
    private T data;

    public SuccessResponse() {
        result = new Result();
        result.setCode(0);
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
