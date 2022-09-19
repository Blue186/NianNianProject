package com.nian.business.utils;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
public class RedissionUtil {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 默认缓存时间
     */
    private static final Long DEFAULT_EXPIRED = 5 * 60L;
    /**
     * redis key前缀
     */
    private static final String REDIS_KEY_PREFIX = "";
    /**
     * 连接超时时间
     */
    private Integer connectTimeout = 3000;
    private RedissonClient redisson;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.database}")
    private Integer database;
    /**
     * 初始化连接
     *
     * @throws IOException
     */
    @Bean
    public RedissonClient redissonClient(){
//        RedissonClient redissonClient = null;
        Config config = new Config();
        String url = "redis://"+host+":"+port;
        config.useSingleServer().setAddress(url)
                .setPassword(password)
                .setDatabase(database);
        try{
            redisson = Redisson.create(config);
            RBloomFilter<Object> bloomFilter = redisson.getBloomFilter("bloom-filter");
            bloomFilter.tryInit(100000,0.001);
            logger.info("布隆过滤器初始化成功");
            logger.info( "redis连接成功,server={}", host);
            return redisson;
        }catch (Exception e){
            logger.info("布隆过滤器初始化失败");
            return null;
        }
    }
//    @PostConstruct
//    public void init() throws IOException {
//        Config config = new Config();
//        config.useSingleServer()
//                .setAddress("redis://"+ host+":"+port)
//                .setPassword(password)
//                .setDatabase(database);
////                .setPingTimeout(3000)
////                .setTimeout(5000)
////                .setSubscriptionConnectionMinimumIdleSize(1)
////                .setSubscriptionConnectionPoolSize(256)
////                .setConnectTimeout(connectTimeout)
////                .setConnectionPoolSize(256)
////                .setConnectionMinimumIdleSize(1)
////                .setRetryAttempts(3)
////                .setRetryInterval(3000)
////                .setIdleConnectionTimeout(30000)
////                .setClientName("com.meiguang.mgframework.extend.redis.RedisCache");
//        if (redisson == null) {
//            redisson = Redisson.create(config);
//            logger.info( "redis连接成功,server={}", host);
//        } else {
//            logger.warn("redis 重复连接,config={}", config);
//        }
//    }

    /**
     * 读取缓存
     *
     * @param key 缓存key
     * @param <T>
     * @return 缓存返回值
     */
    public <T> T get(String key) {
        RBucket<T> bucket = redisson.getBucket(REDIS_KEY_PREFIX + key);
        return bucket.get();
    }

    /**
     * 以string的方式读取缓存
     *
     * @param key 缓存key
     * @return 缓存返回值
     */
    public String getString(String key) {
        RBucket<String> bucket = redisson.getBucket(REDIS_KEY_PREFIX + key, StringCodec.INSTANCE);
        return bucket.get();
    }

    /**
     * 设置缓存（注：redisson会自动选择序列化反序列化方式）
     *
     * @param key   缓存key
     * @param value 缓存值
     * @param <T>
     */
    public <T> void put(String key, T value) {
        RBucket<T> bucket = redisson.getBucket(REDIS_KEY_PREFIX + key);
        bucket.set(value, DEFAULT_EXPIRED, TimeUnit.SECONDS);
    }

    /**
     * 以string的方式设置缓存
     *
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        RBucket<String> bucket = redisson.getBucket(REDIS_KEY_PREFIX + key, StringCodec.INSTANCE);
        bucket.set(value, DEFAULT_EXPIRED, TimeUnit.SECONDS);
    }

    /**
     * 以string的方式保存缓存（与其他应用共用redis时需要使用该函数）
     *
     * @param key     缓存key
     * @param value   缓存值
     * @param expired 缓存过期时间
     */
    public void putString(String key, String value, long expired) {
        RBucket<String> bucket = redisson.getBucket(REDIS_KEY_PREFIX + key, StringCodec.INSTANCE);
        bucket.set(value, expired <= 0 ? DEFAULT_EXPIRED : expired, TimeUnit.SECONDS);
    }

    /**
     * 如果不存在则写入缓存（string方式，不带有redisson的格式信息）
     *
     * @param key     缓存key
     * @param value   缓存值
     * @param expired 缓存过期时间
     */
    public boolean putStringIfAbsent(String key, String value, long expired) {
        RBucket<String> bucket = redisson.getBucket(REDIS_KEY_PREFIX + key, StringCodec.INSTANCE);
        return bucket.trySet(value, expired <= 0 ? DEFAULT_EXPIRED : expired, TimeUnit.SECONDS);
    }

    /**
     * 如果不存在则写入缓存（string方式，不带有redisson的格式信息）（不带过期时间，永久保存）
     *
     * @param key     缓存key
     * @param value   缓存值
     */
    public boolean putStringIfAbsent(String key, String value) {
        RBucket<String> bucket = redisson.getBucket(REDIS_KEY_PREFIX + key, StringCodec.INSTANCE);
        return bucket.trySet(value);
    }

    /**
     * 设置缓存
     *
     * @param key     缓存key
     * @param value   缓存值
     * @param expired 缓存过期时间
     * @param <T>     类型
     */
    public <T> void put(String key, T value, long expired) {
        RBucket<T> bucket = redisson.getBucket(REDIS_KEY_PREFIX + key);
        bucket.set(value, expired <= 0 ? DEFAULT_EXPIRED : expired, TimeUnit.SECONDS);
    }

    /**
     * 移除缓存
     *
     * @param key
     */
    public void remove(String key) {
        redisson.getBucket(REDIS_KEY_PREFIX + key).delete();
    }

    /**
     * 判断缓存是否存在
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        return redisson.getBucket(REDIS_KEY_PREFIX + key).isExists();
    }


    /**
     * 暴露redisson的RList对象
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> RList<T> getRedisList(String key) {
        return redisson.getList(REDIS_KEY_PREFIX + key);
    }

    /**
     * 暴露redisson的RMapCache对象
     *
     * @param key
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> RMapCache<K, V> getRedisMap(String key) {
        return redisson.getMapCache(REDIS_KEY_PREFIX + key);
    }

    /**
     * 暴露redisson的RSET对象
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> RSet<T> getRedisSet(String key) {
        return redisson.getSet(REDIS_KEY_PREFIX + key);
    }


    /**
     * 暴露redisson的RScoredSortedSet对象
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> RScoredSortedSet<T> getRedisScoredSortedSet(String key) {
        return redisson.getScoredSortedSet(REDIS_KEY_PREFIX + key);
    }

    /**
     * 暴露redisson的Lock对象
     *
     * @param key
     * @return
     */
    public RLock getRedisLock(String key) {
        return redisson.getLock(REDIS_KEY_PREFIX + key);
    }


    @PreDestroy
    public void close() {
        try {
            if (redisson != null) {
                redisson.shutdown();
            }
        } catch (Exception ex) {
            logger.error( ex.getMessage(), ex);
        }
    }

    /**
     * Setter method for property <tt>host</tt>.
     *
     * @param host value to be assigned to property host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Setter method for property <tt>password</tt>.
     *
     * @param password value to be assigned to property password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter method for property <tt>connectTimeout</tt>.
     *
     * @param connectTimeout value to be assigned to property connectTimeout
     */
    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}
