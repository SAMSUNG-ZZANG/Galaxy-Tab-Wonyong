package com.example.sopt_seminar.domain.usecase

import com.example.sopt_seminar.domain.state.Result
import java.util.regex.Pattern
import javax.inject.Inject

class ValidateTextUseCase @Inject constructor() {
    operator fun invoke(idText: String, passwordText: String, nameText: String = "로그인"):Result{
        return when{
            nameText.isEmpty() -> Result.Fail("이름을 입력해주세요")
            !Pattern.matches("^[가-힣]*\$", nameText) -> Result.Fail("유효한 이름을 입력해주세요")
            idText.isEmpty() -> Result.Fail("아이디를 입력해주세요")
            !Pattern.matches("^[a-zA-Z0-9]*\$", idText) -> Result.Fail("유효한 아이디를 입력해주세요")
            passwordText.isEmpty() -> Result.Fail("비밀번호를 입력해주세요")
            !Pattern.matches("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#\$%^&*])(?=.*[0-9!@#\$%^&*]).{8,15}\$", passwordText) -> Result.Fail("유효한 비밀번호를 입력해주세요")
            else -> Result.Success(null)
        }
    }
}