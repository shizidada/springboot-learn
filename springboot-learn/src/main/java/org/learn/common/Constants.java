package org.learn.common;

public interface Constants {

    class JWT {
        public static final String TOKEN_HEADER = "Authorization";
        public static final String TOKEN_PREFIX = "Bearer ";

        public static final String SECRET = "jwt_secret";
        public static final String ISS = "ISS";

        // 角色的key
        public static final String ROLE_CLAIMS = "role";

        // 角色的基本信息
        public static final String ROLE_INFO = "role_info";

        // 过期时间
        public static final long EXPIRATION = 60 * 60 * 24;

        // 选择了记住我之后 过期时间
        public static final long EXPIRATION_REMEMBER = 60 * 60 * 24 * 7;
    }
}
