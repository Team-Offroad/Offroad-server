package site.offload.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    //JWT 사용하지 않는 경우

    private static final String SWAGGER_DESCRIPTION = """
    Offroad API Docs
    
    인증/인가가 필요한 API에는 Swagger 상에 명시되어 있지 않아도,
    Header에 Authorization: Bearer {access_token}을 추가해주세요.
    
    401 에러가 발생할 경우, 토큰이 만료되었거나, 잘못된 토큰일 수 있습니다. -> 토큰을 재발급 받아주세요.
    그럼에도 동작하지 않는 경우, -> 서버에게 문의해주세요.
    
    500 에러가 발생할 경우, 서버에 문제가 있을 수 있습니다. -> 서버에게 문의해주세요.
    
    """;
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Offroad API Swagger") // API의 제목
                .description(SWAGGER_DESCRIPTION) // API에 대한 설명
                .version("v1"); // API의 버전
    }
}
