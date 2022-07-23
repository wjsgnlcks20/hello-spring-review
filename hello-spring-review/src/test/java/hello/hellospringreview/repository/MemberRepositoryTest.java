package hello.hellospringreview.repository;

import hello.hellospringreview.domain.Member;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemberRepositoryTest {

    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void save(){
        Member member = new Member();
        member.setName("spring1");
        Member result = memberRepository.save(member);
        assertThat(result).isEqualTo(member);
    }

    @Test
    void findById(){
        Member member = new Member();
        member.setName("spring1");
        memberRepository.save(member);
        Member result = memberRepository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    void findByName(){
        Member member = new Member();
        member.setName("spring1");
        memberRepository.save(member);
        Member result = memberRepository.findByName(member.getName()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    void findAll(){
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("spring1");
        member2.setName("spring2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(2);
    }

}
