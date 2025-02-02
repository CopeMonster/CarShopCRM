package me.alanton.carshopcrm.service.impl;

import lombok.RequiredArgsConstructor;
import me.alanton.carshopcrm.exception.impl.BusinessException;
import me.alanton.carshopcrm.exception.impl.BusinessExceptionReason;
import me.alanton.carshopcrm.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.USER_NOT_FOUND));
    }
}
