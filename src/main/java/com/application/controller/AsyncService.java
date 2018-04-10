package com.application.controller;

import com.application.model.User;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import java.util.Date;

@Stateless
@Path("asyncService")
public class AsyncService {

    @Inject
    private Event<User> event;

    private AsyncResponse asyncResponse;

    @GET
    public void asyncService(@Suspended AsyncResponse asyncResponse) {
        long id = new Date().getTime();
        this.asyncResponse = asyncResponse;
        event.fireAsync(new User(id, "User " + id));
    }

    public void onFireEvent(@ObservesAsync User user) {
        asyncResponse.resume(Response.ok(user).build());
    }
}
