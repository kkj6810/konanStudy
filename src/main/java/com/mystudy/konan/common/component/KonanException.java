package com.mystudy.konan.common.component;

/**
 * Docruzer 에러메세지 캡쳐용 Exception Class.
 *
 * @author Jinhoo.Jang
 * @since 2013.08.09
 * @version 1.0
 * Copyright ⓒ Konan Technology. All Right Reserved
 * ==================================================
 */
@SuppressWarnings("serial")
public class KonanException extends Exception {

    public KonanException () {
        super();
    }

    public KonanException (String msg) {
        super(msg);
    }
}