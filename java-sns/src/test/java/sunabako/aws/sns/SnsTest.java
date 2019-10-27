package sunabako.aws.sns;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.Topic;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SnsTest {
    Optional<URI> getEndpoint() {
        String ep = Optional.ofNullable(System.getenv("SNS_ENDPOINT"))
                .orElse("http://127.0.0.1:9911");
        try {
            return Optional.of(new URI(ep));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Test
    void client() throws URISyntaxException {
        SnsClient client = SnsClient.builder()
                .region(Region.AP_NORTHEAST_1)
                .endpointOverride(getEndpoint().get())
                .build();
        assertTrue(Optional.ofNullable(client).isPresent());
    }
    @Test
    void topics() {
        SnsClient client = SnsClient.builder()
                .region(Region.AP_NORTHEAST_1)
                .endpointOverride(getEndpoint().get())
                .build();
        assertEquals(1, client.listTopics().topics().size());
        Topic topic = client.listTopics().topics().get(0);
        assertEquals(Topic.class, topic.getClass());
        assertEquals("arn:aws:sns:ap-northeast-1:1465414804035:test1", topic.topicArn());
    }

    @Test
    void publish() {
        SnsClient client = SnsClient.builder()
                .region(Region.AP_NORTHEAST_1)
                .endpointOverride(getEndpoint().get())
                .build();
        Topic topic = client.listTopics().topics().get(0);
        PublishRequest request = PublishRequest.builder()
                .topicArn(topic.topicArn())
                .message("Hello by JUnit")
                .build();
        PublishResponse res = client.publish(request);
        assertEquals(PublishResponse.class, res.getClass());
        System.out.println("===========");
        System.out.println(res.messageId());
        System.out.println("===========");
    }
}
