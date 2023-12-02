package com.demo;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;


public class UDPUtils {

    /**
     * 发送广播
     *
     * @return
     */
    public static Set<String> sendBroadcast(String data, String uuid, String dstHost, int dstPort) {
        Set<String> xmlSet = new HashSet<>();
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            clientSocket.setSoTimeout(3000);
            clientSocket.setReuseAddress(true);
            System.out.println(" =============发送局域网广播 ===========");
            InetAddress IPAddress = InetAddress.getByName(dstHost);
            byte[] sendData;
            byte[] receiveData = new byte[2048];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            sendData = data.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, dstPort);

            clientSocket.send(sendPacket);
            do {
                try {
                    clientSocket.receive(receivePacket);
                    String receiveMsg = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
                    if (receiveMsg.contains(uuid)) {
                        xmlSet.add(receiveMsg);
                        System.out.println(receiveMsg);
                    }
                } catch (SocketTimeoutException e) {
                    System.out.println("接收超时,停止扫描");
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlSet;
    }

}