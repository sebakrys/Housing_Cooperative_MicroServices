import React from "react";
import { Nav, NavLink, NavMenu }
    from "./NavbarElements";

const Navbar = () => {
    return (
        <>
            <Nav>
                <NavMenu>
                    <NavLink to="/login" activeStyle>
                        Login
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
};

export default Navbar;