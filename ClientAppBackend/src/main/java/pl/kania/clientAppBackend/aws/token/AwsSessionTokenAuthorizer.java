package pl.kania.clientAppBackend.aws.token;

import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
class AwsSessionTokenAuthorizer {

    private static final int ONE_HOUR_IN_SECONDS = 3600;
    private final AWSSecurityTokenService stsClient;

    AwsSessionTokenAuthorizer(@Value("${aws.region}") String region) {
        stsClient = AWSSecurityTokenServiceClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withRegion(region)
                .build();
    }

    BasicSessionCredentials getCredentialsForRole(String roleSessionName, String roleArn) {
        return Try.of(() -> new AssumeRoleRequest()
                        .withRoleArn(roleArn)
                        .withRoleSessionName(roleSessionName)
                        .withDurationSeconds(1 * ONE_HOUR_IN_SECONDS))
                .map(stsClient::assumeRole)
                .map(AssumeRoleResult::getCredentials)
                .map(sessionCredentials -> new BasicSessionCredentials(
                        sessionCredentials.getAccessKeyId(),
                        sessionCredentials.getSecretAccessKey(),
                        sessionCredentials.getSessionToken()))
                .getOrElseThrow(e -> new IllegalStateException("Error assuming role " + roleSessionName, e));
    }
}
