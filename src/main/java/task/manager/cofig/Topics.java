package task.manager.cofig;

public enum Topics {
    TASK_TOPIC("taskTopic"),
    SUMMARY_TOPIC("summaryTopic");

    private final String realTopicName;

    Topics(String realTopicName) {
        this.realTopicName = realTopicName;
    }

    public String getRealTopicName() {
        return realTopicName;
    }
}
