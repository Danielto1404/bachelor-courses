package tokens;

import visitors.TokenVisitor;

public interface Token {
    void accept(TokenVisitor visitor);
}
