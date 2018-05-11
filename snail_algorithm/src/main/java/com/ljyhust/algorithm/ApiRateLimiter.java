package com.ljyhust.algorithm;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

/**
 * API限流算法，限制接口访问次数，令牌桶算法，tokenBucket
 * bucket存放在redis中
 */
public class ApiRateLimiter {

    private JedisPool jedisPool;

    // 时间片断
    private long intervalInMills;
    // 时间片断内能访问的次数限制
    private long limit;
    // token添加速率
    private double intervalPerPermit;

    public ApiRateLimiter(long intervalInMills, long limit, double intervalPerPermit) {
        this.intervalInMills = intervalInMills;
        this.limit = limit;
        this.intervalPerPermit = intervalPerPermit * 1.0 / limit;
    }

    /**
     * 获取能否访问，方法通过userId或IP唯一访问者标识，来判断是否有访问接口的权限，是否达到接口访问上限等
     * @param uerId 客户标识
     * @return
     */
    public boolean access(String uerId) {

        // 获取redisKey
        String key = genKey(uerId);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Map<String, String> bucketRedis = jedis.hgetAll(key);

            // 不存在该bucket
            if (null == bucketRedis || bucketRedis.size() == 0) {
                TokenBucket bucket = new TokenBucket(System.currentTimeMillis(), this.limit - 1);
                jedis.hmset(key, bucket.toHashMap());
                return true;
            }

            if (bucketRedis.size() != 0) {
                TokenBucket bucket = fromHashMap(bucketRedis);
                // 判断时间间隔
                long currentTimeMillis = System.currentTimeMillis();
                long intervalTimeMillis = currentTimeMillis - bucket.lastRefreshTime;
                long pushTokenCount = (long) (intervalTimeMillis / intervalPerPermit);

                long currentRemainCount = 0;
                // 判断本次访问到上次访问的时间>intervalInMill, 如果大于说明这段时间超过了limit
                if (intervalTimeMillis > intervalInMills) {
                    currentRemainCount = limit;
                } else {
                    currentRemainCount = Math.min(pushTokenCount + bucket.getTokenRemainCount(), limit);
                }

                bucket.setLastRefreshTime(currentTimeMillis);

                // 设置剩余token count
                if (currentRemainCount == 0) {
                    bucket.setTokenRemainCount(currentRemainCount);
                    jedis.hmset(key, bucket.toHashMap());
                    return false;
                } else {
                    bucket.setTokenRemainCount(currentRemainCount - 1);
                    jedis.hmset(key, bucket.toHashMap());
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return false;

    }

    class TokenBucket {
        // 上次访问时间戳
        private long lastRefreshTime;
        // 剩余个数
        private long tokenRemainCount;

        public TokenBucket(long lastRefreshTime, long tokenRemainCount) {
            this.lastRefreshTime = lastRefreshTime;
            this.tokenRemainCount = tokenRemainCount;
        }

        public Map<String, String> toHashMap() {
            Map<String, String> map = new HashMap<>();
            map.put("lastRefreshTime", String.valueOf(lastRefreshTime));
            map.put("tokenRemainCount", String.valueOf(tokenRemainCount));
            return map;
        }

        public long getLastRefreshTime() {
            return lastRefreshTime;
        }

        public void setLastRefreshTime(long lastRefreshTime) {
            this.lastRefreshTime = lastRefreshTime;
        }

        public long getTokenRemainCount() {
            return tokenRemainCount;
        }

        public void setTokenRemainCount(long tokenRemainCount) {
            this.tokenRemainCount = tokenRemainCount;
        }
    }

    public TokenBucket fromHashMap(Map<String, String> map) {
        long lastVisitTime = Long.parseLong(map.get("lastRefreshTime"));
        long tokenRemain = Long.parseLong(map.get("tokenRemainCount"));
        return new TokenBucket(lastVisitTime, tokenRemain);
    }

    private String genKey(String userId) {
        return "rate:limiter:" + intervalInMills + ":" + limit + ":" + userId;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
