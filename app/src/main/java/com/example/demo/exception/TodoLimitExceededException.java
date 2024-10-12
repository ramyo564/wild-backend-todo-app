package com.example.demo.exception;

public class TodoLimitExceededException extends RuntimeException {

    public TodoLimitExceededException() {
        super("할 일은 10개 이상 등록할 수 없습니다.\n중요하지 않은 할 일을 삭제하고 등록해주세요.");
    }
}
