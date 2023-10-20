package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class MainServeurAnnuaireCollaborateurs {
    public static void main(String[] args) {
        try {
            System.err.println("Serveur du service de Gestion des Collaborateurs RUNNING");
            MetierColl m = new MetierColl();
            GestionCollaborateursServiceImpl servant = new GestionCollaborateursServiceImpl(m);
            Server server = ServerBuilder.forPort(40100).addService(servant).build().start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (server != null) {
                    server.shutdown();
                }
            }));

            server.awaitTermination();
        } catch (Exception var3) {
        }
    }
}
