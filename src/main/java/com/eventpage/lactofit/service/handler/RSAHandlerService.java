package com.eventpage.lactofit.service.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RSAHandlerService {
    @Value("${app.ciper.publickey}")
    private String publicKey;
    @Value("${app.ciper.privatekey}")
    private String privateKey;
    
    public String getEncryptRsaStr(String text) {
        try {
            PublicKey rePublicKey = RSAUtil.getPublicKeyFromBase64Encrypted(publicKey);
            String encryptedRe = RSAUtil.encryptRSA(text, rePublicKey);
            return encryptedRe;
        } catch (NoSuchAlgorithmException e) {
            log.error("== ERROR RSAHandlerService : getEncrypt => {} ==", "NoSuchAlgorithmException");
        } catch (InvalidKeySpecException e) {
            log.error("== ERROR RSAHandlerService : getEncrypt => {} ==", "InvalidKeySpecException");
        } catch (InvalidKeyException e) {
            log.error("== ERROR RSAHandlerService : getEncrypt => {} ==", "InvalidKeyException");
        } catch (NoSuchPaddingException e) {
            log.error("== ERROR RSAHandlerService : getEncrypt => {} ==", "NoSuchPaddingException");
        } catch (BadPaddingException e) {
            log.error("== ERROR RSAHandlerService : getEncrypt => {} ==", "BadPaddingException");
        } catch (IllegalBlockSizeException e) {
            log.error("== ERROR RSAHandlerService : getEncrypt => {} ==", "IllegalBlockSizeException");
        }
        return text;
    }

    public String getDecryptRsaStr(String text) {
        try {
            PrivateKey rePrivateKey = RSAUtil.getPrivateKeyFromBase64Encrypted(privateKey);
            String decryptedRe = "";
            if(!text.equals("") && text!=null){
                decryptedRe = RSAUtil.decryptRSA(text, rePrivateKey);   
            }
            return decryptedRe;
        } catch (NoSuchAlgorithmException e) {
            log.error("== ERROR RSAHandlerService : getDecrypt => {} ==", "NoSuchAlgorithmException");
        } catch (InvalidKeySpecException e) {
            log.error("== ERROR RSAHandlerService : getDecrypt => {} ==", "InvalidKeySpecException");
        } catch (InvalidKeyException e) {
            log.error("== ERROR RSAHandlerService : getDecrypt => {} ==", "InvalidKeyException");
        } catch (NoSuchPaddingException e) {
            log.error("== ERROR RSAHandlerService : getDecrypt => {} ==", "NoSuchPaddingException");
        } catch (BadPaddingException e) {
            log.error("== ERROR RSAHandlerService : getDecrypt => {} ==", "BadPaddingException");
        } catch (IllegalBlockSizeException e) {
            log.error("== ERROR RSAHandlerService : getDecrypt => {} ==", "IllegalBlockSizeException");
        } catch (UnsupportedEncodingException e) {
            log.error("== ERROR RSAHandlerService : getDecrypt => {} ==", "UnsupportedEncodingException");
        }
        return text;
    }
}
