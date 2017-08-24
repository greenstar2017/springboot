package org.spring.springboot;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring.springboot.domain.City;
import org.spring.springboot.kafka.producer.MsgProducer;
import org.spring.springboot.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class MsgTest {
	
	private static Log logger = LogFactory.getLog(MsgTest.class);

	@Autowired
	private MsgProducer msgProducer;
	
	@Test
	public void testSendMsg() {
		final CountDownLatch latch = new CountDownLatch(10);
		for(int i=0; i< 10; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					City city = new City();
					long l = new Random().nextLong();
					city.setId(l);
					city.setDescription("description" + l);
					city.setName("name" + l);
					city.setScore(99);
					msgProducer.send(JsonUtils.beanToJson(city));
					latch.countDown();
				}
			}).start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
	}
	
	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(101);
		
		
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
