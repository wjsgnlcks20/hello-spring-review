package hello.hellospringreview.service;

import hello.hellospringreview.domain.Member;
import hello.hellospringreview.repository.MemberRepository;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*

    MemberService 테스트에서는 h2 데이터베이스를 사용하지 않고 MemoryMemberRepository 구현체를
    직접 구현하여 사용하였는데, 이 때문에 DB에 멤버를 저장하고 꺼내더라도 테스트 레벨에서 생성했던
    Member 인스턴스와 find 메서드를 통해 찾은 인스턴스가 동일하게 된다.

    다만 Integration 테스트에서는 테이블에 데이터를 저장하고 다시 가져오는 과정에서 새로운 인스턴스를 생성해
    멤버 변수를 초기화하고 전달하기 때문에, 멤버 변수에 저장된 값은 같지만 인스턴스가 같을 수는 없다.

    따라서 테스트 코드를 작성할 때 인스턴스 자체를 비교하지 말고 멤버 변수를 비교한다.

 */

@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {

    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;

    @Test
    void 멤버등록(){
        Member member = new Member();
        member.setName("spring_test1");
        memberService.join(member);
        Member result = memberService.findOne(member.getId()).get();
        assertThat(result.getName()).isEqualTo(member.getName());
    }

    @Test
    void 중복(){
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("spring_test1");
        member2.setName("spring_test1");
        memberService.join(member1);

        IllegalStateException e = Assertions.assertThrows(IllegalStateException.class, () -> {memberService.join(member2);});
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

}
