package home.ecommerce.service;

import home.ecommerce.dto.UserDTO;
import home.ecommerce.entity.Role;
import home.ecommerce.entity.User;
import home.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;

    @Transactional
    public List<User> listAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public boolean addUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null)
            return false;

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CUSTOMER);
        userRepository.save(user);
        return true;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        modelMapper.map(user, userDTO);
        return userDTO;
    }

    @Transactional
    public void register(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = new User();
        modelMapper.map(userDTO, user);
        user.setRole(Role.CUSTOMER);
        user.setEnabled(false);

        Optional<User> saved = Optional.of(save(user));

        saved.ifPresent(u -> {
            try {
                String token = UUID.randomUUID().toString();
                verificationTokenService.save(saved.get(), token);

                emailService.sendHtmlEmail(u);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        saved.get();
    }

    public User add(UserDTO userDTO, Role role) {
        User user = new User();
        modelMapper.map(userDTO, user);
        user.setRole(role);
        return save(user);
    }

    public User update(UserDTO userDTO, Role role, Long id) {
        User user = new User();
        modelMapper.map(userDTO, user);
        user.setId(id);
        user.setRole(role);
        return save(user);
    }

    public void deleteUser(Long id) {
        Optional<User> deleted = userRepository.findById(id);
        deleted.ifPresent(userRepository::delete);
    }
}
