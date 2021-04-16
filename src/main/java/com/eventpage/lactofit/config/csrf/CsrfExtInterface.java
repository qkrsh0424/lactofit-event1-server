package com.eventpage.lactofit.config.csrf;

public interface CsrfExtInterface {
    int CSRF_TOKEN_EXPIRE_TIME = 30*60*1000;    // milliseconds
    int CSRF_COOKIE_EXPIRE_TIME = 30*60;    // seconds
}