package com.example.demo.tools;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RedisLock {

    /**
     * 加锁标志
     */
    public static final String LOCKED = "TRUE";
    /**
     * 毫秒与毫微秒的换算单位 1毫秒 = 1000000毫微秒
     */
    public static final long MILLI_NANO_CONVERSION = 1000 * 1000L;
    /**
     * 默认超时时间（毫秒）
     */
    public static final long DEFAULT_TIME_OUT = 1000;
    public static final Random RANDOM = new Random();
    /**
     * 锁的超时时间（秒），过期删除
     */
    public static final int EXPIRE = 3 * 60;

    private StringRedisTemplate redisTemplate;
    private String key;

    private boolean locked = false;


    public RedisLock(String key,StringRedisTemplate redisTemplate){
        this.key = key;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 加锁
     * 应该以：
     * lock();
     * try {
     * doSomething();
     * } finally {
     * unlock()；
     * }
     * 的方式调用
     * @param timeout
     * @return
     */
    public boolean lock(long timeout){
        long nano = System.nanoTime();
        timeout *= MILLI_NANO_CONVERSION;
        if (addLock(timeout, nano, EXPIRE)){
            this.locked = true;
            return locked;
        }
        return locked;
    }

    /**
     * 加锁
     * 应该以：
     * lock();
     * try {
     * doSomething();
     * } finally {
     * unlock()；
     * }
     * 的方式调用
     * @param timeout
     * @param expire
     * @return
     */
    public boolean lock(long timeout,int expire){
        long nano = System.nanoTime();
        timeout *= MILLI_NANO_CONVERSION;
        if (addLock(timeout, nano, expire)) {
            this.locked = true;
            return locked;

        }
        return locked;
    }

    /**
     * 加锁
     * 应该以：
     * lock();
     * try {
     * doSomething();
     * } finally {
     * unlock()；
     * }
     * 的方式调用
     * @return
     */
    //默认加锁
    public boolean lock(){
        return lock(DEFAULT_TIME_OUT);
    }

    /**
     * @param timeout 加锁超时时间
     * @param nano
     * @param expire 锁的超时时间(秒),过期删除
     * @return 成功或失败标志
     */
    private boolean addLock(long timeout, long nano, int expire) {
        try{
            while ((System.nanoTime()-nano)<timeout){
                if(this.redisTemplate.opsForValue().setIfAbsent(this.key,LOCKED)){
                    this.redisTemplate.expire(this.key,expire,TimeUnit.SECONDS);
                    return true;
                }
                // 短暂休眠，避免出现活锁
                Thread.sleep(3, RANDOM.nextInt(500));
            }
        }catch (Exception e){
            throw new RuntimeException("Locking error",e);
        }
        return false;
    }
    /**
     *解锁
     * 无论是否加锁成功，都需要调用unlock
     * 应该以：
     * lock();
     * try {
     * doSomething();
     * } finally {
     * unlock()；
     * }
     * 的方式调用
     */
    public void unlock(){
        try {
            if (this.locked){
                this.redisTemplate.delete(this.key);
                this.locked = false;
            }
        }catch (Exception e){
            throw new RuntimeException("Unlock error",e);
        }
    }
}
