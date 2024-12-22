package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtils {
	 @Autowired
	    private MessageSource messageSource;

	    /**
	     * キーからメッセージを取得する.
	     * 
	     * @param key メッセージのキー
	     * @param args プレースホルダー用の変数
	     * @return メッセージ
	     */
	    public String get(String key, Object... args) {
	        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
	    }
}
