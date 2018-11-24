package br.com.sifat;

import br.com.sifat.model.Pessoa;
import br.com.sifat.model.TipoPessoa;
import br.com.sifat.processor.CustomEnumeratedProcessor;

import javax.persistence.EntityManager;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("teste");

        CustomEnumeratedProcessor.process();



        EntityManager em = EntityManagerHelper.getEntityManager();

        Pessoa p = new Pessoa();
        p.setApelido("leonardo");
        p.setIncricaoEstadual("54.768.125.2");
        p.setTipoPessoa(TipoPessoa.FISICA);
        System.out.println(p);
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }
}
