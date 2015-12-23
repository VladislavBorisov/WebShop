package by.bsuir.store.controller.command.enumeration;

import by.bsuir.store.controller.command.ICommand;
import by.bsuir.store.controller.command.impl.*;
import by.bsuir.store.controller.command.impl.admin.*;
import by.bsuir.store.controller.command.impl.navigation.*;

public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    SIGN_UP {
        {
            this.command = new SignupCommand();
        }
    },
    LANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }
    },
    TO_MAIN {
        {
            this.command = new ToMainCommand();
        }
    },
    TO_INDEX {
        {
            this.command = new ToIndexCommand();
        }
    },
    SHOW_CATALOG {
        {
            this.command = new GetCatalogCommand();
        }
    },
    SHOW_PRODUCT {
        {
            this.command = new ProductViewCommand();
        }
    },
    ALL_USERS {
        {
            this.command = new AllUsersCommand();
        }
    },
    ADD_TO_BLACKLIST {
        {
            this.command = new AddToBlacklistCommand();
        }
    },
    REMOVE_FROM_BLACKLIST {
        {
            this.command = new RemoveFromBlackListCommand();
        }
    },
    DELETE_USER {
        {
            this.command = new DeleteUserCommand();
        }
    },
    DELETE_PRODUCT {
        {
            this.command = new DeleteProductCommand();
        }
    },
    ADD_PRODUCT {
        {
            this.command = new AddProductCommand();
        }
    },
    TO_BASKET {
        {
            this.command = new ToBasketCommand();
        }
    },
    TO_EDIT {
        {
            this.command = new ToEditCommand();
        }
    },
    TO_SIGNUP {
        {
            this.command = new ToSignupCommand();
        }
    },
    EDIT_PRODUCT {
        {
            this.command = new EditProductCommand();
        }
    },
    ADD_TO_BASKET {
        {
            this.command = new AddProductToBasketCommand();
        }
    },
    MAKE_ORDER {
        {
            this.command = new MakeOrderCommand();
        }
    },
    DELETE_ORDER {
        {
            this.command = new DeleteOrderCommand();
        }
    },
    ALL_USER_ORDERS {
        {
            this.command = new AllUserOrdersCommand();
        }
    },
    TO_ADD_PRODUCT {
        {
           this.command = new ToAddProductCommand();
        }
    };

    ICommand command;

    public ICommand getCurrentCommand() {
        return command;
    }
}
