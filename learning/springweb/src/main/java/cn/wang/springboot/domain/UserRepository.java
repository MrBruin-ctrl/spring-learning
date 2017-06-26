package cn.wang.springboot.domain;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Administrator on 2017/5/10.
 */
@CacheConfig(cacheNames = "users")
public interface UserRepository extends JpaRepository<User,Long> {
    @Cacheable(key = "#p0")
    User findByName(String name);

    User findByNameAndAge(String name, Integer age);

    @CachePut(key = "#p0.name")
    User save(User user);

    User findById(Long id);

    @Query("select u from User u where u.name = :name")
    User findUser(@Param("name") String name);
}
