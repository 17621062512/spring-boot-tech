package com.ray.tech.config;

import com.beust.jcommander.internal.Lists;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
//import com.google.common.collect.Lists;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    public static final String CACHE_NAME = "Website_2_DataSource_Cache";

    /**
     * @return
     * @See 所有配置:{@link CaffeineSpec#configure(String, String)}
     */
    @Bean(name = CACHE_NAME)
    public CaffeineCacheManager cacheManager() {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .maximumSize(500)
                .expireAfterWrite(20, TimeUnit.MINUTES);
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        caffeineCacheManager.setCacheNames(Lists.newArrayList(CACHE_NAME));

        return caffeineCacheManager;
    }


//    private CacheConfiguration getShotTimeCacheConfiguration() {
//        //Cache配置
//        CacheConfiguration cacheConfig = new CacheConfiguration();
//        // 重要重要重要！！！根据此命名生成Cache，并在使用时被配置到相应的参数上
//        //e.g: @Cacheable(cacheNames = "test", ......)
//        cacheConfig.setName(I3m_L10m_CACHE);
//        //设置允许空闲时长(秒)
//        // 动态改变
//        cacheConfig.setTimeToIdleSeconds(3 * 60L);
//        //设置允许存活时长(秒)
//        // 动态改变
//        cacheConfig.setTimeToLiveSeconds(10 * 60L);
//        //设置堆中可缓存对象数量，0表示无限
//        // 动态改变
//        cacheConfig.setMaxEntriesLocalHeap(500);
//        //设置磁盘中可缓存对象数量，0表示无限,需要支持序列化
////        cacheConfig.setMaxEntriesLocalDisk(10000);
//        //设置总可缓存对象数量，仅用于terracott集群缓存
//        //cacheConfig.setMaxEntriesInCache(105000);
//        //堆最大占比(此配置可被动态改变)
//        //cacheConfig.setMaxBytesLocalHeap("80%");
//        //添加持久化策略
//        PersistenceConfiguration persistenceConfiguration = new PersistenceConfiguration();
//        //如果内存溢出，则放入本地临时文件中,需要支持序列化
//        //作为此方法的替代cacheConfig.setOverflowToDisk(true);
//        persistenceConfiguration.strategy(PersistenceConfiguration.Strategy.NONE);
//        cacheConfig.addPersistence(persistenceConfiguration);
//        //是否永恒，即永不过期，默认false
//        //cacheConfig.setEternal(false);
//        //是否开启日志，默认false,最好不要开启，仅用于terracott集群缓存
//        cacheConfig.setLogging(false);
//        //是否开启非堆存储，默认true
//        //cacheConfig.setOverflowToOffHeap(true);
//        return cacheConfig;
//    }
//
//    private CacheConfiguration getLongTimeCacheConfiguration() {
//        //Cache配置
//        CacheConfiguration cacheConfig = new CacheConfiguration();
//        // 重要重要重要！！！根据此命名生成Cache，并在使用时被配置到相应的参数上
//        //e.g: @Cacheable(cacheNames = "test", ......)
//        cacheConfig.setName(I1h_L4h_CACHE);
//        //设置允许空闲时长(秒)
//        // 动态改变
//        cacheConfig.setTimeToIdleSeconds(60 * 60L);
//        //设置允许存活时长(秒)
//        // 动态改变
//        cacheConfig.setTimeToLiveSeconds(4 * 60 * 60L);
//        //设置堆中可缓存对象数量，0表示无限
//        // 动态改变
//        cacheConfig.setMaxEntriesLocalHeap(500);
//        //设置磁盘中可缓存对象数量，0表示无限,需要支持序列化
////        cacheConfig.setMaxEntriesLocalDisk(10000);
//        //设置总可缓存对象数量，仅用于terracott集群缓存
//        //cacheConfig.setMaxEntriesInCache(105000);
//        //堆最大占比(此配置可被动态改变)
//        //cacheConfig.setMaxBytesLocalHeap("80%");
//        //添加持久化策略
//        PersistenceConfiguration persistenceConfiguration = new PersistenceConfiguration();
//        //如果内存溢出，则放入本地临时文件中,需要支持序列化
//        //作为此方法的替代cacheConfig.setOverflowToDisk(true);
//        persistenceConfiguration.strategy(PersistenceConfiguration.Strategy.NONE);
//        cacheConfig.addPersistence(persistenceConfiguration);
//        //是否永恒，即永不过期，默认false
//        //cacheConfig.setEternal(false);
//        //是否开启日志，默认false,最好不要开启，仅用于terracott集群缓存
//        cacheConfig.setLogging(false);
//        //是否开启非堆存储，默认true
//        //cacheConfig.setOverflowToOffHeap(true);
//        return cacheConfig;
//    }
//
//    /**
//     * 创建 EhCache
//     * <p>
//     * net.sf.ehcache
//     *
//     * @return EhCacheCacheManager
//     */
//    @Bean
//    public EhCacheCacheManager cacheManager() {
//        //Cache管理器配
//        Configuration managerConfig = new Configuration();
//        managerConfig.setName("ehcache_manager");
//        // 如果需要多个不同的cache只需要添加多个配置即可
//        managerConfig.addCache(getShotTimeCacheConfiguration());
//        managerConfig.addCache(getLongTimeCacheConfiguration());
//        //根据以上2种配置，创建Cache管理器及Cache
//        CacheManager cacheManager = CacheManager.create(managerConfig);
//        //讲创建好的Cache管理器添加到Spring的EhCache管理器中
//        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
//        ehCacheCacheManager.setCacheManager(cacheManager);
//        return ehCacheCacheManager;
//    }
}
