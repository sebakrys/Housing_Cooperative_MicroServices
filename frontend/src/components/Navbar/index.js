import React from "react";
import { Nav, NavLink, NavMenu }
    from "./NavbarElements";
import Secured from "../../pages/Secured";



const Navbar = () => {

    /*if(Secured.state.authenticated) */return (
        <>
            <Nav>
                <NavMenu>
                    <NavLink to="/logout" activeStyle>
                        Logout
                    </NavLink>
                    <NavLink to="/public" activeStyle>
                        Public
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

export default Navbar;