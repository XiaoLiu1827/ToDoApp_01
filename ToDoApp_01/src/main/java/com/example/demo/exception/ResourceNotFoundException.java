package com.example.demo.exception;

public class ResourceNotFoundException extends RuntimeException{
	// リソースIDなど、追加のエラーデータを格納するフィールド
    private Long resourceId;

    // エラーメッセージを指定するコンストラクタ
    public ResourceNotFoundException(String message) {
        super(message);
    }

    // エラーメッセージとリソースIDを指定するコンストラクタ
    public ResourceNotFoundException(String message, Long resourceId) {
        super(message);
        this.resourceId = resourceId;
    }

    // エラーメッセージと原因となった例外を指定するコンストラクタ
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // リソースIDを取得するためのメソッド
    public Long getResourceId() {
        return resourceId;
    }
}
