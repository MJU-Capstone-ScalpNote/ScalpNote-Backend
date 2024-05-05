package com.scalpnote.global;

import com.scalpnote.domain.user.domain.User;
import com.scalpnote.global.error.DefaultAuthenticationException;
import com.scalpnote.global.error.DefaultException;
import com.scalpnote.global.payload.ErrorCode;
import org.springframework.util.Assert;

import java.util.Optional;

public class DefaultAssert extends Assert {
    public static void isTrue(boolean value) {
        if (!value) {
            throw new DefaultException(ErrorCode.INVALID_CHECK);
        }
    }


    public static void isAuthentication(String message) {
        throw new DefaultAuthenticationException(message);
    }

    public static void isAuthentication(boolean value) {
        if (!value) {
            throw new DefaultAuthenticationException(ErrorCode.INVALID_AUTHENTICATION);
        }
    }

    public static void isOptionalPresent(Optional<User> user) {
    }
}
