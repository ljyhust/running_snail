package com.ljyhust.dto.common;

/**
 * 接口返回公共类
 * Created by Administrator on 2018/5/5.
 */
public class PublicResponseDto {

    private Integer flag = PublicCommonConstants.API_CODE_SUCCESS;

    private String error;


    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
