package by.bsuir.store.domain;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int userId;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private int role;
    private boolean blackList;
    private String address;
    private String email;

    public User() {
    }

    public User(String login, String password, String firstName, String lastName, int role, boolean blackList, String address, String email) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.blackList = blackList;
        this.address = address;
        this.email = email;
    }

    public User(String login, String password, String firstName, String lastName, String email, String address) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isBlackList() {
        return blackList;
    }

    public void setBlackList(boolean blackList) {
        this.blackList = blackList;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        User o = (User) obj;

        if (role != o.role) {
            return false;
        }
        if (blackList != o.blackList) {
            return false;
        }
        if (userId != o.userId) {
            return false;
        }
        if (login == null) {
            if (o.login != null) {
                return false;
            }
        } else if (!login.equals(o.login)) {
            return false;
        }
        if (password == null) {
            if (o.password != null) {
                return false;
            }
        } else if (!password.equals(o.password)) {
            return false;
        }
        if (firstName == null) {
            if (o.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(o.firstName)) {
            return false;
        }
        if (lastName == null) {
            if (o.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(o.lastName)) {
            return false;
        }
        if (address == null) {
            if (o.address != null) {
                return false;
            }
        } else if (!address.equals(o.address)) {
            return false;
        }

        if (email == null) {
            if (o.email != null) {
                return false;
            }
        } else if (!email.equals(o.email)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + userId;
        result = prime * result + role;
        result = prime * result + ((blackList == false) ? 0 : 1);
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(": login=" + login);
        sb.append(", password=" + password);
        sb.append(", firstName=" + firstName);
        sb.append(", lastName=" + lastName);
        sb.append(", role=" + role);
        sb.append(", blackList=" + blackList);
        sb.append(", address=" + address);
        sb.append(", email=" + email);
        return sb.toString();
    }
}
