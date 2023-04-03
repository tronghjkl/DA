package com.globits.da.domain.baseObject;

import com.globits.da.dto.EmployeeDTO;

public class ResponObject<T> {
    private String status;
    private String messager;
    private T data;
    private Boolean isValid;

    private int code;

    public ResponObject(T data) {
        this.data = data;
    }

    public ResponObject(EmployeeDTO result) {
    }

    public ResponObject(String messager) {
        this.messager = messager;
    }

    public ResponObject(String messager, String status, int code) {
        this.messager = messager;
        this.status = status;
        this.code = code;
    }

    public ResponObject(String messager, String status, int code, T data) {
        this.messager = messager;
        this.status = status;
        this.code = code;
        this.data = data;
    }

    public ResponObject(Boolean isValid, String messager) {
        this.isValid = isValid;
        this.messager = messager;
    }

    public ResponObject(String messager, T data) {
        this.messager = messager;
        this.data = data;
        this.isValid = true;
    }

    public ResponObject(String status, String messager, T data) {
        this.status = status;
        this.messager = messager;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessager() {
        return messager;
    }

    public void setMessager(String messager) {
        this.messager = messager;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
