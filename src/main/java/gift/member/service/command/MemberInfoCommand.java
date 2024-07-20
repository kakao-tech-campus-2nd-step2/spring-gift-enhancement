package gift.member.service.command;

public record MemberInfoCommand(
        String username,
        String password
) {
}
