package cn.wang.springboot.domain;

import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import javax.persistence.Converter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Administrator on 2017/5/11.
 */
public class RedisObjectSerializer implements RedisSerializer<Object>{
    private org.springframework.core.convert.converter.Converter<Object,byte[]> serializer = new SerializingConverter();
    private org.springframework.core.convert.converter.Converter<byte[],Object> deserialize = new DeserializingConverter();
    static final byte[] EMPTY_ARRAY = new byte[0];
    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if(o==null){
            return EMPTY_ARRAY;
        }
        return serializer.convert(o);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if(bytes==null){
            return null;
        }
        return deserialize.convert(bytes);
    }
}
