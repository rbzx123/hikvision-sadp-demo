package com.demo;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Set;
import java.util.UUID;

public class SadpDevice {

    XmlMapper xmlMapper = new XmlMapper();

    public ScanResultList scanDevice() throws Exception {
        String uuid = UUID.randomUUID().toString();
        String inquire = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Probe><Uuid>" + uuid + "</Uuid><Types>inquiry</Types></Probe>";
        Set<String> respXml = UDPUtils.sendBroadcast(inquire, uuid, "255.255.255.255", 37020);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : respXml) {
            if (s != null && s.trim().length() > 0) {
                s = s.trim().replace("\r", "").replace("\n", "");
                stringBuilder.append(s);
            }
        }
        String xml = stringBuilder.toString();
        xml = xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
        xml = "<ScanResultList>" + xml + "</ScanResultList>";
        System.out.println(xml);
        return xmlMapper.readValue(xml, ScanResultList.class);
    }

    public void activateDeviceStep1() throws Exception {
        String password = "abc123456";
        ScanResultList resultList = scanDevice();
        //取一个测试一下
        ScanResultList.ProbeMatch deviceInfo = resultList.getProbeMatch().get(0);
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(publicKey.getEncoded());
        ASN1Primitive derObject = subjectPublicKeyInfo.parsePublicKey();
        byte[] bytes = derObject.getEncoded();
        String base64Hex = Base64.encode(bytes);
        String uuid = UUID.randomUUID().toString();
        String exchangecodeXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Probe><Uuid>" + uuid + "</Uuid><MAC>" + deviceInfo.getMAC() + "</MAC><Types>exchangecode</Types><Code>" + base64Hex + "</Code></Probe>";

        Set<String> xmlSet = UDPUtils.sendBroadcast(exchangecodeXml, uuid, "255.255.255.255", 37020);
        String respXml = "";
        respXml = xmlSet.stream().findFirst().get();
        ExchangeCode exchangeCode = xmlMapper.readValue(respXml, ExchangeCode.class);
        if (exchangeCode != null && uuid.equals(exchangeCode.getUuid())) {
            String base64Code = exchangeCode.getCode();
            byte[] bytes2 = Base64.decode(base64Code);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] bytes3 = cipher.doFinal(bytes2);
            String random = new String(bytes3);
            UDPActivateResult result = activateDeviceStep2(random, password, deviceInfo.getMAC());
            System.out.println(result);
        }
    }

    private UDPActivateResult activateDeviceStep2(String random, String password, String mac) throws
            Exception {
        AES aes = new AES(Mode.ECB, Padding.ZeroPadding, random.substring(0, 16).getBytes());
        String newPwd = random.substring(0, 16) + password;
        String bs64 = aes.encryptBase64(newPwd);
        String uuid = UUID.randomUUID().toString();
        String activateXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Probe><Uuid>" + uuid +
                "</Uuid><MAC>" + mac + "</MAC><Types>activate</Types><Password>" + bs64 + "</Password></Probe>";

        try {
            Set<String> xmlSet = UDPUtils.sendBroadcast(activateXml, uuid, "255.255.255.255", 37020);
            String respXml = "";
            respXml = xmlSet.stream().findFirst().get();
            System.out.println(respXml);
            UDPActivateResult UDPActivateResult = xmlMapper.readValue(respXml, UDPActivateResult.class);
            if (UDPActivateResult != null && uuid.equals(UDPActivateResult.getUuid())) {
                return UDPActivateResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateInterface() throws Exception {
        String username = "admin";
        String password = "abc123456";
        String newIP = "192.168.1.100";
        String newMask = "255.255.255.0";
        String newGateway = "192.168.1.1";
        ScanResultList resultList = scanDevice();
        ScanResultList.ProbeMatch deviceInfo = resultList.getProbeMatch().get(0);
        String uuid = UUID.randomUUID().toString();
        Digester sha256 = new Digester(DigestAlgorithm.SHA256);
        String sha = sha256.digestHex(username + deviceInfo.getSalt() + password);
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        String m5 = md5.digestHex(sha);
        byte[] pub = Hex.decodeHex(m5);
        AES aes = new AES(Mode.ECB, Padding.ZeroPadding, pub);
        byte[] pass = new byte[64];
        System.arraycopy(pub, 0, pass, 0, pub.length);
        byte[] enPass = aes.encrypt(pass);
        String bsPass = Base64.encode(enPass);
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Probe><Uuid>" + uuid + "</Uuid><Types>update</Types><PWErrorParse>true</PWErrorParse><MAC>" + deviceInfo.getMAC() + "</MAC><Password bSalt=\"true\">" + bsPass + "</Password><IPv4Address>" + newIP + "</IPv4Address><CommandPort>8000</CommandPort><IPv4SubnetMask>" + newMask + "</IPv4SubnetMask><IPv4Gateway>" + newGateway + "</IPv4Gateway><IPv6Address>::</IPv6Address><IPv6Gateway>::</IPv6Gateway><IPv6MaskLen>64</IPv6MaskLen><DHCP>false</DHCP><HttpPort>80</HttpPort><SDKOverTLSPort>8443</SDKOverTLSPort></Probe>";
        Set<String> xmlSet = UDPUtils.sendBroadcast(xml, uuid, "255.255.255.255", 37020);
        String respXml = "";
        respXml = xmlSet.stream().findFirst().get();
        UpdateResult updateResult = xmlMapper.readValue(respXml, UpdateResult.class);
        System.out.println(JSONUtil.toJsonPrettyStr(updateResult));
    }

}
