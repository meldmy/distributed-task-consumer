package task.manager.cofig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import task.manager.receiver.ReceiverService;

import static task.manager.cofig.Topics.TASK_TOPIC;

@Configuration
public class RedisSpringConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisSpringConfiguration.class);

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter taskListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(taskListenerAdapter, new PatternTopic(TASK_TOPIC.getRealTopicName()));
        System.out.println();
        LOGGER.info("Registered Redis topic with name: " + TASK_TOPIC.getRealTopicName());
        System.out.println();
        return container;
    }

    @Bean
    MessageListenerAdapter taskListenerAdapter(ReceiverService receiver) {
        return new MessageListenerAdapter(receiver, "receiveTask");
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    ReceiverService receiverService(StringRedisTemplate template) {
        return new ReceiverService(template);
    }
}
