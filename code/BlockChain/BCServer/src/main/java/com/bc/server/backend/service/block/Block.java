package com.bc.server.backend.service.block;

import com.bc.server.utils.ObjectToByteUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class Block implements Serializable {

    private static final long serialVersionUID = -2718643841231352368L;

    private int height;

    private String preBlockHash;

    private byte[] Data;

    private Date timestamp;

    private String hash;

    private long nonce;

    public Block() {

    }

    public Block(int height, String preBlockHash, byte[] data, Date timestamp, String hash, long nonce) {
        this.height = height;
        this.preBlockHash = preBlockHash;
        Data = data;
        this.timestamp = timestamp;
        this.hash = hash;
        this.nonce = nonce;
    }

    public String getPreBlockHash() {
        return preBlockHash;
    }

    public void setPreBlockHash(String preBlockHash) {
        this.preBlockHash = preBlockHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public byte[] getData() {
        return Data;
    }

    public void setData(byte[] data) {
        Data = data;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        return "Block{" +
                "height=" + height +
                ", preBlockHash='" + preBlockHash + '\'' +
                ", Data=" + ObjectToByteUtils.byteToObject(getData()) +
                ", timestamp=" + timestamp +
                ", hash='" + hash + '\'' +
                ", nonce=" + nonce +
                '}';
    }
}
