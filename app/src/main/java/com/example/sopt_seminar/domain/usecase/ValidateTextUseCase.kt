package com.example.sopt_seminar.domain.usecase

import java.util.regex.Pattern
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ValidateTextUseCase @Inject constructor() {
    operator fun invoke(
        idText: String,
        passwordText: String,
        nameText: String = "로그인"
    ): Flow<String?> = flow {
        when {
            nameText.isEmpty() -> emit("이름을 입력해주세요")
            !Pattern.matches("^[가-힣]*\$", nameText) -> emit("유효한 이름을 입력해주세요")
            idText.isEmpty() -> emit("아이디를 입력해주세요")
            !Pattern.matches("^[a-zA-Z0-9]*\$", idText) -> emit("유효한 아이디를 입력해주세요")
            passwordText.isEmpty() -> emit("비밀번호를 입력해주세요")
            !Pattern.matches(
                "^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#\$%^&*])(?=.*[0-9!@#\$%^&*]).{8,15}\$",
                passwordText
            ) -> emit("유효한 비밀번호를 입력해주세요")
            else -> emit(null)
        }
    }
}