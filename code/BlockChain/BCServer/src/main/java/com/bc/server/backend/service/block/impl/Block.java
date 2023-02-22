package com.bc.server.backend.service.block.impl;

import java.util.Date;

public class Block {

    private int height;

    private byte[] preBlockHash;

    private byte[] Data;

    private Date timestamp;

    private byte[] hash;

    private long nonce;

    public Block(int height, byte[] preBlockHash, byte[] data, Date timestamp, byte[] hash, long nonce) {
        this.height = height;
        this.preBlockHash = preBlockHash;
        Data = data;
        this.timestamp = timestamp;
        this.hash = hash;
        this.nonce = nonce;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public byte[] getPreBlockHash() {
        return preBlockHash;
    }

    public void setPreBlockHash(byte[] preBlockHash) {
        this.preBlockHash = preBlockHash;
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

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }
}
