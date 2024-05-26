package com.scalpnote.domain.user.application;

import com.scalpnote.domain.user.domain.User;
import com.scalpnote.domain.user.domain.repository.UserRepository;
import com.scalpnote.domain.user.dto.HairConditionReq;
import com.scalpnote.global.config.security.token.UserPrincipal;
import com.scalpnote.global.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public Message model6Insert(UserPrincipal userPrincipal, HairConditionReq hairConditionReq) {

        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(NullPointerException::new);

        user.updateScalpCondition(hairConditionReq.getHairCondition());

        return Message.builder()
                .message("두피 진단 결과가 저장되었습니다.")
                .build();
    }
}
