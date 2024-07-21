package gift.service;

import gift.entity.MemberEntity;
import gift.domain.MemberDTO;
import gift.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberEntity authenticateToken(MemberDTO memberDTO) {
        MemberEntity foundMember = memberRepository.findByEmail(memberDTO.getEmail());

        if (foundMember == null || !memberDTO.getPassword().equals(foundMember.getPassword())) {
            throw new IllegalArgumentException("로그인 정보가 올바르지 않습니다.");
        }

        return foundMember;
    }

    @Transactional
    public void save(MemberDTO memberDTO) {
        if (existsByEmail(memberDTO.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        // DTO to Entity
        MemberEntity memberEntity = new MemberEntity(memberDTO.getEmail(), memberDTO.getPassword());
        memberRepository.save(memberEntity);
    }


    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
}
