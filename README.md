# OAuth Login Spring Boot Project

스프링부트 환경 OAuth 로그인 예제 프로젝트입니다.
본 프로젝트에서는 구글, 페이스북, 네이버, 카카오 네 개의 OAuth 서비스 제공자에 대한 설정을 소개합니다.

## 블로그 바로가기

[\[Spring Boot\] OAuth2 소셜(구글, 페이스북, 네이버, 카카오) 로그인 완벽 가이드](https://deeplify.dev/spring/oauth2-social-login) 

## 기본 설정

1. 이 프로젝트는 데이터베이스와 연결되어야합니다. (실행하시려면 dbname: oauth_login_tutorial을 만들어주세요.)
2. 하단 application.yml 파일에 각 서비스 제공자 별 `client-id`와 `client-secret` 두 가지를 각각 등록해 주셔야합니다.

```yaml
spring:
  profiles.active: local
  # 데이터 소스 설정
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/oauth_login_tutorial?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false&allowPublicKeyRetrieval=true
    username: root
    password: root
    hikari:
      pool-name: jpa-hikari-pool
      maximum-pool-size: 5
      jdbc-url: ${spring.datasource.url}
      username: ${spring.datasource.username}
      password: ${spring.datasource.password}
      driver-class-name: ${spring.datasource.driver-class-name}
      data-source-properties:
        rewriteBatchedStatements: true
  # JPA 설정
  jpa:
    generate-ddl: true
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        hbm2ddl.import_files_sql_extractor: org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor
        current_session_context_class: org.springframework.orm.hibernate5.SpringSessionContext
        default_batch_fetch_size: ${chunkSize:100}
        jdbc.batch_size: 20
        order_inserts: true
        order_updates: true
        format_sql: true
  # Security OAuth
  security:
    oauth2.client:
      registration:
        google:
          clientId: '{구글 client-id}'
          clientSecret: '{구글 client-secret}'
          scope:
            - email
            - profile
        facebook:
          clientId: '{페이스북 client-id}'
          clientSecret: '{페이스북 client-secret}'
          scope:
            - email
            - public_profile
        naver:
          clientId: '{네이버 client-id}'
          clientSecret: '{네이버 client-secret}'
          clientAuthenticationMethod: post
          authorizationGrantType: authorization_code
          redirectUri: "{baseUrl}/{action}/oauth2/code/{registrationId}"
          scope:
            - nickname
            - email
            - profile_image
          clientName: Naver
        kakao:
          clientId: '{카카오 client-id}'
          clientSecret: '{카카오 client-secret}'
          clientAuthenticationMethod: post
          authorizationGrantType: authorization_code
          redirectUri: "{baseUrl}/{action}/oauth2/code/{registrationId}"
          scope:
            - profile_nickname
            - profile_image
            - account_email
          clientName: Kakao
      # Provider 설정
      provider:
        naver:
          authorizationUri: https://nid.naver.com/oauth2.0/authorize
          tokenUri: https://nid.naver.com/oauth2.0/token
          userInfoUri: https://openapi.naver.com/v1/nid/me
          userNameAttribute: response
        kakao:
          authorizationUri: https://kauth.kakao.com/oauth/authorize
          tokenUri: https://kauth.kakao.com/oauth/token
          userInfoUri: https://kapi.kakao.com/v2/user/me
          userNameAttribute: id

# cors 설정
cors:
  allowed-origins: 'http://localhost:3000'
  allowed-methods: GET,POST,PUT,DELETE,OPTIONS
  allowed-headers: '*'
  max-age: 3600

# jwt secret key 설정
jwt.secret: '8sknjlO3NPTBqo319DHLNqsQAfRJEdKsETOds'

# 토큰 관련 secret Key 및 RedirectUri 설정
app:
  auth:
    tokenSecret: 926D96C90030DD58429D2751AC1BDBBC
    tokenExpiry: 1800000
    refreshTokenExpiry: 604800000
  oauth2:
    authorizedRedirectUris:
      - http://localhost:3000/oauth/redirect
```
