package com.scalpnote.domain.user.application;

import com.scalpnote.domain.user.domain.Model6Result;
import com.scalpnote.domain.user.domain.User;
import com.scalpnote.domain.user.domain.repository.Model6ResultRepository;
import com.scalpnote.domain.user.domain.repository.UserRepository;
import com.scalpnote.domain.user.dto.HairConditionReq;
import com.scalpnote.domain.user.dto.MypageRes;
import com.scalpnote.global.config.security.token.UserPrincipal;
import com.scalpnote.global.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Model6ResultRepository model6ResultRepository;


    @Transactional
    public Message model6Insert(UserPrincipal userPrincipal, HairConditionReq hairConditionReq) {

        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(NullPointerException::new);

        Model6Result model6Result = Model6Result.builder()
                .scalpCondition(hairConditionReq.getHairCondition())
                .user(user)
                .build();

        model6ResultRepository.save(model6Result);


        return Message.builder()
                .message("두피 진단 결과가 저장되었습니다.")
                .build();
    }

    @Transactional
    public MypageRes findMypage(UserPrincipal userPrincipal) {

        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(NullPointerException::new);
        List<Model6Result> model6ResultList = model6ResultRepository.findByUser(user);

        return MypageRes.toDto(user, model6ResultList);
    }
}
