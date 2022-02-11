package edu.xj.medicalcheckupweb.util;

/**
 * @author zyhsna
 */
public class JSONData {

    /**
     * <p>stateCode:状态码</p>
     * <p>data:数据</p>
     * <p>message:返回给前端消息</p>
     */
    private int stateCode;
    private Object data;
    private String message;

    public JSONData() {

    }

    public JSONData(int stateCode, Object data) {
        this.stateCode = stateCode;
        this.data = data;
    }

    public JSONData(int stateCode, Object data, String message) {
        this.stateCode = stateCode;
        this.data = data;
        this.message = message;
    }

    public JSONData(int stateCode, String message) {
        this.stateCode = stateCode;
        this.message = message;
    }

    public JSONData(String message) {
        this.message = message;
    }

    public JSONData(int stateCode) {
        this.stateCode = stateCode;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static JSONData buildSuccess(Object data) {
        return new JSONData(0, data);
    }

    public static JSONData buildSuccess() {
        return new JSONData(0);
    }

    public static JSONData bulidSuccess(String message) {
        return new JSONData(message);
    }


    public static JSONData buildError(int stateCode, Object data) {
        return new JSONData(stateCode, data);
    }

    public static JSONData buildError(int stateCode, String message) {
        return new JSONData(stateCode, message);
    }


    public static JSONData build(int stateCode, Object data) {
        return new JSONData(stateCode, data);
    }

}
