package org.nebulamc.plugin.features.haproxy;

import org.bukkit.entity.Player;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HAProxy {

    Method isOnEuropeanIP;
    Class<?> haProxyAPI;
    Object haProxyAPIInstance;

    public HAProxy() throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException {
        haProxyAPI = Class.forName("org.nebulamc.haproxy.api.HAProxyAPI");
        Class<?> haProxyPlugin = Class.forName("org.nebulamc.haprox.HAProxyDetector");
        haProxyAPIInstance = haProxyPlugin.getField("api").get(null);

        isOnEuropeanIP = haProxyAPI.getMethod("isOnEuropeanIP", String.class, InetSocketAddress.class);
    }

    public boolean isIPInEurope(Player p) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                         .GET()
                                         .uri(URI.create("http://restcountries.com/v3.1/alpha/" + p.getAddress().getHostName()))
                                         .build();
        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String region = (String) response.body().getClass().getField("region").get(response.body());
            if (region.equals("Europe")) {
                return true;
            } else return false;
        } catch (IOException | InterruptedException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isOnEuropeanIP(Player p) {
        try {
            return (boolean) isOnEuropeanIP.invoke(haProxyAPIInstance, p.getName(), p.getAddress());
        } catch (InvocationTargetException | IllegalAccessException e) {
            return false;
        }
    }

}
