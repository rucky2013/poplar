package com.dempe.poplar.core.support;

import br.com.caelum.vraptor.view.Results;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

/**
 * Default 400 handler
 *
 * @author Rodrigo Turini
 */
@ApplicationScoped
public class DefaultInvalidInputHandler implements InvalidInputHandler {

    private final Result result;

    @Inject
    public DefaultInvalidInputHandler(Result result) {
        this.result = result;
    }

    @Override
    public void deny(InvalidInputException e) {
        result.use(Results.http()).sendError(SC_BAD_REQUEST, e.getMessage());
    }
}
