package com.wj.spc.demo_1203.commons.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by liyaoqiang on 2018/12/6.
 */
public class SerializableUtils {
    /**
     * 序列化java对象
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream objectOutputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            return bytes;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 反序列化java对象
     *
     * @param bytes
     * @return
     */
    public static Object unSerialize(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
            return ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }
}
