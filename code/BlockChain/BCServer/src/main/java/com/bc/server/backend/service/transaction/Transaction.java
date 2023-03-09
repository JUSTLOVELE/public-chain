package com.bc.server.backend.service.transaction;

import com.bc.server.utils.ObjectToByteUtils;
import com.bc.server.utils.RSA;
import com.bc.server.utils.SerializeUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class Transaction implements Serializable {

    private static final long serialVersionUID = -2718643841231352368L;

    private String pk;

    private byte[] signature;

    private Object data;

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 计算交易信息的Hash值
     *
     * @return
     */
    public byte[] hash() {
        // 使用序列化的方式对Transaction对象进行深度复制
        byte[] serializeBytes = SerializeUtils.serialize(this);
        Transaction copyTx = (Transaction) SerializeUtils.deserialize(serializeBytes);
        return DigestUtils.sha256(SerializeUtils.serialize(copyTx));
    }

    public void sign(String privateKey) {

        try{

            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            PrivateKey priKey = KeyFactory.getInstance("RSA").generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(priKey);
            signature.update(ObjectToByteUtils.toByteArray(this.data));
            this.signature = signature.sign();

        }catch (Exception e) {
            e.printStackTrace();
        }
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
