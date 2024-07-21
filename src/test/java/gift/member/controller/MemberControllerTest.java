//package gift.member.controller;
//
//import gift.member.model.Member;
//import gift.member.repository.MemberRepository;
//import gift.member.service.MemberService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class MemberControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private MemberRepository memberRepository;
//
//    @MockBean
//    private MemberService memberService;
//
//    private Member member;
//
//    @BeforeEach
//    void setUp() {
//        member = new Member("test@example.com", "password");
//    }
//
//    @Test
//    @DisplayName("GET /members/{memberId} - 성공")
//    public void getMemberById_success() throws Exception {
//        // Given
//        Mockito.when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
//
//        // When & Then
//        mockMvc.perform(get("/members/{memberId}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email").value(member.getEmail()))
//                .andExpect(jsonPath("$.id").value(member.getId()));
//    }
//
//    @Test
//    @DisplayName("GET /members/{memberId} - 실패")
//    public void getMemberById_notFound() throws Exception {
//        // Given
//        Mockito.when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        // When & Then
//        mockMvc.perform(get("/members/{memberId}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string(containsString("회원을 찾을 수 없습니다.")));
//    }
//}
