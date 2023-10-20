package org.example;

import com.proto.collaborateur.CollaborateurRequest;
import com.proto.collaborateur.GestionCollaborateursServiceGrpc;
import com.proto.collaborateur.RefCollaborateurResponse;
import com.proto.common.Identite;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class MainClientTestAnnuaireCollaborateurs {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 40100).usePlaintext()
                .build();

        GestionCollaborateursServiceGrpc.GestionCollaborateursServiceBlockingStub OneRefCollClient
                = GestionCollaborateursServiceGrpc.newBlockingStub(channel);

        System.out.println("Client TEST du service Annuaire des Collaborateurs");
        System.out.println("\nClient : -> TESTER 148 Expected : Desprats Thierry...");
        CollaborateurRequest request = CollaborateurRequest.newBuilder().setCodeColl("148").build();
        RefCollaborateurResponse refResponse = OneRefCollClient.getReferenceCollaborateur(request);
        System.out.println("Références trouvée : ");
        System.out.println(refResponse);
        Identite i4 = refResponse.getIdentite();
        if (i4.getNom().equalsIgnoreCase("Desprats")) {
            System.out.println("[OK]");
        } else {
            System.out.println("[KO]");
        }

        System.out.println("\nClient : -> TESTER 150 Expected : null");
        try {
            request = CollaborateurRequest.newBuilder().setCodeColl("150").build();
            OneRefCollClient.getReferenceCollaborateur(request);
        } catch (StatusRuntimeException var10) {
            System.out.println("Client : <-- " + var10.getStatus().getCode());
            System.out.println("[OK]");
        }
        channel.shutdown();
        System.out.println("\nClient TEST du service Catalogue competence : FIN");
    }
}
