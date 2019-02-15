package com.wj.spc.demo_1203.config.zookeeper.helper;/*
package com.wj.spc.demo_1203.config.zookeeper.helper;

import org.apache.curator.framework.CuratorFramework;

import static java.nio.charset.Charset.forName;

*/
/**
 * Created by liyaoqiang on 2019/1/4.
 *//*

public class ZookeeperHelper {

    public static String getData(CuratorFramework curatorFramework, String path) {
        try {
            byte[] data = curatorFramework.getData().forPath(path);
            return new String(data, forName("utf-8"));
        } catch (Exception e) {
            throw new RuntimeException("Path: " + path + " does not exist");
        }
    }

}
*/
