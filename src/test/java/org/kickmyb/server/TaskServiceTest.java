package org.kickmyb.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kickmyb.server.account.MUser;
import org.kickmyb.server.account.MUserRepository;
import org.kickmyb.server.task.MProgressEventRepository;
import org.kickmyb.server.task.MTask;
import org.kickmyb.server.task.MTaskRepository;
import org.kickmyb.server.task.ServiceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class TaskServiceTest {

    @Autowired
    MUserRepository repoUser;
    @Autowired
    MTaskRepository repo;
    @Autowired
    MProgressEventRepository repoProgressEvent;
    @Autowired
    private ServiceTask serviceTask;

    @Test
    public void supprimerTacheFonctionneAvecIdCorrect() throws Exception {

        // Créer un user
        MUser user = new MUser();
        user.username = "mrpipo";
        user.password = "pipo";
        repoUser.save(user);

        // Ajouter une  task
        MTask task = new MTask();
        task.name = "Tâche test";
        task.creationDate = new Date();
        task.deadline = new Date();
        user.tasks.add(task);
        repo.save(task);
        repoUser.save(user);

        // Cherche les données ajoutées
        MUser userDepuisBD = repoUser.findById(user.id).orElseThrow();

        Assertions.assertEquals(1, userDepuisBD.tasks.size());

        // Supprimer la tâche
        serviceTask.deleteOne(task.id, userDepuisBD);

        // Vérifier qu'elle a été supprimée
        MUser userDepuisBDApresDelete = repoUser.findById(user.id).orElseThrow();
        Assertions.assertTrue(userDepuisBDApresDelete.tasks.isEmpty());
    }

    @Test
    public void suppressionEchoueAvecIdInexistant() {
        MUser user = new MUser();
        user.username = "testuser";
        user.password = "pass";
        repoUser.save(user);

        Assertions.assertThrows(ServiceTask.DoesntExist.class, () -> {
            serviceTask.deleteOne(Long.MAX_VALUE, user);
        });
    }

    @Test
    public void controleAccesFonctionne() throws Exception {
        // Mirko user
        MUser userMirko = new MUser();
        userMirko.username = "Mirko Brioche";
        userMirko.password = "Bonjour123";
        repoUser.save(userMirko);

        // Tâche
        MTask task = new MTask();
        task.name = "Tâche de Mirko";
        task.creationDate = new Date();
        task.deadline = new Date();
        userMirko.tasks.add(task);
        repo.save(task);
        repoUser.save(userMirko);

        // Hackerman user
        MUser userHackerman = new MUser();
        userHackerman.username = "HackerMan123";
        userHackerman.password = "T&9r^ZxF#8wLs!2PvGq@37bK$MnXe+YD";
        repoUser.save(userHackerman);

        // Vérifier que Hackerman peut pas modifier ma tâche
        Assertions.assertThrows(ServiceTask.Unauthorized.class, () -> {
            serviceTask.deleteOne(task.id, userHackerman);
        });
    }
}
