package pl.kania.clientAppBackend.aws.token;

import com.amazonaws.auth.BasicSessionCredentials;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AwsSessionTokenProvider {

    private final AwsSessionTokenAuthorizer sessionTokenAuthorizer;
    private final LoadingCache<AwsRole, BasicSessionCredentials> cache;
    private final RoleProperties roleProperties;

    public AwsSessionTokenProvider(AwsSessionTokenAuthorizer sessionTokenAuthorizer, RoleProperties roleProperties) {
        this.sessionTokenAuthorizer = sessionTokenAuthorizer;
        this.roleProperties = roleProperties;

        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(8, TimeUnit.HOURS)
                .build(getCacheLoader());
    }

    public BasicSessionCredentials getCredentials() {
        return Try.of(() -> cache.get(roleProperties.getRole()))
                .getOrElseThrow(e -> new IllegalStateException("Cannot get role from cache", e));
    }

    private CacheLoader<AwsRole, BasicSessionCredentials> getCacheLoader() {
        return new CacheLoader<>() {
            @Override
            public BasicSessionCredentials load(AwsRole role) throws Exception {
                return sessionTokenAuthorizer.getCredentialsForRole(role.getName(), role.getArn());
            }
        };
    }

}
