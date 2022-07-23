package hello.hellospringreview;

import hello.hellospringreview.Controller.MemberController;
import hello.hellospringreview.domain.Member;
import hello.hellospringreview.repository.*;
import hello.hellospringreview.service.MemberService;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

//@SpringBootConfiguration 하면 컴파일 에러. 한 패키지에 이와 @SpringBootApplication 이 공존할 수 없다.
@Configuration
public class SpringConfig {

    private final DataSource dataSource;
    private final EntityManager em;

    public SpringConfig(DataSource dataSource, EntityManager em){
        this.dataSource = dataSource;
        this.em = em;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }
}
