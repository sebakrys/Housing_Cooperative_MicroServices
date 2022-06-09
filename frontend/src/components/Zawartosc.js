import React from "react";
import IndexNavbarSecured from "./Navbar/indexNavbarSecured";

import {BrowserRouter as Router, Routes, Route, Link}
    from 'react-router-dom';
import LoginPage from "../pages/LoginPage";
import Welcome from "../pages/Welcome";
import RegisterPage from "../pages/RegisterPage";
import BuildingsPage from "../pages/BuildingsPage";
import AddBuildingPage from "../pages/AddBuildingPage";
import Navbar from "./Navbar";
import Secured from "../pages/Secured";
import Keycloak from "keycloak-js";
import UserInfo from "./UserInfo";
import Logout from "./Logout";
import {useNavigate} from "react-router";




class Zawartosc extends React.Component{

    static sToken = null;
    static sAdmin = false;


    constructor(props) {
        super(props);
        this.state = { keycloak: null, authenticated: false };
        this.authInProg = false;

    }




    componentDidMount() {
        if(!this.authInProg){
            this.authInProg = true;
            //alert("componentDidMount")
            const keycloak = Keycloak('/keycloak.json');
            keycloak.init({onLoad: 'login-required'}).then(authenticated => {
                this.setState({ keycloak: keycloak, authenticated: authenticated });
            })
        }

    }

    componentDidUpdate() {
        if(this.authInProg)this.authInProg=false;
    }

    render() {
        if (this.state.keycloak) {
            if (this.state.authenticated) {
                console.log("TOKEN:"+this.state.keycloak.token);
                console.log("ROLES:"+JSON.stringify(this.state.keycloak.tokenParsed));
                console.log("ROLES:"+JSON.stringify(this.state.keycloak.tokenParsed.realm_access.roles));
                const roles = this.state.keycloak.tokenParsed.realm_access.roles;
                const found = roles.indexOf("ADMIN")
                console.log("Found: "+found)
                console.log("KEYCLOACK USER: "+JSON.stringify(this.state.keycloak));
                Zawartosc.sToken = this.state.keycloak.token;
                if(found!=(-1)){
                    Zawartosc.sAdmin = true;
                    return (
                        <div>
                            <IndexNavbarSecured />
                            <Link to="http://localhost:8080/realms/resourceServer/protocol/openid-connect/logout" className="btn btn-primary">Sign up</Link>
                            <button
                                type="button"
                                className="text-blue-800"
                                onClick={() => {
                                    this.state.keycloak.logout()
                                }}
                            >
                                Logout
                            </button>
                            <a href="http://localhost:8080/realms/resourceServer/protocol/openid-connect/logout"><button>Click</button></a>

                            <Routes>
                                <Route path='/logout' component={() => {
                                    alert("kupa");
                                    window.location.href = 'http://localhost:8080/realms/resourceServer/protocol/openid-connect/logout';
                                    return null;
                                }}/>
                                <Route exact path='/' exact element={<Welcome/>}/>
                                <Route path="/user" element={<UserInfo keycloak={this.state.keycloak}/>}/>
                                <Route path="/public" element={<Welcome/>}/>
                                <Route path="/secured" element={<Secured/>}/>
                                <Route path='/login' element={<LoginPage/>}/>
                                <Route path='/register' element={<RegisterPage/>}/>
                                <Route path='/buildings' element={<BuildingsPage/>}/>
                                <Route path='/addbuilding' element={<AddBuildingPage/>}/>
                            </Routes>
                        </div>
                    );
                }else{
                    console.log("Nie ADMIN")
                    return (
                        <div>
                            <Navbar keycloak={this.state.keycloak}/>
                            <div>
                                Unable to authenticate as Admin!
                            </div>
                            <Routes>
                                <Route exact path='/' exact element={<Welcome/>}/>
                                <Route path="/public" element={<Welcome/>}/>
                                <Route path="/user" element={<UserInfo keycloak={this.state.keycloak}/>}/>
                            </Routes>
                        </div>

                    );
                }
            } else return (<div><Navbar/>
                <Route path="/" element={<Welcome/>}/>Unable to authenticate!</div>)
        }
        return (
            <div>Initializing Keycloak...</div>
        );

    }

};

export default Zawartosc;