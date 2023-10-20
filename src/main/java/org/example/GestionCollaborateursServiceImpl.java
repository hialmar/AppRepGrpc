package org.example;

import com.proto.collaborateur.CollaborateurRequest;
import com.proto.collaborateur.GestionCollaborateursServiceGrpc;
import com.proto.collaborateur.RefCollaborateurResponse;
import com.proto.common.Contact;
import com.proto.common.Identite;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;

public class GestionCollaborateursServiceImpl extends GestionCollaborateursServiceGrpc.GestionCollaborateursServiceImplBase{
    private MetierColl metier;
    public GestionCollaborateursServiceImpl(MetierColl metier) {
        this.metier = metier;
    }
    public void getReferenceCollaborateur(CollaborateurRequest request, StreamObserver<RefCollaborateurResponse> response) {
        try {
            Identite i = this.metier.getIdentite(request.getCodeColl()); Contact c = this.metier.getContact(request.getCodeColl()); RefCollaborateurResponse rfcor =
                    RefCollaborateurResponse.newBuilder().setIdentite(i).setContact(c).build(); response.onNext(rfcor);
            response.onCompleted();
        } catch (NotFoundException var6) {
            response.onError(new StatusException(Status.NOT_FOUND)); }
    }
}
