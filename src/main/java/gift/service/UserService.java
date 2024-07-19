package gift.service;

import gift.DTO.User.UserRequest;
import gift.DTO.User.UserResponse;
import gift.domain.User;
import gift.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    /*
     * User의 정보를 오름차순으로 조회하는 로직
     */
    public Page<UserResponse> findAllASC(int page, int size, String field){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc(field));
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorts));

        Page<User> users = userRepository.findAll(pageable);

        return users.map(UserResponse::new);
    }
    /*
     * User의 정보를 오름차순으로 조회하는 로직
     */
    public Page<UserResponse> findAllDESC(int page, int size, String field){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc(field));
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorts));

        Page<User> users = userRepository.findAll(pageable);

        return users.map(UserResponse::new);
    }
    /*
     * User의 정보를 userId를 기준으로 찾는 로직
     */
    public UserResponse loadOneUser(String userId){
        User user = userRepository.findByUserId(userId);
        return new UserResponse(user);
    }
    /*
     * 위와 동일, 오버로딩
     */
    public UserResponse loadOneUser(Long id){
        User user = userRepository.findById(id).orElseThrow(NullPointerException::new);
        return new UserResponse(user);
    }
    /*
     * User의 정보를 저장하는 로직
     */
    @Transactional
    public void createUser(UserRequest userRequest){
        userRepository.save(new User(
                userRequest.getUserId(),
                userRequest.getEmail(),
                userRequest.getPassword()
        ));
    }
    @Transactional
    public void delete(Long id){
        userRepository.deleteById(id);
    }
    /*
     * user 정보를 업데이트하는 로직
     */
    @Transactional
    public void update(UserRequest userRequest){
        User byUserId = userRepository.findByUserId(userRequest.getUserId());
        byUserId.updateEntity(userRequest.getEmail(), userRequest.getPassword());
    }
    /*
     * userId의 중복 여부를 확인하는 로직
     */
    public boolean isDuplicate(UserRequest userRequest){
        return userRepository.existsByUserId(userRequest.getUserId());
    }
    /*
     * 위와 동일 ( overloading )
     */
    public boolean isDuplicate(Long id){
        return userRepository.existsById(id);
    }
    /*
     * 로그인을 위한 확인을 해주는 로직
     */
    public boolean login(UserRequest userRequest){
        return userRepository.existsByUserIdAndPassword(userRequest.getUserId(), userRequest.getPassword());
    }
    /*
     * user 정보를 삭제하는 로직
     */
}
