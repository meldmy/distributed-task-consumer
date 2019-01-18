package task.manager.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import task.manager.cofig.Topics;

import java.util.LinkedHashSet;

import static task.manager.cofig.Topics.SUMMARY_TOPIC;
import static task.manager.receiver.Command.*;

public class ReceiverService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiverService.class);
    private final LinkedHashSet<String> availableTasks;
    private final StringRedisTemplate template;

    @Autowired
    public ReceiverService(StringRedisTemplate template) {
        this.template = template;
        availableTasks = new LinkedHashSet<>();
    }

    public void receiveTask(String taskMessage) {
        if (taskMessage.startsWith(ADD.getCommandPrefix())) {
            String addTaskdescription = taskMessage.substring(ADD.getCommandPrefix().length());
            System.out.println("Added task with description: \n<" + addTaskdescription + ">");
            availableTasks.add(addTaskdescription);
        } else if (taskMessage.startsWith(TEST.getCommandPrefix())) {
            System.out.println("Added dummy tasks: \n");
            availableTasks.add("Learn AWS S3");
            availableTasks.add("Learn Spark");
            availableTasks.add("Learn Karate API");
            availableTasks.add("Learn Maven");
            availableTasks.add("Learn Java 8 features");
        } else if (taskMessage.startsWith(REMOVE.getCommandPrefix())) {
            String removeTaskDescription = taskMessage.substring(REMOVE.getCommandPrefix().length());
            if (availableTasks.contains(removeTaskDescription)) {
                System.out.println("Task with the following name was removed: \n<" + removeTaskDescription + ">");
                availableTasks.remove(removeTaskDescription);
            }
        } else if (taskMessage.startsWith(SUMMARY.getCommandPrefix())) {
            LOGGER.info("Sending summary...");
            String delimeter = "->";
            template.convertAndSend(SUMMARY_TOPIC.getRealTopicName(), delimeter + String.join("\n" + delimeter, availableTasks));
        } else {
            System.out.println("Can't recognize received command");
        }
    }
}
/*
ADD haircut
ADD car
ADD car2
REMOVE car2
SUMMARY


*/
