package com.skiff.common.core.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

public class IpUtil {


    private static InetAddress inetAddress = null;

    public static InetAddress getInetAddress() throws Exception {
        if (Objects.nonNull(inetAddress)) {
            return inetAddress;
        }
        InetAddress localHost = InetAddress.getLocalHost();
        String ipv4Regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        if (localHost.getHostAddress().matches(ipv4Regex) && !localHost.isAnyLocalAddress() && !localHost.isLoopbackAddress()) {
            inetAddress = localHost;
            return inetAddress;
        }
        List<InetAddress> inetAddressList = getInetAddressList();
        inetAddress = inetAddressList.stream().filter(x -> !x.getHostAddress().startsWith("2.")).findFirst().orElse(inetAddressList.getFirst());
        return inetAddress;
    }

    public static List<InetAddress> getInetAddressList() throws SocketException {
        List<InetAddress> inetAddressList = new ArrayList<>();
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            if (!networkInterface.isVirtual() && !networkInterface.isLoopback()) {
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress.getAddress().length == 4
                            && !inetAddress.isLoopbackAddress()
                            && !inetAddress.isAnyLocalAddress()) {
                        inetAddressList.add(inetAddress);
                    }
                }
            }
        }
        return inetAddressList;
    }
}
