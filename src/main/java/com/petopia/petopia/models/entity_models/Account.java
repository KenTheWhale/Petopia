package com.petopia.petopia.models.entity_models;

import com.petopia.petopia.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`account`")
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String password;

    private String email;

    private String avatarLink;

    @ManyToOne
    @JoinColumn(name = "`status_id`")
    private AccountStatus accountStatus;

    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    private List<Token> tokenList;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private User user;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private ServiceProvider serviceProvider;

    @OneToOne(mappedBy = "account")
    @ToString.Exclude
    private Shop shop;

    @OneToOne(mappedBy = "account")
    @ToString.Exclude
    private ServiceCenter serviceCenter;

    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    private List<AccountImage> accountImageList;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
