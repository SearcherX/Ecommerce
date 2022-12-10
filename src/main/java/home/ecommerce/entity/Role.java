package home.ecommerce.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CUSTOMER,
    SELLER,
    ADMIN;


    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
