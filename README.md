## 项目介绍

> 该项目为尚筹网项目的前台会员系统

简介：尚筹网项目是一个众筹平台，主要作用是帮助创业者发布创业项目，向大众募集启动资金的融资平台，整个项目分为**前台会员**(微服务)和**后台管理**(SSM)两个模块

学习资料：https://www.bilibili.com/video/BV1bE411T7oZ

后台项目地址：

技术点：	

- 前台项目使用 SpringCloud + SpringBoot 的分布式架构; 后台项目使用 SSM 的单一架构
- [后台项目] 中使用 **SpringSecurity** 并基于 RBAC0 的模型完成通过**权限/角色控制用户操作**
- [后台项目] 中使用 **XML加配置 AOP 的方式** 实现 **声明式事务**
- [第三方接口] 通过 **阿里云云市场** 配置项目短信服务

### 架构

![image-20220204213728517](D:\StudyCode\Project\crowd-admin\README.assets\image-20220204213728517.png)

## 前端

## 后端

### SpringBoot 整合 Redis

1. 导入依赖

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-redis</artifactId>
   </dependency>
   <dependency>
       <groupId>org.apache.commons</groupId>
       <artifactId>commons-pool2</artifactId>
   </dependency>
   <dependency>
       <groupId>com.fasterxml.jackson.core</groupId>
       <artifactId>jackson-databind</artifactId>
   </dependency>
   ```

2. 编写配置文件

   ```yaml
   spring:
     redis:
       host: 192.168.227.30
       port: 6379
       timeout: 10000
       lettuce:
         pool:
           max-active: 8
           max-wait: -1
           max-idle: 8
           min-idle: 0
   ```

3. 编写配置类

   ```java
   @Configuration
   public class RedisConfig {
   
       @Resource
       private RedisConnectionFactory redisConnectionFactory;
   
       /**
        * 配置Jackson2JsonRedisSerializer序列化策略
        */
       private Jackson2JsonRedisSerializer<Object> serializer() {
           // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
           Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
           ObjectMapper objectMapper = new ObjectMapper();
   
           // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
           objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
   
           objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
   
           // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
           objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
   
           jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
           return jackson2JsonRedisSerializer;
       }
   
   
       @Bean
       public RedisTemplate<String, Object> redisTemplate() {
           RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
           redisTemplate.setConnectionFactory(redisConnectionFactory);
           // 用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
           redisTemplate.setValueSerializer(serializer());
   
           StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
           // 使用StringRedisSerializer来序列化和反序列化redis的key值
           redisTemplate.setKeySerializer(stringRedisSerializer);
   
           // hash的key也采用String的序列化方式
           redisTemplate.setHashKeySerializer(stringRedisSerializer);
           // hash的value序列化方式采用jackson
           redisTemplate.setHashValueSerializer(serializer());
           redisTemplate.afterPropertiesSet();
           return redisTemplate;
       }
   
       @Bean
       public CacheManager cacheManager() {
           RedisSerializer<String> redisSerializer = new StringRedisSerializer();
           // 配置序列化（解决乱码的问题）
           RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
               // 缓存有效期
               //.entryTtl(timeToLive)
               // 使用StringRedisSerializer来序列化和反序列化redis的key值
               .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
               // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
               .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer()))
               // 禁用空值
               .disableCachingNullValues();
   
           return RedisCacheManager.builder(redisConnectionFactory)
               .cacheDefaults(config)
               .build();
       }
   
       @Bean
       public IGlobalCache globalCache() {
           return new CrowdRedisCacheManager(redisTemplate());
       }
   
   }
   ```

4. 编写工具类

   ```java
   public interface IGlobalCache {
   
       /**
        * 指定缓存失效时间
        *
        * @param key  键
        * @param time 时间(秒)
        * @return
        */
       boolean expire(String key, long time);
   
       /**
        * @param key 键 不能为null
        * @return 时间(秒) 返回0代表为永久有效
        */
       long getExpire(String key);
   
       /**
        * 判断key是否存在
        *
        * @param key 键
        * @return true 存在 false不存在
        */
       boolean hasKey(String key);
   
       /**
        * 删除缓存
        *
        * @param key 可以传一个值 或多个
        */
       void del(String... key);
       // ============================String=============================
   
       /**
        * 普通缓存获取
        *
        * @param key 键
        * @return 值
        */
       Object get(String key);
   
       /**
        * 普通缓存放入
        *
        * @param key   键
        * @param value 值
        * @return true成功 false失败
        */
       boolean set(String key, Object value);
   
       /**
        * 普通缓存放入并设置时间
        *
        * @param key   键
        * @param value 值
        * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
        * @return true成功 false 失败
        */
       boolean set(String key, Object value, long time);
   
       /**
        * 递增
        *
        * @param key   键
        * @param delta 要增加几(大于0)
        * @return
        */
       long incr(String key, long delta);
   
       /**
        * 递减
        *
        * @param key   键
        * @param delta 要减少几(小于0)
        * @return
        */
       long decr(String key, long delta);
   
       /**
        * HashGet
        *
        * @param key  键 不能为null
        * @param item 项 不能为null
        * @return 值
        */
       Object hget(String key, String item);
   
       /**
        * 获取hashKey对应的所有键值
        *
        * @param key 键
        * @return 对应的多个键值
        */
       Map<Object, Object> hmget(String key);
   
       /**
        * HashSet
        *
        * @param key 键
        * @param map 对应多个键值
        * @return true 成功 false 失败
        */
       boolean hmset(String key, Map<String, Object> map);
   
       /**
        * HashSet 并设置时间
        *
        * @param key  键
        * @param map  对应多个键值
        * @param time 时间(秒)
        * @return true成功 false失败
        */
       boolean hmset(String key, Map<String, Object> map, long time);
   
       /**
        * 向一张hash表中放入数据,如果不存在将创建
        *
        * @param key   键
        * @param item  项
        * @param value 值
        * @return true 成功 false失败
        */
       boolean hset(String key, String item, Object value);
   
       /**
        * 向一张hash表中放入数据,如果不存在将创建
        *
        * @param key   键
        * @param item  项
        * @param value 值
        * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
        * @return true 成功 false失败
        */
       boolean hset(String key, String item, Object value, long time);
   
       /**
        * 删除hash表中的值
        *
        * @param key  键 不能为null
        * @param item 项 可以使多个 不能为null
        */
       void hdel(String key, Object... item);
   
       /**
        * 判断hash表中是否有该项的值
        *
        * @param key  键 不能为null
        * @param item 项 不能为null
        * @return true 存在 false不存在
        */
       boolean hHasKey(String key, String item);
   
       /**
        * hash递增 如果不存在,就会创建一个 并把新增后的值返回
        *
        * @param key  键
        * @param item 项
        * @param by   要增加几(大于0)
        * @return
        */
       double hincr(String key, String item, double by);
   
       /**
        * hash递减
        *
        * @param key  键
        * @param item 项
        * @param by   要减少记(小于0)
        * @return
        */
       double hdecr(String key, String item, double by);
   
       /**
        * 根据key获取Set中的所有值
        *
        * @param key 键
        * @return
        */
       Set<Object> sGet(String key);
   
       /**
        * 根据value从一个set中查询,是否存在
        *
        * @param key   键
        * @param value 值
        * @return true 存在 false不存在
        */
       boolean sHasKey(String key, Object value);
   
       /**
        * 将数据放入set缓存
        *
        * @param key    键
        * @param values 值 可以是多个
        * @return 成功个数
        */
       long sSet(String key, Object... values);
   
       /**
        * 将set数据放入缓存
        *
        * @param key    键
        * @param time   时间(秒)
        * @param values 值 可以是多个
        * @return 成功个数
        */
       long sSetAndTime(String key, long time, Object... values);
   
   
       /**
        * 获取set缓存的长度
        *
        * @param key 键
        * @return
        */
       long sGetSetSize(String key);
   
       /**
        * 移除值为value的
        *
        * @param key    键
        * @param values 值 可以是多个
        * @return 移除的个数
        */
       long setRemove(String key, Object... values);
   
       /**
        * 获取list缓存的内容
        *
        * @param key   键
        * @param start 开始
        * @param end   结束 0 到 -1代表所有值
        * @return
        */
       List<Object> lGet(String key, long start, long end);
   
       /**
        * 获取list缓存的长度
        *
        * @param key 键
        * @return
        */
       long lGetListSize(String key);
   
       /**
        * 通过索引 获取list中的值
        *
        * @param key   键
        * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
        * @return
        */
       Object lGetIndex(String key, long index);
   
       /**
        * 将list放入缓存
        *
        * @param key   键
        * @param value 值
        * @return
        */
       boolean lSet(String key, Object value);
   
       /**
        * 将list放入缓存
        *
        * @param key   键
        * @param value 值
        * @return
        */
       boolean lSet(String key, Object value, long time);
   
       /**
        * 将list放入缓存
        *
        * @param key   键
        * @param value 值
        * @param time  时间(秒)
        * @return
        */
       boolean lSetAll(String key, List<Object> value);
   
   
       /**
        * 将list放入缓存
        *
        * @param key   键
        * @param value 值
        * @param time  时间(秒)
        * @return
        */
       boolean lSetAll(String key, List<Object> value, long time);
   
       /**
        * 将list放入缓存
        *
        * @param key   键
        * @param value 值
        * @return
        */
   
       boolean rSet(String key, Object value);
   
       /**
        * 将list放入缓存
        *
        * @param key   键
        * @param value 值
        * @param time  时间(秒)
        * @return
        */
   
       boolean rSet(String key, Object value, long time);
   
       /**
        * 将list放入缓存
        *
        * @param key   键
        * @param value 值
        * @return
        */
       boolean rSetAll(String key, List<Object> value);
   
       /**
        * 将list放入缓存
        *
        * @param key   键
        * @param value 值
        * @param time  时间(秒)
        * @return
        */
       boolean rSetAll(String key, List<Object> value, long time);
   
       /**
        * 根据索引修改list中的某条数据
        *
        * @param key   键
        * @param index 索引
        * @param value 值
        * @return
        */
       boolean lUpdateIndex(String key, long index, Object value);
   
       /**
        * 移除N个值为value
        *
        * @param key   键
        * @param count 移除多少个
        * @param value 值
        * @return 移除的个数
        */
       long lRemove(String key, long count, Object value);
   
       /**
        * 从redis集合中移除[start,end]之间的元素
        *
        * @param key
        * @param stard
        * @param end
        * @return
        */
       void rangeRemove(String key, Long stard, Long end);
   
       /**
        * 返回当前redisTemplate
        *
        * @return
        */
       RedisTemplate getRedisTemplate();
   }
   ```

   实现类

   ```java
   public final class CrowdRedisCacheManager implements IGlobalCache {
   
       private final RedisTemplate<String, Object> redisTemplate;
   
       public CrowdRedisCacheManager(RedisTemplate<String, Object> redisTemplate) {
           this.redisTemplate = redisTemplate;
       }
   
       @Override
       public boolean expire(String key, long time) {
           try {
               if (time > 0) {
                   redisTemplate.expire(key, time, TimeUnit.SECONDS);
               }
               return true;
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
       }
   
       @Override
       public long getExpire(String key) {
           return redisTemplate.getExpire(key, TimeUnit.SECONDS);
       }
   
       @Override
       public boolean hasKey(String key) {
           try {
               return redisTemplate.hasKey(key);
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
       }
   
       @Override
       @SuppressWarnings("unchecked")
       public void del(String... key) {
           if (key != null && key.length > 0) {
               if (key.length == 1) {
                   redisTemplate.delete(key[0]);
               } else {
                   redisTemplate.delete(CollectionUtils.arrayToList(key));
               }
           }
       }
   
       @Override
       public Object get(String key) {
           return key == null ? null : redisTemplate.opsForValue().get(key);
       }
   
       @Override
       public boolean set(String key, Object value) {
           try {
               redisTemplate.opsForValue().set(key, value);
               return true;
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
       }
   
       @Override
       public boolean set(String key, Object value, long time) {
           try {
               if (time > 0) {
                   redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
               } else {
                   set(key, value);
               }
               return true;
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
       }
   
       @Override
       public long incr(String key, long delta) {
           if (delta < 0) {
               throw new RuntimeException("递增因子必须大于0");
           }
           return redisTemplate.opsForValue().increment(key, delta);
       }
   
       @Override
       public long decr(String key, long delta) {
           if (delta < 0) {
               throw new RuntimeException("递减因子必须大于0");
           }
           return redisTemplate.opsForValue().increment(key, -delta);
       }
   
       @Override
       public Object hget(String key, String item) {
           return redisTemplate.opsForHash().get(key, item);
       }
   
       @Override
       public Map<Object, Object> hmget(String key) {
           return redisTemplate.opsForHash().entries(key);
       }
   
       @Override
       public boolean hmset(String key, Map<String, Object> map) {
           try {
               redisTemplate.opsForHash().putAll(key, map);
               return true;
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
       }
   
       @Override
       public boolean hmset(String key, Map<String, Object> map, long time) {
           try {
               redisTemplate.opsForHash().putAll(key, map);
               if (time > 0) {
                   expire(key, time);
               }
               return true;
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
       }
   
       @Override
       public boolean hset(String key, String item, Object value) {
           try {
               redisTemplate.opsForHash().put(key, item, value);
               return true;
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
       }
   
       @Override
       public boolean hset(String key, String item, Object value, long time) {
           try {
               redisTemplate.opsForHash().put(key, item, value);
               if (time > 0) {
                   expire(key, time);
               }
               return true;
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
       }
   
       @Override
       public void hdel(String key, Object... item) {
           redisTemplate.opsForHash().delete(key, item);
       }
   
       @Override
       public boolean hHasKey(String key, String item) {
           return redisTemplate.opsForHash().hasKey(key, item);
       }
   
       @Override
       public double hincr(String key, String item, double by) {
           return redisTemplate.opsForHash().increment(key, item, by);
       }
   
       @Override
       public double hdecr(String key, String item, double by) {
           return redisTemplate.opsForHash().increment(key, item, -by);
       }
   
       @Override
       public Set<Object> sGet(String key) {
           try {
               return redisTemplate.opsForSet().members(key);
           } catch (Exception e) {
               e.printStackTrace();
               return null;
           }
       }
   
       @Override
       public boolean sHasKey(String key, Object value) {
           try {
               return redisTemplate.opsForSet().isMember(key, value);
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
       }
   
       @Override
       public long sSet(String key, Object... values) {
           try {
               return redisTemplate.opsForSet().add(key, values);
           } catch (Exception e) {
               e.printStackTrace();
               return 0;
           }
       }
   
       @Override
       public long sSetAndTime(String key, long time, Object... values) {
           try {
               Long count = redisTemplate.opsForSet().add(key, values);
               if (time > 0) {
                   expire(key, time);
               }
               return count;
           } catch (Exception e) {
               e.printStackTrace();
               return 0;
           }
       }
   
       @Override
       public long sGetSetSize(String key) {
           try {
               return redisTemplate.opsForSet().size(key);
           } catch (Exception e) {
               e.printStackTrace();
               return 0;
           }
       }
   
       @Override
       public long setRemove(String key, Object... values) {
           try {
               Long count = redisTemplate.opsForSet().remove(key, values);
               return count;
           } catch (Exception e) {
               e.printStackTrace();
               return 0;
           }
       }
   
       @Override
       public List<Object> lGet(String key, long start, long end) {
           try {
               return redisTemplate.opsForList().range(key, start, end);
           } catch (Exception e) {
               e.printStackTrace();
               return null;
           }
       }
   
       @Override
       public long lGetListSize(String key) {
           try {
               return redisTemplate.opsForList().size(key);
           } catch (Exception e) {
               e.printStackTrace();
               return 0;
           }
       }
   
       @Override
       public Object lGetIndex(String key, long index) {
           try {
               return redisTemplate.opsForList().index(key, index);
           } catch (Exception e) {
               e.printStackTrace();
               return null;
           }
       }
   
       @Override
       public boolean lSetAll(String key, List<Object> value) {
           try {
               redisTemplate.opsForList().leftPushAll(key, value);
               return true;
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
       }
   
       @Override
       public boolean lSet(String key, Object value) {
           try {
               redisTemplate.opsForList().leftPushIfPresent(key, value);
               return true;
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
       }
   
       @Override
       public boolean lSet(String key, Object value, long time) {
           try {
               redisTemplate.opsForList().leftPush(key, value);
               if (time > 0) {
                   expire(key, time);
               }
               return true;
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
   
       }
   
       @Override
       public boolean lSetAll(String key, List<Object> value, long time) {
           try {
               redisTemplate.opsForList().leftPushAll(key, value);
               if (time > 0)
                   expire(key, time);
               return true;
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
       }
   
       @Override
       public boolean rSet(String key, Object value) {
           try {
               redisTemplate.opsForList().rightPush(key, value);
               return true;
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
       }
   
       @Override
       public boolean rSet(String key, Object value, long time) {
           try {
               redisTemplate.opsForList().rightPush(key, value);
               if (time > 0) {
                   expire(key, time);
               }
               return true;
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
   
       }
   
       @Override
       public boolean rSetAll(String key, List<Object> value) {
           try {
               redisTemplate.opsForList().rightPushAll(key, value);
               return true;
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
   
       }
   
       @Override
       public boolean rSetAll(String key, List<Object> value, long time) {
           try {
               redisTemplate.opsForList().rightPushAll(key, value);
               if (time > 0)
                   expire(key, time);
               return true;
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
       }
   
       @Override
       public boolean lUpdateIndex(String key, long index, Object value) {
           try {
               redisTemplate.opsForList().set(key, index, value);
               return true;
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           }
       }
   
       @Override
       public long lRemove(String key, long count, Object value) {
           try {
               Long remove = redisTemplate.opsForList().remove(key, count, value);
               return remove;
           } catch (Exception e) {
               e.printStackTrace();
               return 0;
           }
       }
   
       @Override
       public void rangeRemove(String key, Long stard, Long end) {
           try {
               redisTemplate.opsForList().trim(key, stard, end);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
   
       @Override
       public RedisTemplate getRedisTemplate() {
           return redisTemplate;
       }
   
   }
   ```

   

### 实体类进一步细分

1. VO 视图对象
   - 接收游览器发送的对象
   - 把数据响应回给游览器去显示
2. PO 持久化对象
   - 将数据封装到 PO 对象并存入数据库
   - 将数据库查询i出来的数据封装到 PO 对象
3. DO 数据对象
   - 从 各种中间件/第三方接口 中查询出来的数据，例如：Redis / ElasticSearch ...
4. DTO 数据传输对象
   - 微服务之间通信时使用的数据对象

## 其他

### 三高

1. 高可扩：项目设计架构时需要考虑到功能的持续更新
2. 高性能：提高系统响应速度，系统处理一个请求的时间尽可能短，减少用户等待的时间，优化用户体验
3. 高并发：当用户访问量增大时，提高系统的并发能力，避免系统崩溃

