package org.example;

import com.proto.collaborateur.CollaborateurRequest;
import com.proto.collaborateur.GestionCollaborateursServiceGrpc;
import com.proto.collaborateur.RefCollaborateurResponse;
import com.proto.common.Competence;
import com.proto.expertise.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.io.PrintStream;
import java.util.Iterator;

public class RecoExpertsServiceImpl extends RecoExpertiseServiceGrpc.RecoExpertiseServiceImplBase {
    private ManagedChannel channelExp = ManagedChannelBuilder.forAddress("localhost",40000)
            .usePlaintext().build();

    private ManagedChannel channelColl = ManagedChannelBuilder
            .forAddress("localhost",40100) .usePlaintext().build();

    private ExpertiseServiceGrpc.ExpertiseServiceBlockingStub CompRechClient;
    private GestionCollaborateursServiceGrpc.GestionCollaborateursServiceBlockingStub
            OneRefCollClient;
    public RecoExpertsServiceImpl() {
        this.CompRechClient = ExpertiseServiceGrpc.newBlockingStub(this.channelExp);
        this.OneRefCollClient = GestionCollaborateursServiceGrpc.newBlockingStub(this.channelColl);
    }

    public void giveAllExpertsSujet(ExpertiseSujetRequest request, StreamObserver<RecoExpertResponse> response) {
        try {
            ExpertiseSujetRequest requestTopic = ExpertiseSujetRequest.newBuilder().setSujet(request.getSujet()).build();
            Iterator<ExpertiseResponse> competences = this.CompRechClient.getCompetencesSujet(requestTopic);
            while (competences.hasNext()) {
                RecoExpertResponse r = null;
                RecoExpertResponse uneExpertise = null;
                Competence c = ((ExpertiseResponse) competences.next()).getComp();
                PrintStream var10000 = System.out;

                String var10001 = c.getCodeColl();
                var10000.println("Compétence trouvée : " + var10001 + "\t" + c.getSujet() + "\t" + c.getNiveau());
                CollaborateurRequest refRequest = CollaborateurRequest.newBuilder()
                        .setCodeColl(c.getCodeColl()).build();
                RefCollaborateurResponse refResponse = this.OneRefCollClient.getReferenceCollaborateur(refRequest);
                System.out.println("Références trouvées : ");
                System.out.println(refResponse);
                response.onNext(RecoExpertResponse.newBuilder()
                        .setNiveau(c.getNiveau()).setIdentite(refResponse.getIdentite()).setContact(refResponse.getContact()).build());
            }
            response.onCompleted();
        } catch (StatusRuntimeException var10) {
        }
    }
}
