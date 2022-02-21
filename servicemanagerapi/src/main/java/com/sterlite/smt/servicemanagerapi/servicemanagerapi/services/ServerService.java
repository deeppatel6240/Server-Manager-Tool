package com.sterlite.smt.servicemanagerapi.servicemanagerapi.services;
/*
 * author: deepkumar.sherathiya
 * version: 1.0
 * date: 21/02/2022
 */

import com.sterlite.smt.servicemanagerapi.servicemanagerapi.model.Server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {
    Server create(Server server);
    Server ping(String ipAddress) throws IOException;
    Collection<Server> list(int limit);
    Server get(Long id);
    Server update(Server server);
    Boolean delete(Long id);
}
