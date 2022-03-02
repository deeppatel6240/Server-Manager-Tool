package com.sterlite.smt.servicemanagerapi.servicemanagerapi.services;
/*
 * author: deepkumar.sherathiya
 * version: 1.0
 * date: 21/02/2022
 */

import com.sterlite.smt.servicemanagerapi.servicemanagerapi.exceptions.ServerNotFoundException;
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.model.Server;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public interface ServerService {
    Server create(Server server) throws Exception;
    Server ping(String ipAddress) throws IOException;
    Collection<Server> list(int limit);
    Server get(Long id) throws ServerNotFoundException;
    Server update(Server server);
    Map<String, Boolean> delete(Long id) throws ServerNotFoundException;
}
