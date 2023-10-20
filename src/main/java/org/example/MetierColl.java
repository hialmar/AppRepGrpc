package org.example;

import com.proto.common.Contact;
import com.proto.common.Identite;

import java.util.HashMap;

public class MetierColl {
    private final HashMap<String, Identite> identitesColl = new HashMap<>();
    private final HashMap<String, Contact> contactsColl = new HashMap<>();
    public MetierColl() {
        this.identitesColl.put("148", Identite.newBuilder().setNom("Desprats").setPrenom("Thierry").build());
        this.contactsColl.put("148", Contact.newBuilder().setLocbureau("1R1-124").setEmail("Thierry.Desprats@ups-tlse3.fr").setPhone("6929").build());
        this.identitesColl.put("146", Identite.newBuilder().setNom("Teyssie").setPrenom("Cedric").build());
    }
    public Identite getIdentite(String codeColl) throws NotFoundException {
        Identite i = this.identitesColl.get(codeColl);
        if (i != null) { return i; } else { throw new NotFoundException(); } }
    public Contact getContact(String codeColl) throws NotFoundException { Contact c = (Contact)this.contactsColl.get(codeColl);
        if (c != null) { return c; } else { throw new NotFoundException(); }
    }
}
