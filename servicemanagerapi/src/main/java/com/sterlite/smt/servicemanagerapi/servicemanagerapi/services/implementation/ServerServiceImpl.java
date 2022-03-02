package com.sterlite.smt.servicemanagerapi.servicemanagerapi.services.implementation;
/*
 * author: deepkumar.sherathiya
 * version: 1.0
 * date: 21/02/2022
 */
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.enumeration.Status;
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.exceptions.ServerAlreadyExistException;
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.exceptions.ServerNotFoundException;
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.model.Server;
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.repositories.ServerRepo;
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.services.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

import static java.lang.Boolean.TRUE;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    @Autowired
    private final ServerRepo serverRepo;

    @Override
    public Server create(Server server) throws ServerAlreadyExistException {
        log.info("Saving new server: {}", server.getName());

        Optional<Server> serverRepoById = serverRepo.findById(server.getId());

        if (serverRepoById.isPresent()) {
            throw new ServerAlreadyExistException("Server id already exist");
        } else {
            server.setImageUrl(setServerImageUrl());
        }
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
    public Server get(Long id) throws ServerNotFoundException, NumberFormatException {
        log.info("Fetching server by id: {}", id);
        Optional<Server> getServerById = serverRepo.findById(id);
        return getServerById.orElseThrow(() -> new ServerNotFoundException("Sorry! Server id: " +
                id + " is not found."));
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}", server.getId());
        return serverRepo.save(server);
    }

    @Override
    public Map<String, Boolean> delete(Long id) throws ServerNotFoundException, NumberFormatException {
        log.info("Deleting server by id: {}", id);
        Server getServerByIdDelete = serverRepo.findById(id).orElseThrow(() -> new ServerNotFoundException("Sorry! Server id: " +
                id + " is not exist."));
        serverRepo.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", TRUE);
        return response;
    }

    private String setServerImageUrl() {
        String[] imageNames = {"server0.png", "server1.png", "server2.png", "server3.png", "server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/server/image/" + imageNames[new Random().nextInt(5)]).toUriString();
    }

    private boolean isReachable(String ipAddress, int port, int timeOut) {
        try {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(ipAddress, port), timeOut);
            }
            return true;
        } catch (IOException exception) {
            return false;
        }
    }
}
