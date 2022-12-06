package home.ecommerce.service.security;

import home.ecommerce.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class DbUserDetails implements UserDetails {
    private final User dbUser;

    public DbUserDetails(User dbUser) {
        this.dbUser = dbUser;
    }

    @Override
    public String getUsername() {
        return dbUser.getUsername();
    }

    @Override
    public String getPassword() {
        return dbUser.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.<GrantedAuthority>singletonList(
                new SimpleGrantedAuthority(dbUser.getRole().getAuthority())
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
