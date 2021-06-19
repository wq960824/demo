package com.example.demo.config.shiro;

import com.example.demo.entity.UserBase;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;


public class PasswordHelper {

    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private static final int hashIterations = 1024;

    public static void encryptPassword(UserBase user) {

        user.setSalt(randomNumberGenerator.nextBytes().toBase64());

        String newPassword = new SimpleHash(
                "SHA-256",
                user.getPass(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toBase64();

        user.setPass(newPassword);
    }

}
