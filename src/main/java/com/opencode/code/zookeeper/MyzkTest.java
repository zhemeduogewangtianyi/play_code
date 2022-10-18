package com.opencode.code.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class MyzkTest {
    //zk的连接地址
    //这里的地址修改成自己服务器的地址，2181是zookeeper的端口号
    public static final String zkconnect = "stable.zk.scsite.net:2181";
    //超时时间
    public static final int timeout = 15000;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = connect();
        //create(zooKeeper,"/zktest","changebefore");
        //delete(zooKeeper,"/azktest");
        //setData(zooKeeper,"/zktest","changeafter");
        Scanner sc = new Scanner(System.in);
        String data;
        while ((data = sc.nextLine()) != null) {
            getData(zooKeeper, data);
        }

    }

    //zk连接方法
    public static ZooKeeper connect() throws IOException {
        ZooKeeper zk = new ZooKeeper(zkconnect, timeout, null);
        System.out.println("zk连接成功");
        return zk;
    }

    //增加
    public static void create(ZooKeeper zooKeeper, String node, String data) throws InterruptedException, KeeperException {
        System.out.println("开始创建节点" + node + "节点数据为:" + data);
        List<ACL> acl = ZooDefs.Ids.OPEN_ACL_UNSAFE;
        CreateMode createMode = CreateMode.PERSISTENT;
        zooKeeper.create(node, data.getBytes(), acl, createMode);
        System.out.println("zk节点创建成功");
    }

    //删除
    public static void delete(ZooKeeper zooKeeper, String node) throws InterruptedException, KeeperException {
        //先判断节点是否存在
        Stat stat = zooKeeper.exists(node, false);

        System.out.println("开始删除节点:" + node + "原来的版本号是" + stat.getVersion());
        //int version = -1;
        //-1表示无视版本号
        zooKeeper.delete(node, stat.getVersion());
        System.out.println("zk节点删除成功");
    }

    //修改
    public static void setData(ZooKeeper zooKeeper, String node, String data) throws InterruptedException, KeeperException {
        //先判断节点是否存在
        Stat stat = zooKeeper.exists(node, false);
        System.out.println("开始修改节点:" + node + "原来的版本号是" + stat.getVersion());
        zooKeeper.setData(node, data.getBytes(), stat.getVersion());
        System.out.println("zk节点修改成功");
    }

    //查询
    public static void getData(ZooKeeper zooKeeper, String node)  {
        System.out.println("开始查询节点:" + node);
        try{
            byte[] bytes = zooKeeper.getData(node, false, null);
            String data = new String(bytes , "utf-8");
            System.out.println("查询到的数据是:" + data);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
