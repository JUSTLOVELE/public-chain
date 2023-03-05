package com.bc.server.transaction;

import java.io.Serializable;

public class Transaction implements Serializable {

    private static final long serialVersionUID = -2718643841231352368L;

    private String pk;

    private String signature;

    private Object data;

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "pk='" + pk + '\'' +
                ", signature='" + signature + '\'' +
                ", data=" + data +
                '}';
    }
}
