package by.bsuir.store.controller;

import by.bsuir.store.controller.command.ICommand;
import by.bsuir.store.controller.command.enumeration.CommandEnum;
import by.bsuir.store.controller.command.impl.EmptyCommand;
import by.bsuir.store.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    private static final String COMMAND = "command";
    private static final String MESSAGE = "WRONG_ACTION";

    public ICommand defineCommand(HttpServletRequest request) {
        ICommand command = new EmptyCommand();

        String action = request.getParameter(COMMAND);

        if (action == null || action.isEmpty()) {
            return command;
        }

        try {
            CommandEnum commandEnum = CommandEnum.valueOf(action.toUpperCase());
            command = commandEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("message", action + MessageManager.WRONG_ACTION);
        }
        return command;
    }
}
