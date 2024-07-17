package gift.security.hash;

import org.mindrot.jbcrypt.BCrypt;

public final class Hasher {

    private Hasher() {}

    public static String hashPassword(String plainPw) {
        return BCrypt.hashpw(plainPw, BCrypt.gensalt());
    }
}
