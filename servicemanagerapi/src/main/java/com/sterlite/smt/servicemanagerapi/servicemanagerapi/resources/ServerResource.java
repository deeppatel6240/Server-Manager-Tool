package com.sterlite.smt.servicemanagerapi.servicemanagerapi.resources;
/*
 * author: deepkumar.sherathiya
 * version: 1.0
 * date: 22/02/2022
 */

import com.sterlite.smt.servicemanagerapi.servicemanagerapi.enumeration.Status;
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.exceptions.ServerNotFoundException;
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.model.Response;
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.model.Server;
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.services.implementation.ServerServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;


@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
@Slf4j
public class ServerResource {

    @Autowired
    private final ServerServiceImpl serverService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers(){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("servers", serverService.list(20)))
                        .message("Server retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {

        Server server = serverService.ping(ipAddress);

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server", server))
                        .message(server.getStatus() == Status.SERVER_UP ? "Ping Success" : "Ping Failed")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server", serverService.create(server)))
                        .message("Server Created successfully")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .data(Map.of("server", serverService.get(Long.valueOf(id))))
                            .message("Server retrieved")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
                    );
        }
        catch (ServerNotFoundException serverNotFoundException){
             log.error("Server id: {} is not found", id);
             return ResponseEntity.ok(
                     Response.builder()
                             .timeStamp(LocalDateTime.now())
                             .message(serverNotFoundException.getMessage())
                             .status(INTERNAL_SERVER_ERROR)
                             .statusCode(INTERNAL_SERVER_ERROR.value())
                             .build()
             );
        }
        catch (NumberFormatException n){
            log.error("Server id should be in integer");
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Server id is should be in integer")
                            .status(BAD_REQUEST)
                            .statusCode(BAD_REQUEST.value())
                            .build()
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .data(serverService.delete(Long.valueOf(id)))
                            .message("Server deleted successfully")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }
        catch (ServerNotFoundException serverNotFoundException) {
            log.error("Server id: {} is not exist", id);
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message(serverNotFoundException.getMessage())
                            .status(INTERNAL_SERVER_ERROR)
                            .statusCode(INTERNAL_SERVER_ERROR.value())
                            .build()
            );
        }
        catch (NumberFormatException n){
            log.error("Server id should be in integer");
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Server id is should be in integer")
                            .status(BAD_REQUEST)
                            .statusCode(BAD_REQUEST.value())
                            .build()
            );
        }
    }

    @PutMapping("/save/{id}")
    public ResponseEntity<Response> updateServer(@RequestBody @Valid Server server){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("Updated", serverService.update(server)))
                        .message("Server updated successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(
                "/home/sterlite/Projects/SMT/ServerImages/" + fileName));
    }
}
