package gift.permission.controller;

import gift.global.dto.ApiResponseDto;
import gift.permission.dto.LoginRequestDto;
import gift.permission.dto.RegistrationRequestDto;
import gift.permission.service.PermissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 로그인 또는 회원가입을 통해 이메일과 비밀번호를 받아서 토큰을 반환해주는 컨트롤러
@RestController
@RequestMapping("/api")
public class PermissionApiController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionApiController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    // 회원 가입에 대한 핸들러
    @PostMapping("/registration")
    public ApiResponseDto register(
        @RequestBody @Valid RegistrationRequestDto registrationRequestDto) {
        // service로부터 비즈니스 로직(회원가입)을 완료하고 끝.
        // 보통 회원가입을 마치면 로그인 페이지로 돌려보내므로 이를 구현하는 데에 굳이 Token을 반환할 필요는 없다고 판단.
        permissionService.register(registrationRequestDto);
        return ApiResponseDto.succeed();
    }

    // 유저 로그인에 대한 핸들러
    @PostMapping("/users/login")
    public ApiResponseDto userLogin(@RequestBody LoginRequestDto loginRequestDto) {
        return ApiResponseDto.succeed(permissionService.userLogin(loginRequestDto));
    }

    // 어드민 로그인에 대한 핸들러
    @PostMapping("/admin/login")
    public ApiResponseDto adminLogin(@RequestBody LoginRequestDto loginRequestDto) {
        return ApiResponseDto.succeed(permissionService.adminLogin(loginRequestDto));
    }
}