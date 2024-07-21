package gift.member.application;

import gift.exception.type.DuplicateNameException;
import gift.exception.type.NotFoundException;
import gift.member.application.command.MemberEmailUpdateCommand;
import gift.member.application.command.MemberJoinCommand;
import gift.member.application.command.MemberLoginCommand;
import gift.member.application.command.MemberPasswordUpdateCommand;
import gift.member.domain.Member;
import gift.member.domain.MemberRepository;
import gift.member.presentation.request.ResolvedMember;
import gift.wishlist.domain.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final WishlistRepository wishlistRepository;

    public MemberService(MemberRepository memberRepository, WishlistRepository wishlistRepository) {
        this.memberRepository = memberRepository;
        this.wishlistRepository = wishlistRepository;
    }

    public Long join(MemberJoinCommand command) {
        return memberRepository.save(command.toMember()).getId();
    }

    public Long login(MemberLoginCommand command) {
        return memberRepository
                .findByEmailAndPassword(command.email(), command.password())
                .orElseThrow(() -> new NotFoundException("해당 회원이 존재하지 않습니다."))
                .getId();
    }

    @Transactional
    public void updateEmail(MemberEmailUpdateCommand command, ResolvedMember resolvedMember) {
        Member member = getMember(resolvedMember);

        if (memberRepository.existsByEmail(command.email()))
            throw new DuplicateNameException("이미 사용중인 이메일입니다.");

        member.updateEmail(command.email());
    }

    @Transactional
    public void updatePassword(MemberPasswordUpdateCommand command, ResolvedMember resolvedMember) {
        memberRepository.findById(resolvedMember.id())
                .orElseThrow(() -> new NotFoundException("해당 회원이 존재하지 않습니다."))
                .updatePassword(command.password());
    }

    public MemberServiceResponse findById(Long memberId) {
        return memberRepository.findById(memberId)
                .map(MemberServiceResponse::from)
                .orElseThrow(() -> new NotFoundException("해당 회원이 존재하지 않습니다."));
    }

    public List<MemberServiceResponse> findAll() {
        return memberRepository.findAll()
                .stream().map(MemberServiceResponse::from).toList();
    }

    @Transactional
    public void delete(ResolvedMember resolvedMember) {
        Member member = getMember(resolvedMember);

        wishlistRepository.deleteAllByMemberId(member.getId());
        memberRepository.delete(member);
    }

    private Member getMember(ResolvedMember resolvedMember) {
        return memberRepository.findById(resolvedMember.id())
                .orElseThrow(() -> new NotFoundException("해당 회원이 존재하지 않습니다."));
    }
}
