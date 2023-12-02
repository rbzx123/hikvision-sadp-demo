package com.demo;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.burgstaller.okhttp.AuthenticationCacheInterceptor;
import com.burgstaller.okhttp.CachingAuthenticatorDecorator;
import com.burgstaller.okhttp.digest.CachingAuthenticator;
import com.burgstaller.okhttp.digest.Credentials;
import com.burgstaller.okhttp.digest.DigestAuthenticator;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ISAPIService {

    private final static String HIK_DEFAULT_IP = "192.168.1.64";

    XmlMapper xmlMapper = new XmlMapper();
    OkHttpClient client = noAuthClient();
    HikUtils hikUtils = new HikUtils(client);

    public void activateByHttp1() throws Exception {


        String password = "abc123456";

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4);
        keyPairGenerator.initialize(spec);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        CompletableFuture<String> activateTask = CompletableFuture.supplyAsync(() -> {
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            byte[] pubBytes = ByteUtils.bigintToArr(publicKey.getModulus(), 128);
            String pubHex = ByteUtils.toBase64Hex(pubBytes);
            String xml = "<PublicKey><key>" + pubHex + "</key></PublicKey>";
            String url = "http://" + HIK_DEFAULT_IP + ISAPI.ACTIVATE_CHALLENGE.getURL();
            String respXml = null;
            try {
                respXml = hikUtils.post(url, xml);
                return respXml;
            } catch (Exception e) {

                throw new RuntimeException(e);
            }
        }).thenApply(xml -> {
            try {
                ActivateChallenge challenge = xmlMapper.readValue(xml, ActivateChallenge.class);
                byte[] bytes1 = ByteUtils.cryptToBytes(challenge.getKey());
                Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
                byte[] bytes2 = cipher.doFinal(bytes1);
                return new String(bytes2);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).thenApply(random -> {
            try {
                String respXml = activateByHttp2(random, password);
                return respXml;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        try {
            String respXml = activateTask.get();
            HikHttpResponse hikHttpResponse = xmlMapper.readValue(respXml, HikHttpResponse.class);
            if (hikHttpResponse.getStatusCode() == 1 && hikHttpResponse.getStatusString().equalsIgnoreCase("ok") && hikHttpResponse.getSubStatusCode().equalsIgnoreCase("ok")) {
                System.out.println("ok");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private String activateByHttp2(String random, String password) {
        byte[] bytes = ByteUtils.bigintToArr(new BigInteger(random, 16), 16);
        AES aes = new AES(Mode.ECB, Padding.ZeroPadding, bytes);
        byte[] passByte = new byte[16];
        for (int i = 0; i < password.length(); i++) {
            passByte[i] = (byte) password.charAt(i);
        }
        password = new String(passByte);
        byte[] data1 = aes.encrypt(random.substring(0, 16));
        byte[] data2 = aes.encrypt(password);
        byte[] data = new byte[32];
        System.arraycopy(data1, 0, data, 0, 16);
        System.arraycopy(data2, 0, data, 16, 16);
        String bs64 = ByteUtils.toBase64Hex(data);
        String xml = "<ActivateInfo><password>" + bs64 + "</password></ActivateInfo>";
        String url = "http://" + HIK_DEFAULT_IP + ISAPI.ACTIVATE_DEVICE.getURL();
        return hikUtils.put(url, xml);
    }

    public OkHttpClient authClient(String username, String password) {
        DigestAuthenticator authenticator = new DigestAuthenticator(new Credentials(username, password));
        Map<String, CachingAuthenticator> authCache = new HashMap<>();
        return new OkHttpClient.Builder().authenticator(new CachingAuthenticatorDecorator(authenticator, authCache)).addInterceptor(new AuthenticationCacheInterceptor(authCache)).build();
    }

    public OkHttpClient noAuthClient() {
        return new OkHttpClient.Builder().build();
    }

    public void queryDeviceInfo(String boxCode, String ip, String password) throws Exception {
        String url = String.format("http://%s%s", ip, ISAPI.DEVICE_INFO.getURL());
        OkHttpClient okHttpClient = authClient("admin", "abc123456");
        hikUtils = new HikUtils(okHttpClient);

        String xml = hikUtils.get(url);
        System.out.println(xml);
        DeviceInfo deviceInfo = xmlMapper.readValue(xml, DeviceInfo.class);
        System.out.println(JSONUtil.toJsonPrettyStr(deviceInfo));
    }
}
