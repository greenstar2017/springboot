package org.spring.springboot.kafka.consumer;

import org.spring.springboot.domain.City;
import org.spring.springboot.service.impl.CityESServiceImpl;
import org.spring.springboot.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 * @author xiaojf 2017/3/24 14:36
 */
@Component
public class MsgConsumer {
	
	@Autowired
	private CityESServiceImpl cityESServiceImpl;
	
    @KafkaListener(topics = {"topic1"}, id="2")
    public void processMessage(String content) {
        System.out.println("MsgConsumer" + content);
        
        City city = JsonUtils.json2Object(content, City.class);
        cityESServiceImpl.saveCity(city);
    }


}