package de.ait_tr.g_36.exception_nandling.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


// 2 способ обработки ошибок

// ПЛЮС - быстро и удобно без лишнего кода создаём
// глобальный обработчик данного исключения
// МИНУС - пользователь не видит ДЕТАЛЬНОГО сообщения об ошибке,
// следовательно, не понимает причин её возникновения
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SecondTestException extends RuntimeException {

    public SecondTestException(String message) {
        super(message);
    }

}
