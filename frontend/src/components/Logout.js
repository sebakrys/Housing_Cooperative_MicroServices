import React, { Component } from 'react';
import Zawartosc from "./Zawartosc";
import App from "../App";
import axios from "axios";

class Logout extends Component {

    constructor(props) {
        super(props);
    }


    logout() {
        alert("Logout1");
        this.props.history.push('/');
        alert("Logout2");
        this.props.keycloak.logout();
        alert("Logout3");
        Zawartosc.sAdmin = false;
        Zawartosc.sToken = null;
        alert("Logout4");
    }

    render() {

        var userJSON = {
            /*client_id : "<client_id>",
            client_secret : "<client_secret>",
            refresh_token : "<refresh_token>"*/
        };
        const headers = {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization': 'Bearer '+Zawartosc.sToken
        };
        axios.post('http://localhost:8080/realms/resourceServer/protocol/openid-connect/logout', userJSON, {headers})
            .then((response) => {
                console.log("RESP:"+response);
                //return postResponse.data;
                //todo wiadomosc o pozytywnym zarejestrowaniu
                //return(['ok', data]);
            }, (error) => {
                console.log("EEEE:"+error);
                //return(['error', 'Nie zarejestrowano']);

            });
        return (
            <button onClick={ () => {
            alert("Logout Page");
            } }>
                Logout
            </button>
        );
    }
}
export default Logout;