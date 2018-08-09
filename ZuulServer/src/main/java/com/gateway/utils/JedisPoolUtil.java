package com.gateway.utils;

import redis.clients.jedis.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by pengns on 2018/8/8.
 */
public class JedisPoolUtil {

    private static ShardedJedisPool shardedJedisPool;

    // 静态代码初始化池配置
    static {
        //change "maxActive" -> "maxTotal" and "maxWait" -> "maxWaitMillis" in all examples
        JedisPoolConfig config = new JedisPoolConfig();
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(5);
        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
        config.setMaxTotal(-1);
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setMaxWaitMillis(5);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        //创建四个redis服务实例，并封装在list中
        List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
        list.add(new JedisShardInfo("10.8.228.213", 6379));
        //创建具有分片功能的的Jedis连接池
        shardedJedisPool = new ShardedJedisPool(config, list);
    }
    public static ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }
    public static void main(String[] args) {
        ShardedJedis jedis = JedisPoolUtil.getShardedJedisPool().getResource();
        
    }
}
