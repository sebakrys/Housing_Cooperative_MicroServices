import React from "react";
import { Nav, NavLink, NavMenu }
    from "./NavbarElements";
import Secured from "../../pages/Secured";



const NavbarSecured = () => {

    /*if(Secured.state.authenticated) */return (
        <>
            <Nav>
                <NavMenu>

                    <a className="btn btn-outline-danger" href="http://localhost:8080/realms/resourceServer/protocol/openid-connect/logout">
                        Logout
                    </a>
                    <NavLink to="/user" activeStyle>
                        User
                    </NavLink>
                    <NavLink to="/register" activeStyle>
                        Register
                    </NavLink>
                    <NavLink to="/buildings" activeStyle>
                        Buildings
                    </NavLink>
                    <NavLink to="/addbuilding" activeStyle>
                        Add Building
                    </NavLink>
                </NavMenu>
            </Nav>
        </>
    );
    /*else return (
        <>
            <Nav>
                <NavMenu>
                    <NavLink to="/login" activeStyle>
                        Login
                    </NavLink>
                    <NavLink to="/public" activeStyle>
                        Public
                    </NavLink>
                    <NavLink to="/secured" activeStyle>
                        Secured
                    </NavLink>
                </NavMenu>
            </Nav>
        </>
    );*/
};

export default NavbarSecured;