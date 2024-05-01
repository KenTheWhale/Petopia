package com.petopia.petopia.configurations;

import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.entity_models.Token;
import com.petopia.petopia.models.entity_models.TokenStatus;
import com.petopia.petopia.repositories.AccountRepo;
import com.petopia.petopia.repositories.TokenRepo;
import com.petopia.petopia.repositories.TokenStatusRepo;
import com.petopia.petopia.services.JWTService;
import com.petopia.petopia.services.TokenStatusService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;

    private final UserDetailsService userDetailsService;

    private final AccountRepo accountRepo;

    private final TokenRepo tokenRepo;

    private final TokenStatusService tokenStatusService;

    private final TokenStatusRepo tokenStatusRepo;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String jwt;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        String accountUsername = jwtService.extractUsername(jwt);
        if(accountUsername != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(accountUsername);
            Account account = accountRepo.findByUsername(userDetails.getUsername()).orElse(null);
            assert account != null;
            if(!account.getAccountStatus().getStatus().equals("active")) {
                filterChain.doFilter(request, response);
                return;
            }
            Token refreshToken = tokenRepo.findByAccount_IdAndTokenStatusStatusAndType(account.getId(), "active", "refresh").orElse(null);
            Token accessToken = tokenRepo.findByValue(jwt).orElse(null);
            assert refreshToken != null;
            assert accessToken != null;
            //check if refresh token is expired to disable it
            if(!jwtService.checkTokenIsValid(refreshToken.getValue())){
                tokenStatusService.applyExpiredStatus(refreshToken);
                tokenRepo.save(refreshToken);
            }

            //refresh if access token is expired
            if(!jwtService.checkTokenIsValid(accessToken.getValue())){
                //but refresh is still active
                if(jwtService.checkTokenIsValid(refreshToken.getValue())){
                    tokenStatusService.applyExpiredStatus(accessToken);
                    tokenRepo.save(accessToken);

                    TokenStatus active = tokenStatusRepo.findByStatus("active").orElse(null);
                    assert active != null;
                    tokenRepo.save(Token.builder()
                            .value(jwtService.generateAccessToken(account))
                            .type("access")
                            .tokenStatus(active)
                            .account(account)
                            .build());
                }
                // and refresh is expired too
                else{
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            //
            if(jwtService.checkTokenIsValid(jwt)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
