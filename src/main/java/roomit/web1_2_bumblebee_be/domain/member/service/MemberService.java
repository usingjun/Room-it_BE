package roomit.web1_2_bumblebee_be.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomit.web1_2_bumblebee_be.domain.member.entity.Member;
import roomit.web1_2_bumblebee_be.domain.member.exception.MemberNotFound;
import roomit.web1_2_bumblebee_be.domain.member.repository.MemberRepository;
import roomit.web1_2_bumblebee_be.domain.member.request.MemberRegisterRequest;
import roomit.web1_2_bumblebee_be.domain.member.request.MemberUpdateRequest;
import roomit.web1_2_bumblebee_be.domain.member.response.MemberResponse;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void signupMember(MemberRegisterRequest memberRequest) {
        Member member = Member.builder()
                .nickName(memberRequest.getNickName())
                .age(memberRequest.getAge())
                .sex(memberRequest.getSex())
                .pwd(memberRequest.getPwd())
                .email(memberRequest.getEmail())
                .role(memberRequest.getRole())
                .phoneNumber(memberRequest.getPhoneNumber())
                .build();

        memberRepository.save(member);
    }

    public MemberResponse read(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);

        return new MemberResponse(member);
    }

    public MemberResponse update(Long memberId, MemberUpdateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);

        member.changeAge(request.getAge());
        member.changeEmail(request.getEmail());
        member.changeRole(request.getRole());
        member.changePhoneNumber(request.getPhoneNumber());
        member.changePwd(request.getPwd());
        memberRepository.save(member);

        return new MemberResponse(member);
    }

    public void delete(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);

        memberRepository.delete(member);
    }
}