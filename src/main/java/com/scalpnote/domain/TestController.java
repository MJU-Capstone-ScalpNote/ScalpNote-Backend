package com.scalpnote.domain;

import com.scalpnote.domain.user.domain.User;
import com.scalpnote.global.payload.Message;
import com.scalpnote.global.payload.ResponseCustom;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Hello!!");
    }

    @GetMapping("/test2")
    public ResponseCustom<?> test2() {
        return ResponseCustom.OK(new Message("a"));
    }

}
