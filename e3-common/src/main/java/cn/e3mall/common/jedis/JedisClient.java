package cn.e3mall.common.jedis;

import java.util.List;

/**
 * redis客户端接口
 * @author Dragon
 *
 */
public interface JedisClient {
	/**
	 * 向redis中添加string类型的值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	String set(String key,String value);
	
	/**
	 * 根据key获取string的value
	 * @param key
	 * @return
	 */
	String get(String key);
	
	/**
	 * 判断这个key是否存在
	 * @param key
	 * @return
	 */
	Boolean exists(String key);
	
	/**
	 * 设置超时时间
	 * @param key 键
	 * @param seconds 超时时间，以秒为单位
	 * @return
	 */
	Long expire(String key,int seconds);
	
	/**
	 * 获取key的剩余生存时间
	 * @param key
	 * @return 以秒为单位，获取key的剩余生存时间
	 */
	Long ttl(String key);
	
	/**
	 *  设置这个key对应的value自增
	 * @param key
	 * @return
	 */
	Long incr(String key);
	
	/**
	 *  添加hash值
	 * @param key 键
	 * @param field 二级键
	 * @param value 值
	 * @return
	 */
	Long hset(String key,String field,String value);
	
	/**
	 *  获取哈希值
	 * @param key 键
	 * @param field 二级键
	 * @return
	 */
	String hget(String key,String field);
	
	/**
	 * 删除哈希值
	 * @param key 键
	 * @param fileld 二级键数组
	 * @return
	 */
	Long hdel(String key,String...fileld);

	/**
	 * 删除某个key
	 * @param key
	 * @return
	 */
	Long del(String key);

	/**
	 * 获取所有的values
	 * @param key
	 * @return
	 */
	List<String> hvals(String key);
}
