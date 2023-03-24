package pl.kania.clientAppBackend.aws.token;

import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;
import software.amazon.awssdk.services.sts.model.AssumeRoleResponse;

@Service
class AwsSessionTokenAuthorizer {

    private static final int ONE_HOUR_IN_SECONDS = 3600;
    private final StsClient stsClient;

    AwsSessionTokenAuthorizer(@Value("${aws.region}") String region) {
        stsClient = StsClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create())
                .region(Region.of(region))
                .build();
    }

    AwsBasicCredentials getCredentialsForRole(String roleSessionName, String roleArn) {
        return Try.of(() -> AssumeRoleRequest.builder()
                        .roleArn(roleArn)
                        .roleSessionName(roleSessionName)
                        .durationSeconds(1 * ONE_HOUR_IN_SECONDS)
                        .build())
                .map(stsClient::assumeRole)
                .map(AssumeRoleResponse::credentials)
                .map(sessionCredentials -> AwsBasicCredentials.create(
                        sessionCredentials.accessKeyId(),
                        sessionCredentials.secretAccessKey()))
                .getOrElseThrow(e -> new IllegalStateException("Error assuming role " + roleSessionName, e));
    }
}
