package com.nian.business.utils;

import org.springframework.stereotype.Component;


/**
 * 定时执行缓存添加任务
 */
@Component
public class RedissonCache {

//    /**
//     * 用户信息缓存定时更新
//     */
//    @Scheduled(cron = "0 0 8 1/1 1-12 ?")
//    public void userCacheUpdate(){
//        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter("bloom-filter");
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.orderByDesc("id");
//        List<User> users = userService.getBaseMapper().selectList(wrapper);//拿到所有的users
//        for (User user : users) {
//            String key = CacheCode.CACHE_USER+user.getId();
//            bloomFilter.add(key);
//            redissonClient.getBucket(key).trySet(user,CacheCode.USER_TIME,TimeUnit.MINUTES);//这里在添加到布隆过滤器中的同时，添加到redis缓存。
//        }
//    }
}
