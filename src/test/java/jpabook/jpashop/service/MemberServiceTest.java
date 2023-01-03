package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void join() throws Exception{
        //given
        Member member = new Member();
        member.setName("yepppi");

        //when
        Long memberId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(memberId));
    }

    @Test(expected = IllegalStateException.class)
    public void duplicatedMember() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("yepppi");

        Member member2 = new Member();
        member2.setName("yepppi");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("예외가 발생해야한다.");
    }
}