package com.bc.server.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;

public class ObjectToByteUtils {

    private final static Log _logger = LogFactory.getLog(ObjectToByteUtils.class);

    /**
     * 将Object对象转byte数组
     * @param obj byte数组的object对象
     * @return
     */
    public static byte[] toByteArray(Object obj) {

        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {

            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            _logger.error("" ,ex);
        }

        return bytes;
    }

    /**
     * 字节传Object
     * @param bytes
     * @return
     */
    public static Object byteToObject(byte[] bytes) {

        Object obj = null;
        try {

            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception e) {
            _logger.error("" ,e);
        }

        return obj;
    }
}
