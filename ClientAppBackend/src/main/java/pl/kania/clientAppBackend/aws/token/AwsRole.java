package pl.kania.clientAppBackend.aws.token;

import lombok.Value;

@Value
public class AwsRole {
    String arn;
    String name;
}
