package hello.hellospringreview.service;

import hello.hellospringreview.domain.Member;
import hello.hellospringreview.repository.MemberRepository;
import hello.hellospringreview.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemberServiceTest {

    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    MemberService memberService = new MemberService(memberRepository);

//    @BeforeEach
//    void beforeEach(){
//        memberRepository = new MemoryMemberRepository();
//        memberService = new MemberService(memberRepository);
//    }
    // 아직도 beforeEach는 왜 해줘야 하는 건지 의문. 전혀 차이가 없어보이는데.
    // AfterEach 없이 이것만 하면 또 중복(누적)문제 해결 안됨.

    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    public void 회원가입() throws Exception {
        Member member1 = new Member();
        member1.setName("spring1");
        Long saveId = memberService.join(member1);
        Member findMember = memberService.findOne(saveId).get();
        assertThat(findMember).isEqualTo(member1);
    }

    @Test
    public void 중복() throws Exception {
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("spring1");
        member2.setName("spring1");
        memberService.join(member1);

        IllegalStateException e = Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }


    @Test
    public void findMembers(){
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("spring1");
        member2.setName("spring2");
        memberService.join(member1);
        memberService.join(member2);
        List<Member> result = memberService.findMembers();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void findOne(){
        Member member1 = new Member();
        member1.setName("spring1");
        memberService.join(member1);
        Member result = memberService.findOne(member1.getId()).get();
        assertThat(result.getId()).isEqualTo(member1.getId());
    }
}
