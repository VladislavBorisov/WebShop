package com.web.shop.controller;

import javax.servlet.http.HttpServletRequest;

import com.web.shop.controller.command.ICommand;
import com.web.shop.controller.command.enumeration.CommandEnum;
import com.web.shop.controller.command.impl.EmptyCommand;
import com.web.shop.resource.MessageManager;

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
