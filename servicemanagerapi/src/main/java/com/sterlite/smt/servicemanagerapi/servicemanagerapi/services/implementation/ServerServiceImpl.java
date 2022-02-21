package com.sterlite.smt.servicemanagerapi.servicemanagerapi.services.implementation;
/*
 * author: deepkumar.sherathiya
 * version: 1.0
 * date: 21/02/2022
 */
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.enumeration.Status;
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.model.Server;
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.repositories.ServerRepo;
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.services.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import static java.lang.Boolean.TRUE;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepo serverRepo;

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server ip address: {}", ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id: {}", id);
        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}", server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by id: {}", id);
        serverRepo.deleteById(id);
        return TRUE;
    }

    private String setServerImageUrl() {
        String[] imageNames = {"server0.png", "server1.png", "server2.png", "server3.png", "server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("ServerImages/" + imageNames[new Random().nextInt(5)]).toUriString();
    }
}
