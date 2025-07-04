package org.kickmyb.server.task;

import org.kickmyb.server.account.MUser;
import org.kickmyb.transfer.*;

import java.util.List;

public interface ServiceTask {

    class Existing extends Exception {}
    class TooShort extends Exception {}
    class Empty extends Exception {}
    class Unauthorized extends Exception {}
    class DoesntExist extends Exception {}

    // entity handling
    TaskDetailResponse detail(Long id, MUser user) throws Unauthorized;
    void addOne(AddTaskRequest req, MUser user) throws Existing, Empty, TooShort;
    void deleteOne(Long id, MUser user) throws Unauthorized, DoesntExist;
    void updateProgress(long taskID, int value, MUser user) throws Unauthorized;
    List<HomeItemResponse> home(Long userID);
    TaskDetailPhotoResponse detailPhoto(Long id, MUser user);
    List<HomeItemPhotoResponse> homePhoto(Long userID);

    // Potential web demo for JS injection
    String index();

    MUser userFromUsername(String username);
}