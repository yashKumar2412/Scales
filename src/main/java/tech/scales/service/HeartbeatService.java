package tech.scales.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;
import java.util.ArrayList;

@Service
public class HeartbeatService {

    private static final Logger logger = LoggerFactory.getLogger(HeartbeatService.class);
    private final SqsClient sqsClient;

    @Value("${config.heartbeat-queue.type}")
    private String queueType;

    @Value("${config.heartbeat-queue.url}")
    private String queueUrl;

    @Value("${config.max-servers}")
    private String maxServers;

    public HeartbeatService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public List<String> checkHeartbeatQueue() {
        logger.info("Reading from {} HeartbeatQueue: {}", queueType, queueUrl);
        List<String> backendServers = new ArrayList<>();

        boolean moreMessages = true;

        while (moreMessages) {
            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .maxNumberOfMessages(Integer.getInteger(maxServers))
                    .build();

            List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();

            if (messages.isEmpty()) {
                moreMessages = false;
            } else {
                for (Message message : messages) {
                    try {
                        backendServers.add(message.body());

                        sqsClient.deleteMessage(builder -> builder
                                .queueUrl(queueUrl)
                                .receiptHandle(message.receiptHandle())
                        );

                        logger.info("Deleted message from SQS: {}", message.body());
                    } catch (Exception e) {
                        logger.error("Failed to delete message: {}", e.getMessage());
                    }
                }
            }
        }

        return backendServers;
    }
}
