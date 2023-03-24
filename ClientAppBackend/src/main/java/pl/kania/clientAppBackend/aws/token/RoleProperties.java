package pl.kania.clientAppBackend.aws.token;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Setter
@Configuration
@PropertySource("classpath:secrets.properties")
public class RoleProperties {

    @Value("${aws.sts.role.arn}")
    private String roleArn;

    @Value("${aws.sts.role.name}")
    private String roleName;

    public AwsRole getRole() {
        return new AwsRole(roleArn, roleName);
    }
}
